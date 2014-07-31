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

import com.intellij.ide.util.projectWizard.JdkChooserPanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.MultiLineLabelUI;
import com.intellij.util.ui.UIUtil;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Maxim.Manuylov
 *         Date: 03.04.2010
 */
class OCamlSdkChooserPanel extends JComponent {
    @NotNull private final JdkChooserPanel myJdkChooser;

    public OCamlSdkChooserPanel(final Project project) {
        myJdkChooser = new JdkChooserPanel(project);

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder());

        final JLabel label = new JLabel("Specify the OCaml binaries directory");
        label.setUI(new MultiLineLabelUI());
        add(label, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.HORIZONTAL, new Insets(8, 10, 8, 10), 0, 0));

        final JLabel jdkLabel = new JLabel("OCaml SDK version:");
        jdkLabel.setFont(UIUtil.getLabelFont().deriveFont(Font.BOLD));
        add(jdkLabel, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));

        add(myJdkChooser, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.BOTH, new Insets(2, 10, 10, 5), 0, 0));
        JButton configureButton = new JButton("Configure...");
        add(configureButton, new GridBagConstraints(1, GridBagConstraints.RELATIVE, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST,
            GridBagConstraints.NONE, new Insets(2, 0, 10, 5), 0, 0));

        configureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myJdkChooser.editJdkTable();
            }
        });

        myJdkChooser.setAllowedJdkTypes(new SdkType[] { OCamlSdkType.getInstance() });

        final Sdk selectedJdk = project == null ? null : ProjectRootManager.getInstance(project).getProjectJdk();
        myJdkChooser.updateList(selectedJdk, null);
    }

    @Nullable
    public Sdk getChosenJdk() {
        return myJdkChooser.getChosenJdk();
    }

    public JComponent getPreferredFocusedComponent() {
        return myJdkChooser;
    }

    public void selectSdk(@Nullable final Sdk sdk) {
        myJdkChooser.selectJdk(sdk);
    }
}