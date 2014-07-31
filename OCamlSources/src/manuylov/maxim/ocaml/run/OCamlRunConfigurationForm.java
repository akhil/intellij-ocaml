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

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkListCellRenderer;
import com.intellij.openapi.projectRoots.ui.ProjectJdksEditor;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.SimpleTextAttributes;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 09.04.2010
 */
public class OCamlRunConfigurationForm implements OCamlRunConfigurationParams {
    @NotNull private JPanel myRootPanel;
    @NotNull private TextFieldWithBrowseButton myMainFileEditor;
    @NotNull private RawCommandLineEditor myProgramParamsEditor;
    @NotNull private RawCommandLineEditor myCompilerParamsEditor;
    @NotNull private RawCommandLineEditor myLinkerParamsEditor;
    @NotNull private TextFieldWithBrowseButton myWorkingDirectoryEditor;
    private RawCommandLineEditor myRunnerParamsEditor;
    @NotNull private JRadioButton myUseModuleSdkRadioButton;
    @NotNull private JRadioButton myUseSpecifiedSdkRadioButton;
    @NotNull private JComboBox myModuleComboBox;
    @NotNull private JComboBox mySpecifiedSdkComboBox;
    private JButton myConfigureSdkButton;

    @NotNull private final Project myProject;

    public OCamlRunConfigurationForm(@NotNull final OCamlRunConfiguration configuration) {
        myProject = configuration.getProject();

        final FileChooserDescriptor mlFileChooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false) {
            public boolean isFileVisible(@NotNull final VirtualFile file, final boolean showHiddenFiles) {
                return file.isDirectory() || OCamlFileUtil.isImplementationFile(file);
            }
        };
        mlFileChooserDescriptor.setRoot(myProject.getBaseDir());
        
        final ComponentWithBrowseButton.BrowseFolderActionListener<JTextField> listener =
            new ComponentWithBrowseButton.BrowseFolderActionListener<JTextField>("Select OCaml Application Main File",
                "", myMainFileEditor, myProject, mlFileChooserDescriptor, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT) {
                protected void onFileChoosen(@NotNull final VirtualFile chosenFile) {
                    super.onFileChoosen(chosenFile);
                    setWorkingDirectory(chosenFile.getParent().getPath());
                }
            };

        myMainFileEditor.addActionListener(listener);

        final List<Module> validModules = configuration.getValidModules();
        Collections.sort(validModules, new Comparator<Module>() {
            public int compare(@NotNull final Module module1, @NotNull final Module module2) {
                return module1.getName().compareTo(module2.getName());
            }
        });
        validModules.add(0, null);
        myModuleComboBox.setModel(new CollectionComboBoxModel(validModules, null));
        myModuleComboBox.setRenderer(new ColoredListCellRenderer() {
          @Override
          protected void customizeCellRenderer(@NotNull final JList list,
                                               @Nullable final Object value,
                                               final int index,
                                               final boolean selected,
                                               final boolean hasFocus) {
            if (value == null) {
              append("[none]", SimpleTextAttributes.REGULAR_ATTRIBUTES);
            }
            else {
              final Module module = (Module)value;
              setIcon(module.getModuleType().getNodeIcon(false));
              append(module.getName(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
            }
          }
        });
        myModuleComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(@NotNull final ItemEvent e) {
                myUseModuleSdkRadioButton.setSelected(true);
                myUseSpecifiedSdkRadioButton.setSelected(false);
            }
        });

