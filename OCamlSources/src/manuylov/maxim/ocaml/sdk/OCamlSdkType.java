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

package manuylov.maxim.ocaml.sdk;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import manuylov.maxim.ocaml.util.OCamlSystemUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

/**
 * @author Maxim.Manuylov
 *         Date: 03.04.2010
 */
public class OCamlSdkType extends SdkType {
    @NotNull
    public static OCamlSdkType getInstance() {
        return SdkType.findInstance(OCamlSdkType.class);
    }

    public OCamlSdkType() {
        super("OCaml SDK");
    }

    @NotNull
    public Icon getIcon() {
        return OCamlIconUtil.getSmallOCamlIcon();
    }

    @Override
    @NotNull
    public Icon getIconForAddAction() {
        return getIcon();
    }

    @Nullable
    public String suggestHomePath() {
        if (SystemInfo.isWindows) {
            return "C:\\cygwin\\bin";
        }
        else if (SystemInfo.isLinux) {
            return "/usr/bin";
        }
        return null;
    }

    public boolean isValidSdkHome(@NotNull final String path) {
        final File ocaml = getTopLevelExecutable(path);
        final File ocamlc = getByteCodeCompilerExecutable(path);
        final File ocamlrun = getByteCodeInterpreterExecutable(path);
        final File ocamlopt = getNativeCompilerExecutable(path);
        final File ocamldebug = getDebuggerExecutable(path);
        return ocaml.canExecute() && ocamlc.canExecute() && ocamlrun.canExecute() && ocamlopt.canExecute() && ocamldebug.canExecute();
    }

    @NotNull
    public static File getTopLevelExecutable(@NotNull final String sdkHome) {
        return getExecutable(sdkHome, "ocaml");
    }

    @NotNull
    public static File getByteCodeCompilerExecutable(@NotNull final String sdkHome) {
        return getExecutable(sdkHome, "ocamlc");
    }

    @NotNull
    public static File getByteCodeInterpreterExecutable(@NotNull final String sdkHome) {
        return getExecutable(sdkHome, "ocamlrun");
    }

    @NotNull
    public static File getDebuggerExecutable(@NotNull final String sdkHome) {
        return getExecutable(sdkHome, "ocamldebug");
    }

    @NotNull
    public static File getNativeCompilerExecutable(@NotNull final String sdkHome) {
        return getExecutable(sdkHome, "ocamlopt");
    }

    @NotNull
    public String suggestSdkName(@Nullable final String currentSdkName, @NotNull final String sdkHome) {
        String version = getVersionString(sdkHome);
        if (version == null) return "Unknown at " + sdkHome;
        return "OCaml " + version;
    }

    @Nullable
    public String getVersionString(@NotNull final String sdkHome) {
        final String exePath = getByteCodeCompilerExecutable(sdkHome).getAbsolutePath();
        final ProcessOutput processOutput;
        try {
            processOutput = OCamlSystemUtil.getProcessOutput(sdkHome, exePath, "-version");
        } catch (final ExecutionException e) {
            return null;
        }
        if (processOutput.getExitCode() != 0) return null;
        final String stdout = processOutput.getStdout().trim();
        return stdout.isEmpty() ? null : stdout;
    }

    @Nullable
    public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull final SdkModel sdkModel, @NotNull final SdkModificator sdkModificator) {
        return null;
    }

    public void saveAdditionalData(@NotNull final SdkAdditionalData additionalData, @NotNull final Element additional) {
    }

    @NonNls
    public String getPresentableName() {
        return "OCaml SDK";
    }

    public void setupSdkPaths(@NotNull final Sdk sdk) {
        final SdkModificator[] sdkModificatorHolder = new SdkModificator[] { null };
        final ProgressManager progressManager = ProgressManager.getInstance();
        final Project project = PlatformDataKeys.PROJECT.getData(DataManager.getInstance().getDataContext());
        final Task.Modal setupTask = new Task.Modal(project, "Setting up library files", false) {
            public void run(@NotNull final ProgressIndicator indicator) {
                sdkModificatorHolder[0] = setupSdkPathsUnderProgress(sdk);
            }
        };
        progressManager.run(setupTask);
        if (sdkModificatorHolder[0] != null) sdkModificatorHolder[0].commitChanges();
    }

    @NotNull
    protected SdkModificator setupSdkPathsUnderProgress(@NotNull final Sdk sdk) {
        final SdkModificator sdkModificator = sdk.getSdkModificator();
        doSetupSdkPaths(sdkModificator);
        return sdkModificator;
    }

    public void doSetupSdkPaths(@NotNull final SdkModificator sdkModificator) {
        final String sdkHome = sdkModificator.getHomePath();

        {
            final File stdLibDir = new File(new File(new File(sdkHome).getParentFile(), "lib"), "ocaml");
            if (tryToProcessAsStandardLibraryDir(sdkModificator, stdLibDir)) return;
        }

        try {
            final String exePath = getByteCodeCompilerExecutable(sdkHome).getAbsolutePath();
            final ProcessOutput processOutput = OCamlSystemUtil.getProcessOutput(sdkHome, exePath, "-where");
            if (processOutput.getExitCode() == 0) {
                final String stdout = processOutput.getStdout().trim();
                if (!stdout.isEmpty()) {
                    if (SystemInfo.isWindows && stdout.startsWith("/")) {
                        for (final File root : File.listRoots()) {
                            final File stdLibDir = new File(root, stdout);
                            if (tryToProcessAsStandardLibraryDir(sdkModificator, stdLibDir)) return;
                        }
                    }
                    else {
                        final File stdLibDir = new File(stdout);
                        if (tryToProcessAsStandardLibraryDir(sdkModificator, stdLibDir)) return;
                    }
                }
            }
        }
        catch (final ExecutionException ignore) {}

        final File stdLibDir = new File("/usr/lib/ocaml");
        tryToProcessAsStandardLibraryDir(sdkModificator, stdLibDir);
    }

    private boolean tryToProcessAsStandardLibraryDir(@NotNull final SdkModificator sdkModificator, @NotNull final File stdLibDir) {
        if (!isStandardLibraryDir(stdLibDir)) return false;
        final VirtualFile dir = LocalFileSystem.getInstance().findFileByIoFile(stdLibDir);
        if (dir != null) {
            sdkModificator.addRoot(dir, OrderRootType.SOURCES);
            sdkModificator.addRoot(dir, OrderRootType.CLASSES);
        }
        return true;
    }

    private boolean isStandardLibraryDir(@NotNull final File dir) {
        if (!dir.isDirectory()) return false;
        final File pervasives_ml = new File(dir, "pervasives.ml");
        final File pervasives_mli = new File(dir, "pervasives.mli");
        final File pervasives_cmi = new File(dir, "pervasives.cmi");
        final File pervasives_cmx = new File(dir, "pervasives.cmx");
        return pervasives_ml.isFile() && pervasives_mli.isFile() && pervasives_cmi.isFile() && pervasives_cmx.isFile();
    }

    @NotNull
    private static File getExecutable(@NotNull final String path, @NotNull final String command) {
        return new File(path, SystemInfo.isWindows ? command + ".exe" : command);
    }
}