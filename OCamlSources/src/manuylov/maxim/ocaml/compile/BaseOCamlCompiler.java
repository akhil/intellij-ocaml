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

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.FileProcessingCompiler;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.run.OCamlRunConfiguration;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.intellij.openapi.compiler.CompilerMessageCategory.*;
import static com.intellij.openapi.compiler.CompilerMessageCategory.ERROR;

abstract class BaseOCamlCompiler {
    @NotNull protected static final Key<Boolean> THERE_WAS_RECOMPILATION = new Key<Boolean>("THERE_WAS_RECOMPILATION");
    @NotNull private static final String FILE = "File";
    @NotNull private static final String LINE = "line";
    @NotNull private static final String CHARACTERS = "characters";
    @NotNull private static final String WARNING_ = "Warning:";
    private static final int INVALID_INT = -1;

    @NotNull
    protected OCamlModule getMainOCamlModule(@NotNull final OCamlCompileContext ocamlContext) {
        final OCamlModule ocamlModule = getRunConfiguration(ocamlContext).getMainOCamlModule();
        assert ocamlModule != null;
        return ocamlModule;
    }

    @NotNull
    protected OCamlRunConfiguration getRunConfiguration(@NotNull final OCamlCompileContext ocamlContext) {
        final OCamlRunConfiguration runConfiguration = ocamlContext.getRunConfiguration();
        assert runConfiguration != null;
        return runConfiguration;
    }

    @Nullable
    protected GeneralCommandLine getBaseCompilerCommandLineForFile(@NotNull final VirtualFile file,
                                                                   @NotNull final ProjectFileIndex fileIndex,
                                                                   @NotNull final CompileContext context,
                                                                   final boolean isDebugMode) {
        final Module module = fileIndex.getModuleForFile(file);
        if (module == null) {
            context.addMessage(ERROR, "Cannot determine module for \"" + OCamlFileUtil.getPathToDisplay(file) + "\" file.", file.getUrl(), INVALID_INT, INVALID_INT);
            return null;
        }

        final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
        if (!OCamlModuleUtil.isOCamlSdk(sdk)) {
            context.addMessage(ERROR, "Sdk of module \"" + module.getName() + "\" is invalid.", file.getUrl(), INVALID_INT, INVALID_INT);
            return null;
        }

        //noinspection ConstantConditions
        final String sdkHomePath = sdk.getHomePath();
        final String compilerExePath = OCamlSdkType.getByteCodeCompilerExecutable(sdkHomePath).getAbsolutePath();

        final GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setWorkDirectory(sdkHomePath);
        cmd.setExePath(compilerExePath);
        if (isDebugMode) {
            cmd.addParameter("-g");
        }

        return cmd;
    }

    protected void processInfoLines(@NotNull final List<String> lines, @NotNull final CompileContext context, @Nullable final VirtualFile file) {
        for (final String line : lines) {
            final String url = file == null ? null : file.getUrl();
            context.addMessage(INFORMATION, line, url, INVALID_INT, INVALID_INT);
        }
    }

    protected boolean processErrorAndWarningLines(@NotNull final List<String> lines, @NotNull final CompileContext context, @Nullable final VirtualFile file) {
        boolean hasError = false;
        final List<FailureMessage> failures = parseOutput(lines);
        for (final FailureMessage failure : failures) {
            final boolean isError = failure.getType() == FailureMessage.Type.ERROR;
            final String url = file == null ? findUrl(failure) : file.getUrl();
            context.addMessage(isError ? ERROR : WARNING, failure.getMessageText(), url, failure.getLineNumber(), failure.getStartPosition());
            hasError |= isError;
        }
        return hasError;
    }

    @NotNull
    private List<FailureMessage> parseOutput(@NotNull final List<String> lines) {
        final List<FailureMessage> result = new ArrayList<FailureMessage>();
        final int size = lines.size();
        int i = 0;
        while (i < size) {
            final String current = lines.get(i);
            int nextIndex = i + 1;
            while (nextIndex < size && !lines.get(nextIndex).startsWith(FILE)) nextIndex++;
            if (current.startsWith(FILE)) {
                result.add(createFailureMessage(current, join(lines, i + 1, nextIndex)));
            }
            else {
                result.add(new FailureMessage(FailureMessage.Type.ERROR, join(lines, i, nextIndex), null, INVALID_INT, INVALID_INT, INVALID_INT));
            }
            i = nextIndex;
        }
        return result;
    }

