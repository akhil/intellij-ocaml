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
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 07.04.2010
 */
public class OCamlRunConfiguration extends ModuleBasedConfiguration<RunConfigurationModule> implements RunConfiguration, OCamlRunConfigurationParams {
    @Nullable private VirtualFile myMLFile = null;
    @NotNull private String myProgramParams = "";
    private boolean myIsUsedModuleSdk = true;
    @Nullable private Sdk mySpecifiedSdk = null;
    @NotNull private String myCompilerOptions = "";
    @NotNull private String myLinkerOptions = "";
    @NotNull private String myRunnerOptions = "";
    @NotNull private String myWorkingDirectory = "";

    public OCamlRunConfiguration(@NotNull final RunConfigurationModule runConfigurationModule,
                                 @NotNull final ConfigurationFactory factory,
                                 @NotNull final String name) {
        super(name, runConfigurationModule, factory);
    }

    @Override
    @NotNull
    public List<Module> getValidModules() {
        final Module[] modules = ModuleManager.getInstance(getProject()).getModules();
        final List<Module> result = new ArrayList<Module>();
        for (final Module module : modules) {
          if (OCamlModuleUtil.isOCamlModule(module)) {
            result.add(module);
          }
        }
        return result;
    }

    @Override
    @NotNull
    protected ModuleBasedConfiguration createInstance() {
        return new OCamlRunConfiguration(getConfigurationModule(), getFactory(), getName());
    }

