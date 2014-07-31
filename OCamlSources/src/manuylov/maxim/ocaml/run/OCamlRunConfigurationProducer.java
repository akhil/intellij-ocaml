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

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.junit.RuntimeConfigurationProducer;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 07.04.2010
 */
public class OCamlRunConfigurationProducer extends RuntimeConfigurationProducer implements Cloneable {
    @Nullable private PsiFile mySourceFile = null;

    public OCamlRunConfigurationProducer() {
        super(OCamlConfigurationType.getInstance());
    }

    @Nullable
    public PsiElement getSourceElement() {
        return mySourceFile;
    }

    @Nullable
    protected RunnerAndConfigurationSettingsImpl createConfigurationByElement(@NotNull final Location location,
                                                                              @NotNull final ConfigurationContext context) {
        final PsiFile psiFile = location.getPsiElement().getContainingFile();
        if (psiFile == null || !OCamlFileUtil.isImplementationFile(psiFile)) {
            return null;
        }
        final Project project = psiFile.getProject();
        final VirtualFile virtualFile = psiFile.getVirtualFile();
        if (virtualFile == null) {
            return null;
        }
        final OCamlModule ocamlModule = OCamlModule.getBySourceFile(virtualFile, project);
        if (ocamlModule == null) {
            return null;
        }
        mySourceFile = psiFile;

        final RunnerAndConfigurationSettingsImpl settings = cloneTemplateConfiguration(project, context);
        final OCamlRunConfiguration configuration = (OCamlRunConfiguration) settings.getConfiguration();
        configuration.setMainOCamlModule(ocamlModule);
        configuration.setWorkingDirectory(FileUtil.toSystemDependentName(ocamlModule.getSourcesDir().getPath()));
        configuration.setName(configuration.suggestedName());
        final Module module = ModuleUtil.findModuleForPsiElement(psiFile);
        configuration.setUsedModuleSdk(module != null);
        configuration.setModule(module);
        copyStepsBeforeRun(project, configuration);
        return settings;
    }

    public int compareTo(@Nullable final Object obj) {
        return 0;
    }
}