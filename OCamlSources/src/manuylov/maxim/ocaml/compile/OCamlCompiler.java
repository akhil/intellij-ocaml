/*
 * OCaml Support For IntelliJ Platform.
 * Copyright (C) 2010 Maxim Manuylov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/gpl-2.0.html>.
 */

package manuylov.maxim.ocaml.compile;

import com.intellij.compiler.impl.CompilerContentIterator;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.SourceInstrumentingCompiler;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleFileIndex;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import manuylov.maxim.ocaml.entity.CyclicDependencyException;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import manuylov.maxim.ocaml.util.OCamlSystemUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.intellij.openapi.compiler.CompilerMessageCategory.ERROR;
import static com.intellij.openapi.compiler.CompilerMessageCategory.INFORMATION;

/**
 * @author Maxim.Manuylov
 *         Date: 05.04.2010
 */
public class OCamlCompiler extends BaseOCamlCompiler implements SourceInstrumentingCompiler {
    @NotNull
    public ProcessingItem[] getProcessingItems(@NotNull final CompileContext context) {
        final ProgressIndicator progressIndicator = context.getProgressIndicator();
        progressIndicator.setIndeterminate(true);
        progressIndicator.setText("Preparing files for compiling...");

        final ArrayList<ProcessingItem> items = new ArrayList<ProcessingItem>();
        final ArrayList<OCamlModule> ocamlModules = new ArrayList<OCamlModule>();

        final OCamlCompileContext ocamlContext = OCamlCompileContext.createOn(context);
        final boolean isDebugMode = ocamlContext.isDebugMode();

        try {
            if (ocamlContext.isStandaloneCompile()) {
                ocamlModules.addAll(collectItemsForStandaloneCompile(context, items));
            }
            else {
                final OCamlModule mainOCamlModule = getMainOCamlModule(ocamlContext);
                ocamlModules.add(mainOCamlModule);
                ocamlModules.addAll(mainOCamlModule.collectAllDependencies());
                Collections.reverse(ocamlModules);
            }
        }
        catch (final CyclicDependencyException e) {
            context.addMessage(ERROR, e.getMessage(), null, -1, -1);
            return new ProcessingItem[0];
        }

        final boolean isRebuild = context.isRebuild();
        final LocalFileSystem fileSystem = LocalFileSystem.getInstance();
        for (final OCamlModule ocamlModule : ocamlModules) {
            processFile(fileSystem.findFileByIoFile(ocamlModule.getInterfaceFile()), ocamlModule.getCompiledInterfaceFile(), items, isDebugMode, isRebuild);
            processFile(fileSystem.findFileByIoFile(ocamlModule.getImplementationFile()), ocamlModule.getCompiledImplementationFile(), items, isDebugMode, isRebuild);
        }

        return items.toArray(new ProcessingItem[items.size()]);
    }

    private void processFile(@Nullable final VirtualFile file,
                             @NotNull final File compiledFile,
                             @NotNull final ArrayList<ProcessingItem> items,
                             final boolean isDebugMode,
                             final boolean isRebuild) {
        if (file != null) {
            items.add(createProcessingItem(file, compiledFile, isDebugMode, isRebuild));
        }
    }

    @NotNull
    private List<OCamlModule> collectItemsForStandaloneCompile(@NotNull final CompileContext context,
                                                               @NotNull final ArrayList<ProcessingItem> items
    ) throws CyclicDependencyException {
        final Project project = context.getProject();
        final ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        final Set<OCamlModule> ocamlModules = new HashSet<OCamlModule>();
        final Module[] modules = ModuleManager.getInstance(project).getModules();
        for (final Module module : modules) {
            if (!OCamlModuleUtil.isOCamlModule(module)) continue;
            final ModuleFileIndex fileIndex = ModuleRootManager.getInstance(module).getFileIndex();
            final Collection<VirtualFile> files = new ArrayList<VirtualFile>();
            fileIndex.iterateContent(new CompilerContentIterator(null, fileIndex, true, files));
            for (final VirtualFile file : files) {
                if (OCamlFileUtil.isOCamlSourceFile(file)) {
                    final OCamlModule ocamlModule = OCamlModule.getBySourceFile(file, project);
                    assert ocamlModule != null;
                    ocamlModules.add(ocamlModule);
                }
                else {
                    final File destDir = OCamlFileUtil.getCompiledDir(projectFileIndex, file.getParent());
                    items.add(createProcessingItem(file, new File(destDir, file.getName()), false, false));
                }
            }
        }
        final ProgressIndicator indicator = context.getProgressIndicator();
        final String oldText = indicator.getText();
        indicator.setText("Computing dependencies...");
        try {
            return OCamlModule.sortAccordingToDependencies(ocamlModules);
        }
        finally {
            indicator.setText(oldText);
        }
    }