    @NotNull
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new OCamlRunConfigurationEditor(this); 
    }

    @NotNull
    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment env) throws ExecutionException {
        return new OCamlCommandLineState(this, executor, env);
    }

    @Override
    public void readExternal(@NotNull final Element element) throws InvalidDataException {
        super.readExternal(element);
        myMLFile = null;
        final String mlFilePath = JDOMExternalizerUtil.readField(element, "ocamlModule");
        if (mlFilePath != null) {
            myMLFile = LocalFileSystem.getInstance().findFileByPath(FileUtil.toSystemDependentName(mlFilePath));
        }
        myProgramParams = JDOMExternalizerUtil.readField(element, "programParams");
        myIsUsedModuleSdk = Boolean.valueOf(JDOMExternalizerUtil.readField(element, "useModuleSdk"));
        readModule(element);
        mySpecifiedSdk = null;
        final String systemIndependentSdkHomePath = JDOMExternalizerUtil.readField(element, "specifiedSdkHome");
        if (systemIndependentSdkHomePath != null) {
            final String sdkHomePath = FileUtil.toSystemDependentName(systemIndependentSdkHomePath);
            if (sdkHomePath != null) {
                final List<Sdk> sdks = ProjectJdkTable.getInstance().getSdksOfType(OCamlSdkType.getInstance());
                for (final Sdk sdk : sdks) {
                    if (sdkHomePath.equals(sdk.getHomePath())) {
                        mySpecifiedSdk = sdk;
                        break;
                    }
                }
            }
        }
        myCompilerOptions = JDOMExternalizerUtil.readField(element, "compilerOptions");
        myLinkerOptions = JDOMExternalizerUtil.readField(element, "linkerOptions");
        myRunnerOptions = JDOMExternalizerUtil.readField(element, "runnerOptions");
        myWorkingDirectory = FileUtil.toSystemDependentName(JDOMExternalizerUtil.readField(element, "workingDirectory"));
    }

    @Override
    public void writeExternal(@NotNull final Element element) throws WriteExternalException {
        super.writeExternal(element);
        if (myMLFile != null) {
            final String mlFilePath = FileUtil.toSystemIndependentName(myMLFile.getPath());
            JDOMExternalizerUtil.writeField(element, "ocamlModule", mlFilePath);
        }
        JDOMExternalizerUtil.writeField(element, "programParams", myProgramParams);
        JDOMExternalizerUtil.writeField(element, "useModuleSdk", String.valueOf(myIsUsedModuleSdk));
        writeModule(element);
        if (mySpecifiedSdk != null) {
            final String sdkHomePath = FileUtil.toSystemIndependentName(mySpecifiedSdk.getHomePath());
            JDOMExternalizerUtil.writeField(element, "specifiedSdkHome", sdkHomePath);
        }
        JDOMExternalizerUtil.writeField(element, "compilerOptions", myCompilerOptions);
        JDOMExternalizerUtil.writeField(element, "linkerOptions", myLinkerOptions);
        JDOMExternalizerUtil.writeField(element, "runnerOptions", myRunnerOptions);
        JDOMExternalizerUtil.writeField(element, "workingDirectory", FileUtil.toSystemIndependentName(myWorkingDirectory));
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        super.checkConfiguration();

        if (myMLFile == null) {
            throw new RuntimeConfigurationException("Application main file must be an OCaml source implementation file and must be located under the some OCaml module source root.");
        }

        if (myIsUsedModuleSdk) {
            final Module module = getModule();
            if (module == null) {
                throw new RuntimeConfigurationException("Please choose the valid OCaml module or select the \"Use specified SDK\" option.");
            }
            final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
            if (!OCamlModuleUtil.isOCamlSdk(sdk)) {
                throw new RuntimeConfigurationException("There is no valid OCaml SDK in the specified module.");
            }
        }
        else {
            if (mySpecifiedSdk == null) {
                final Sdk projectSdk = ProjectRootManager.getInstance(getProject()).getProjectJdk();
                if (!OCamlModuleUtil.isOCamlSdk(projectSdk)) {
                    throw new RuntimeConfigurationException("Project default SDK is not a valid OCaml SDK.");
                }
            }
            else if (!OCamlModuleUtil.isOCamlSdk(mySpecifiedSdk)) {
                throw new RuntimeConfigurationException("The specified SDK is not a valid OCaml SDK.");
            }
        }
    }

    @Nullable
    private OCamlModule getOCamlModule() {
        //noinspection ConstantConditions
        return myMLFile == null ? null : OCamlModule.getBySourceFile(myMLFile, getProject());
    }

    @Override
    public boolean isGeneratedName() {
      return Comparing.equal(getName(), suggestedName());
    }

    @Override
    @NotNull
    public String suggestedName() {
        final OCamlModule ocamlModule = getOCamlModule();
        return ocamlModule == null ? "Unnamed" : ocamlModule.getName();
    }

    @Nullable
    public OCamlModule getMainOCamlModule() {
        return getOCamlModule();
    }

    public void setMainOCamlModule(@Nullable final OCamlModule ocamlModule) {
        myMLFile = ocamlModule == null ? null : LocalFileSystem.getInstance().findFileByIoFile(ocamlModule.getImplementationFile());
    }

    @NotNull
    public String getProgramParams() {
        return myProgramParams;
    }

    public void setProgramParams(@NotNull final String params) {
        myProgramParams = params;
    }

    public boolean isUsedModuleSdk() {
        return myIsUsedModuleSdk;
    }

    public void setUsedModuleSdk(final boolean usedModuleSdk) {
        myIsUsedModuleSdk = usedModuleSdk;
    }

    @Nullable
    public Module getModule() {
        return getConfigurationModule().getModule();
    }

    @Nullable
    public Sdk getSpecifiedSdk() {
        return mySpecifiedSdk;
    }

    public void setSpecifiedSdk(@Nullable final Sdk sdk) {
        mySpecifiedSdk = sdk;
    }

    @NotNull
    public String getCompilerOptions() {
        return myCompilerOptions;
    }

    public void setCompilerOptions(@NotNull final String options) {
        myCompilerOptions = options;
    }

    @NotNull
    public String getLinkerOptions() {
        return myLinkerOptions;
    }

    public void setLinkerOptions(@NotNull final String options) {
        myLinkerOptions = options;
    }

    @NotNull
    public String getRunnerOptions() {
        return myRunnerOptions;
    }

    public void setRunnerOptions(@NotNull final String options) {
        myRunnerOptions = options;
    }

    @NotNull
    public String getWorkingDirectory() {
        return myWorkingDirectory;
    }

    public void setWorkingDirectory(@NotNull final String dirPath) {
        myWorkingDirectory = dirPath;
    }
}
