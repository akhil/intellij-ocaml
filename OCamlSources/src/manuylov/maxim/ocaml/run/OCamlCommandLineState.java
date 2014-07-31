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

package manuylov.maxim.ocaml.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.text.StringUtil;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Maxim.Manuylov
 *         Date: 07.04.2010
 */
public class OCamlCommandLineState extends CommandLineState {
    @NotNull private final OCamlRunConfigurationParams myConfig;
    @NotNull private final Executor myExecutor;
    @NotNull private final Project myProject;

    public OCamlCommandLineState(@NotNull final OCamlRunConfiguration config,
                                 @NotNull final Executor executor,
                                 @NotNull final ExecutionEnvironment env) {
        super(env);
        myConfig = config;
        myExecutor = executor;
        myProject = config.getProject();
        setConsoleBuilder(TextConsoleBuilderFactory.getInstance().createBuilder(myProject));
    }

    @Override
    @NotNull
    protected OSProcessHandler startProcess() throws ExecutionException {
        final GeneralCommandLine cmd = generateCommandLine();
        final OSProcessHandler processHandler = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
        ProcessTerminatedListener.attach(processHandler);
        return processHandler;
    }

    @NotNull
    private GeneralCommandLine generateCommandLine() { //todo implement properly for debugging and add profiling
        final Sdk sdk = getSdk();
        final String sdkHome = sdk.getHomePath();
        String workingDirectory = myConfig.getWorkingDirectory();
        if (StringUtil.isEmptyOrSpaces(workingDirectory)) {
            workingDirectory = sdkHome;
        }
        final OCamlModule ocamlModule = myConfig.getMainOCamlModule();
        assert ocamlModule != null;
        final File executableFile = ocamlModule.getCompiledExecutableFile();

        final GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setWorkDirectory(workingDirectory);
        if (DefaultDebugExecutor.getDebugExecutorInstance().getId().equals(myExecutor.getId())) {
            cmd.setExePath(OCamlSdkType.getDebuggerExecutable(sdkHome).getAbsolutePath());
        }
        else {
            cmd.setExePath(OCamlSdkType.getByteCodeInterpreterExecutable(sdkHome).getAbsolutePath());
        }
        cmd.getParametersList().addParametersString(myConfig.getRunnerOptions());
        cmd.addParameter(executableFile.getAbsolutePath());
        cmd.getParametersList().addParametersString(myConfig.getProgramParams());

        return cmd;
    }

    @NotNull
    private Sdk getSdk() {
        Sdk sdk;
        if (myConfig.isUsedModuleSdk()) {
            final Module module = myConfig.getModule();
            assert module != null;
            sdk = ModuleRootManager.getInstance(module).getSdk();
        }
        else {
            sdk = myConfig.getSpecifiedSdk();
            if (sdk == null) {
                sdk = ProjectRootManager.getInstance(myProject).getProjectJdk();
            }
        }
        assert OCamlModuleUtil.isOCamlSdk(sdk);
        //noinspection ConstantConditions
        return sdk;
    }
}