    @NotNull
    public ProcessingItem[] process(@NotNull final CompileContext context, @NotNull final ProcessingItem[] items) {
        final ProgressIndicator progressIndicator = context.getProgressIndicator();
        progressIndicator.setIndeterminate(false);
        progressIndicator.setText("Compiling...");

        final double allCount = items.length;
        int processedCount = -1;

        final ArrayList<ProcessingItem> processedItems = new ArrayList<ProcessingItem>();
        final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(context.getProject()).getFileIndex();
        final OCamlCompileContext ocamlContext = OCamlCompileContext.createOn(context);

        for (final ProcessingItem item : items) {
            processedCount++;
            progressIndicator.setFraction(processedCount / allCount);

            final VirtualFile file = item.getFile();
            progressIndicator.setText2(OCamlFileUtil.getPathToDisplay(file));

            final VirtualFile destDir = getDestination(file, fileIndex);
            if (destDir == null) {
                context.addMessage(ERROR, "Cannot determine destination directory for \"" + OCamlFileUtil.getPathToDisplay(file) + "\" file.", file.getUrl(), -1, -1);
                continue;
            }

            if (OCamlFileUtil.isOCamlSourceFile(file)) {
                context.putUserData(THERE_WAS_RECOMPILATION, true);
                if (!compile(file, fileIndex, context, ocamlContext, destDir)) continue;
            }
            else {
                try {
                    file.copy(this, destDir, file.getName());
                } catch (final IOException e) {
                    context.addMessage(ERROR, "Cannot copy \"" + OCamlFileUtil.getPathToDisplay(file) + "\" file to \"" + OCamlFileUtil.getPathToDisplay(destDir) + "\" directory: " + e.getLocalizedMessage(), file.getUrl(), -1, -1);
                    continue;
                }
            }
            processedItems.add(item);
        }

        progressIndicator.setFraction(1.0);
        progressIndicator.setText2("");

        return processedItems.toArray(new ProcessingItem[processedItems.size()]);
    }

    private boolean compile(@NotNull final VirtualFile file,
                            @NotNull final ProjectFileIndex fileIndex,
                            @NotNull final CompileContext context,
                            @NotNull final OCamlCompileContext ocamlContext,
                            final VirtualFile destDir) {
        final OCamlModule ocamlModule = OCamlModule.getBySourceFile(file, context.getProject());
        assert ocamlModule != null;
        
        final GeneralCommandLine cmd = getBaseCompilerCommandLineForFile(file, fileIndex, context, ocamlContext.isDebugMode());
        if (cmd == null) return false;

        cmd.addParameter("-c");

        final Set<String> addedPaths = new HashSet<String>();
        addPath(cmd, addedPaths, OCamlFileUtil.getPathToDisplay(destDir));
        for (final OCamlModule dependency : ocamlModule.collectExactDependencies()) { // todo research: A: {open B;; let s = w;;} B: {open C;;} C: {let w = 0;;} - maybe replace collectExactDependencies with collectAllDependencies 
            final String path = OCamlFileUtil.getCompiledDir(fileIndex, dependency.getSourcesDir()).getPath();
            addPath(cmd, addedPaths, path);
        }
        
        cmd.addParameter("-o");
        final String destFileWithoutExtension = FileUtil.toSystemDependentName(new File(destDir.getPath(), file.getNameWithoutExtension()).getAbsolutePath());
        cmd.addParameter(destFileWithoutExtension);

        if (!ocamlContext.isStandaloneCompile()) {
            cmd.getParametersList().addParametersString(getRunConfiguration(ocamlContext).getCompilerOptions());
        }
            
        cmd.addParameter(OCamlFileUtil.getPathToDisplay(file));

        try {
            final ProcessOutput processOutput = OCamlSystemUtil.execute(cmd);

            processInfoLines(processOutput.getStdoutLines(), context, file);
            final boolean hasErrors = processErrorAndWarningLines(processOutput.getStderrLines(), context, file);

            return !hasErrors;
        }
        catch (final ExecutionException e) {
            context.addMessage(ERROR, e.getLocalizedMessage(), file.getUrl(), -1, -1);
            return false;
        }
    }

    private void addPath(@NotNull final GeneralCommandLine cmd, @NotNull final Set<String> addedPaths, @NotNull final String path) {
        if (!addedPaths.contains(path)) {
            cmd.addParameter("-I");
            cmd.addParameter(path);
        }
        addedPaths.add(path);
    }

    @NotNull
    public ValidityState createValidityState(@NotNull final DataInput in) throws IOException {
        return OCamlValidityState.load(in);
    }

    @NotNull
    public String getDescription() {
        return "OCaml Files Compiler";
    }

    public boolean validateConfiguration(@NotNull final CompileScope scope) {
        return true;
    }

    @Nullable
    private VirtualFile getDestination(@NotNull final VirtualFile file, @NotNull final ProjectFileIndex fileIndex) {
        if (!fileIndex.isInSourceContent(file)) return null;

        final VirtualFile sourcesDir = file.getParent();
        if (sourcesDir == null) return null;
        final File compiledDir = OCamlFileUtil.getCompiledDir(fileIndex, sourcesDir);

        final LocalFileSystem fileSystem = LocalFileSystem.getInstance();

        final ArrayList<String> relativeDirs = new ArrayList<String>();
        File dir = compiledDir;
        final VirtualFile[] destDir = new VirtualFile[] { fileSystem.findFileByIoFile(dir) };
        while (dir != null && destDir[0] == null) {
            relativeDirs.add(0, dir.getName());
            dir = dir.getParentFile();
            destDir[0] = fileSystem.findFileByIoFile(dir);
        }
        if (dir == null) return null;

        for (final String dirName : relativeDirs) {
            ApplicationManager.getApplication().invokeAndWait(new Runnable() {
                public void run() {
                    destDir[0] = ApplicationManager.getApplication().runWriteAction(new Computable<VirtualFile>() {
                        public VirtualFile compute() {
                            try {
                                return destDir[0].createChildDirectory(OCamlCompiler.this, dirName);
                            } catch (final IOException e) {
                                return null;
                            }
                        }
                    });
                }
            }, ModalityState.defaultModalityState());
            if (destDir[0] == null) return null;
        }

        return destDir[0];
    }
}