        final List<Sdk> allSdks = ProjectJdkTable.getInstance().getSdksOfType(OCamlSdkType.getInstance());
        allSdks.add(0, null);
        mySpecifiedSdkComboBox.setModel(new CollectionComboBoxModel(allSdks, null));
        mySpecifiedSdkComboBox.setRenderer(new SdkListCellRenderer("<Project Default>"));
        mySpecifiedSdkComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(@NotNull final ItemEvent e) {
                myUseSpecifiedSdkRadioButton.setSelected(true);
                myUseModuleSdkRadioButton.setSelected(false);
            }
        });

        myConfigureSdkButton.addActionListener(new ActionListener() {
            public void actionPerformed(@NotNull final ActionEvent e) {
                final ProjectJdksEditor editor = new ProjectJdksEditor((Sdk) mySpecifiedSdkComboBox.getSelectedItem(),
                    myProject, mySpecifiedSdkComboBox);
                editor.show();
                if (editor.isOK()) {
                    setSpecifiedSdk(editor.getSelectedJdk());
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

    @Nullable
    public OCamlModule getMainOCamlModule() {
        final String filePath = FileUtil.toSystemIndependentName(myMainFileEditor.getText());
        final VirtualFile file = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (file == null) return null;
        return OCamlModule.getBySourceFile(file, myProject);
    }

    public void setMainOCamlModule(@Nullable final OCamlModule ocamlModule) {
        myMainFileEditor.setText(ocamlModule == null ? "" : FileUtil.toSystemDependentName(ocamlModule.getImplementationFile().getAbsolutePath()));
    }

    @NotNull
    public String getProgramParams() {
        return myProgramParamsEditor.getText();
    }

    public void setProgramParams(@NotNull final String params) {
        myProgramParamsEditor.setText(params);
    }

    public boolean isUsedModuleSdk() {
        return myUseModuleSdkRadioButton.isSelected();
    }

    public void setUsedModuleSdk(final boolean usedModuleSdk) {
        myUseModuleSdkRadioButton.setSelected(usedModuleSdk);
        myUseSpecifiedSdkRadioButton.setSelected(!usedModuleSdk);
    }

    @Nullable
    public Module getModule() {
        return (Module) myModuleComboBox.getSelectedItem();
    }

    public void setModule(@Nullable final Module module) {
        if (module == null) {
            myModuleComboBox.setSelectedItem(null);
            return;
        }
        final String moduleName = module.getName();
        final int count = myModuleComboBox.getItemCount();
        for (int i = 0; i < count; i++) {
            final Module item = (Module) myModuleComboBox.getItemAt(i);
            if (item != null && moduleName.equals(item.getName())) {
                myModuleComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    @Nullable
    public Sdk getSpecifiedSdk() {
        return (Sdk) mySpecifiedSdkComboBox.getSelectedItem();
    }

    public void setSpecifiedSdk(@Nullable final Sdk sdk) {
        if (sdk == null) {
            mySpecifiedSdkComboBox.setSelectedItem(null);
            return;
        }
        final String sdkHome = sdk.getHomePath();
        final int count = mySpecifiedSdkComboBox.getItemCount();
        for (int i = 0; i < count; i++) {
            final Sdk item = (Sdk) mySpecifiedSdkComboBox.getItemAt(i);
            if (item != null && sdkHome.equals(item.getHomePath())) {
                mySpecifiedSdkComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    @NotNull
    public String getCompilerOptions() {
        return myCompilerParamsEditor.getText();
    }

    public void setCompilerOptions(@NotNull final String options) {
        myCompilerParamsEditor.setText(options);
    }

    @NotNull
    public String getLinkerOptions() {
        return myLinkerParamsEditor.getText();
    }

    public void setLinkerOptions(@NotNull final String options) {
        myLinkerParamsEditor.setText(options);
    }

    @NotNull
    public String getRunnerOptions() {
        return myRunnerParamsEditor.getText();
    }

    public void setRunnerOptions(@NotNull final String options) {
        myRunnerParamsEditor.setText(options);
    }

    @NotNull
    public String getWorkingDirectory() {
        return FileUtil.toSystemIndependentName(myWorkingDirectoryEditor.getText());
    }

    public void setWorkingDirectory(@NotNull final String dirPath) {
        myWorkingDirectoryEditor.setText(FileUtil.toSystemDependentName(dirPath));
    }
}