    @NotNull
    private String join(@NotNull final List<String> lines, final int startIndex, final int endIndex) {
        final StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            sb.append("\n").append(lines.get(i).trim());
        }
        return sb.toString().trim();
    }

    @NotNull
    private FailureMessage createFailureMessage(@NotNull final String firstLine, @NotNull final String secondLine) {
        final String[] parts = firstLine.split(", ");
        final int partsCount = parts.length;

        final String firstPart = parts[0];
        final String quotedFilePath = firstPart.startsWith(FILE) ? firstPart.substring(FILE.length()).trim() : null;
        final String filePath = quotedFilePath == null
            ? null
            : (quotedFilePath.startsWith("\"") && quotedFilePath.endsWith("\"") ? quotedFilePath.substring(1, quotedFilePath.length() - 1) : null);

        int lineNum = INVALID_INT;
        if (partsCount > 1) {
            final String secondPart = parts[1];
            final String lineNumStr = secondPart.startsWith(LINE) ? secondPart.substring(LINE.length()).trim() : null;
            if (lineNumStr != null) {
                lineNum = parseInt(lineNumStr);
            }
        }

        int startPos = INVALID_INT, endPos = INVALID_INT;
        if (partsCount > 2) {
            final String thirdPart = parts[2];
            final String charactersWithColonStr = thirdPart.startsWith(CHARACTERS) ? thirdPart.substring(CHARACTERS.length()).trim() : null;
            if (charactersWithColonStr != null) {
                final String charactersStr = charactersWithColonStr.endsWith(":")
                    ? charactersWithColonStr.substring(0, charactersWithColonStr.length() - 1)
                    : charactersWithColonStr;
                final String[] positions = charactersStr.split("-");
                if (positions.length == 2) {
                    startPos = parseInt(positions[0]);
                    endPos = parseInt(positions[1]);
                }
            }
        }
        
        if (startPos != INVALID_INT) startPos++;
        if (endPos != INVALID_INT) endPos++;

        final boolean isWarning = secondLine.startsWith(WARNING_);

        final FailureMessage.Type type = isWarning ? FailureMessage.Type.WARNING : FailureMessage.Type.ERROR;
        
        String messageText = isWarning ? secondLine.substring(WARNING_.length()).trim() : secondLine;

        if (startPos != INVALID_INT && endPos != INVALID_INT) {
            messageText += " (" + CHARACTERS + " " + startPos + "-" + endPos + ")";
        }

        return new FailureMessage(type, messageText, filePath, lineNum, startPos, endPos);
    }

    private int parseInt(@NotNull final String string) {
        try {
            return Integer.parseInt(string);
        } catch (final NumberFormatException e) {
            return INVALID_INT;
        }
    }

    @Nullable
    private String findUrl(@NotNull final FailureMessage failure) {
        final String path = failure.getFilePath();
        if (path == null) return null;
        final VirtualFile file = LocalFileSystem.getInstance().findFileByPath(path);
        return file == null ? null : file.getUrl();
    }

    @NotNull
    protected OCamlLinkerProcessingItem createFakeProcessingItem(@NotNull final VirtualFile file) {
        return doCreateProcessingItem(null, file, null);
    }

    @NotNull
    protected OCamlLinkerProcessingItem createProcessingItem(@NotNull final VirtualFile file,
                                                             @NotNull final File compiledFile,
                                                             final boolean isDebugMode,
                                                             final boolean force) {
        return createProcessingItem(null, file, compiledFile, isDebugMode, force);
    }

    @NotNull
    protected OCamlLinkerProcessingItem createProcessingItem(@Nullable final OCamlModule ocamlModule,
                                                             @NotNull final VirtualFile file,
                                                             @NotNull final File compiledFile,
                                                             final boolean isDebugMode,
                                                             final boolean force) {
        final ValidityState state = OCamlValidityState.create(file, compiledFile, isDebugMode, force);
        return doCreateProcessingItem(ocamlModule, file, state);
    }

    @NotNull
    private OCamlLinkerProcessingItem doCreateProcessingItem(@Nullable final OCamlModule ocamlModule,
                                                             @NotNull final VirtualFile file,
                                                             @Nullable final ValidityState state) {
        return new OCamlLinkerProcessingItem(ocamlModule, file, state);
    }

    protected static class OCamlLinkerProcessingItem implements FileProcessingCompiler.ProcessingItem {
        @Nullable private final OCamlModule myOCamlModule;
        @NotNull private final VirtualFile myFile;
        @Nullable private final ValidityState myState;

        private OCamlLinkerProcessingItem(@Nullable final OCamlModule OCamlModule, @NotNull final VirtualFile file, @Nullable final ValidityState state) {
            myOCamlModule = OCamlModule;
            myFile = file;
            myState = state;
        }

        @Nullable
        OCamlModule getOCamlModule() {
            return myOCamlModule;
        }

        @NotNull
        public VirtualFile getFile() {
            return myFile;
        }

        @Nullable
        public ValidityState getValidityState() {
            return myState;
        }
    }
}
