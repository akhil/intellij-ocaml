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
import com.intellij.openapi.project.Project;
import manuylov.maxim.ocaml.module.OCamlSdkChooserPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 03.04.2010
 */
class OCamlSdkSelectStep extends ModuleWizardStep {
    private final OCamlModuleBuilder myModuleBuilder;
    private final OCamlSdkChooserPanel myPanel;
    private final String myHelp;
    private final Icon myIcon;

    public OCamlSdkSelectStep(@NotNull final OCamlModuleBuilder moduleBuilder,
                              @Nullable final Icon icon,
                              @Nullable final String helpId,
                              @Nullable final Project project) {
        super();
        myIcon = icon;
        myModuleBuilder = moduleBuilder;
        myPanel = new OCamlSdkChooserPanel(project);
        myHelp = helpId;
    }

    public String getHelpId() {
        return myHelp;
    }

    public JComponent getPreferredFocusedComponent() {
        return myPanel.getPreferredFocusedComponent();
    }

    public JComponent getComponent() {
        return myPanel;
    }

    public void updateDataModel() {
        myModuleBuilder.setSdk(myPanel.getChosenJdk());
    }

    public Icon getIcon() {
        return myIcon;
    }

    public boolean validate() {
        return true;
    }
}