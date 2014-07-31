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

package manuylov.maxim.ocaml.toolWindow;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkListCellRenderer;
import com.intellij.openapi.projectRoots.ui.ProjectJdksEditor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.RawCommandLineEditor;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 13.04.2010
 */
public class OCamlToolWindowSettingsForm {
    private RawCommandLineEditor myCommandLineParamsEditor;
    private JComboBox mySdkComboBox;
    private JButton myConfigureButton;
    private JPanel myRootPanel;
    private TextFieldWithBrowseButton myWorkingDirectoryEditor;

    @NotNull private final Project myProject;

    public OCamlToolWindowSettingsForm(@NotNull final Project project) {
        myProject = project;

        final List<Sdk> allSdks = ProjectJdkTable.getInstance().getSdksOfType(OCamlSdkType.getInstance());
        allSdks.add(0, null);
        mySdkComboBox.setModel(new CollectionComboBoxModel(allSdks, null));
        mySdkComboBox.setRenderer(new SdkListCellRenderer("<Project Default>"));

        myConfigureButton.addActionListener(new ActionListener() {
            public void actionPerformed(@NotNull final ActionEvent e) {
                final ProjectJdksEditor editor = new ProjectJdksEditor((Sdk) mySdkComboBox.getSelectedItem(),
                    myProject, mySdkComboBox);
                editor.show();
                if (editor.isOK()) {
                    setSelectedSdk(editor.getSelectedJdk());
                }
            }
        });

        final FileChooserDescriptor workingDirChooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        workingDirChooserDescriptor.setRoot(myProject.getBaseDir());
        myWorkingDirectoryEditor.addBrowseFolderListener("Select Working Directory", "", myProject, workingDirChooserDescriptor);
    }

    @NotNull
    public JComponent getRootPanel() {
        return myRootPanel;
    }

    @NotNull
    public JComboBox getSdkComboBox() {
        return mySdkComboBox;
    }

    @NotNull
    public String getCmdParams() {
        return myCommandLineParamsEditor.getText();
    }

    public void setCmdParams(@NotNull final String params) {
        myCommandLineParamsEditor.setText(params);
    }

    @NotNull
    public String getWorkingDirectory() {
        return myWorkingDirectoryEditor.getText();
    }

    public void setWorkingDirectory(@NotNull final String dir) {
        myWorkingDirectoryEditor.setText(dir);
    }

    @Nullable
    public Sdk getSelectedSdk() {
        return (Sdk) mySdkComboBox.getSelectedItem();
    }

    public void setSelectedSdk(@Nullable final Sdk sdk) {
        if (sdk == null) {
            mySdkComboBox.setSelectedItem(null);
            return;
        }
        final String sdkHome = sdk.getHomePath();
        final int count = mySdkComboBox.getItemCount();
        for (int i = 0; i < count; i++) {
            final Sdk item = (Sdk) mySdkComboBox.getItemAt(i);
            if (item != null && sdkHome.equals(item.getHomePath())) {
                mySdkComboBox.setSelectedIndex(i);
                return;
            }
        }
    }
}
