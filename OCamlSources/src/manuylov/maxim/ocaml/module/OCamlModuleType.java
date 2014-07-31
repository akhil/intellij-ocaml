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

package manuylov.maxim.ocaml.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectWizardStepFactory;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public class OCamlModuleType extends ModuleType<OCamlModuleBuilder> {
    @NotNull
    public static final String ID = "OCAML_MODULE";

    @NotNull
    public static OCamlModuleType getInstance() {
        return (OCamlModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @Override
    public ModuleWizardStep[] createWizardSteps(final WizardContext wizardContext,
                                                final OCamlModuleBuilder moduleBuilder,
                                                final ModulesProvider modulesProvider) {
        final ArrayList<ModuleWizardStep> steps = new ArrayList<ModuleWizardStep>();
        steps.add(new OCamlSourcesPathStep(moduleBuilder, null, null));
        steps.add(new OCamlSdkSelectStep(moduleBuilder, null, null, wizardContext.getProject()));
        final ModuleWizardStep supportForFrameworksStep = ProjectWizardStepFactory.getInstance().createSupportForFrameworksStep(wizardContext, moduleBuilder);
        if (supportForFrameworksStep != null) {
            steps.add(supportForFrameworksStep);
        }
        return steps.toArray(new ModuleWizardStep[steps.size()]);
    }

    public OCamlModuleType() {
        super(ID);
    }

    @NotNull
    public OCamlModuleBuilder createModuleBuilder() {
        return new OCamlModuleBuilder();
    }

    @NotNull
    public String getName() {
        return "OCaml Module";
    }

    @NotNull
    public String getDescription() {
        return "Provides facilities for developing <strong>OCaml</strong> applications.";
    }

    @NotNull
    public Icon getBigIcon() {
        return OCamlIconUtil.getBigOCamlIcon();
    }

    @NotNull
    public Icon getNodeIcon(final boolean isOpened) {
        return OCamlIconUtil.getSmallOCamlIcon();
    }
}
