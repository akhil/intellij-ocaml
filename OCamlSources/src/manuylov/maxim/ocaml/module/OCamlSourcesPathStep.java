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

import com.intellij.CommonBundle;
import com.intellij.ide.util.BrowseFilesListener;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.FieldPanel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Maxim.Manuylov
 *         Date: 14.05.2010
 */
class OCamlSourcesPathStep extends ModuleWizardStep {
    private final OCamlModuleBuilder myBuilder;
    private final Icon myIcon;
    private final String myHelpId;
    private JRadioButton myRbCreateSource;
    private JRadioButton myRbNoSource;
    private JTextField myTfSourceDirectoryName;
    private JTextField myTfFullPath;

    public OCamlSourcesPathStep(@NotNull final OCamlModuleBuilder builder, @Nullable final Icon icon, @NonNls @Nullable final String helpId) {
        myBuilder = builder;
        myIcon = icon;
        myHelpId = helpId;
    }

    @NotNull
    public JComponent getComponent() {
        final JPanel panel = new JPanel(new GridBagLayout());

        final JLabel label = new JLabel("Please specify module sources directory.");

        panel.add(label, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));
        myRbCreateSource = new JRadioButton("Create source directory", myBuilder.isShouldCreateSourcesDir());
        panel.add(myRbCreateSource, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));

        myTfSourceDirectoryName = new JTextField(myBuilder.getRelativeSourcesPath());
        final JLabel srcPathLabel = new JLabel("Enter relative path to module content root:");
        panel.add(srcPathLabel, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(8, 30, 0, 0), 0, 0));
        final FileChooserDescriptor chooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        chooserDescriptor.setIsTreeRootVisible(true);
        final FieldPanel fieldPanel = createFieldPanel(myTfSourceDirectoryName, null, new BrowsePathListener(myTfSourceDirectoryName, chooserDescriptor));
        panel.add(fieldPanel, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(8, 30, 0, 10), 0, 0));

        myRbNoSource = new JRadioButton("Do not create source directory", !myBuilder.isShouldCreateSourcesDir());
        panel.add(myRbNoSource, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));

        final JLabel fullPathLabel = new JLabel("The following directory will be marked as a source directory:");
        panel.add(fullPathLabel, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));

        myTfFullPath = new JTextField();
        myTfFullPath.setEditable(false);
        final Insets borderInsets = myTfFullPath.getBorder().getBorderInsets(myTfFullPath);
        myTfFullPath.setBorder(BorderFactory.createEmptyBorder(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right));
        panel.add(myTfFullPath, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 1.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(8, 10, 8, 10), 0, 0));

        ButtonGroup group = new ButtonGroup();
        group.add(myRbCreateSource);
        group.add(myRbNoSource);
        myTfSourceDirectoryName.getDocument().addDocumentListener(new DocumentAdapter() {
            public void textChanged(DocumentEvent event) {
                updateFullPathField();
            }
        });
        updateFullPathField();

        final StateUpdater stateUpdater = new StateUpdater() {
            public void updateState(final boolean shouldCreateSourcesDir) {
                srcPathLabel.setEnabled(shouldCreateSourcesDir);
                fieldPanel.setEnabled(shouldCreateSourcesDir);
                fullPathLabel.setVisible(shouldCreateSourcesDir);
                myTfFullPath.setVisible(shouldCreateSourcesDir);
                if (shouldCreateSourcesDir) {
                    myTfSourceDirectoryName.requestFocus();
                }
            }
        };

        myRbCreateSource.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                stateUpdater.updateState(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        stateUpdater.updateState(myBuilder.isShouldCreateSourcesDir());

        return panel;
    }

    private void updateFullPathField() {
        final String sourceDirectoryPath = getSourceDirectoryPath();
        if (sourceDirectoryPath != null) {
            myTfFullPath.setText(sourceDirectoryPath.replace('/', File.separatorChar));
        } else {
            myTfFullPath.setText("");
        }
    }

    public JComponent getPreferredFocusedComponent() {
        return myBuilder.isShouldCreateSourcesDir() ? myTfSourceDirectoryName : myRbNoSource;
    }

    public void updateDataModel() {
        myBuilder.setShouldCreateSourcesDir(myRbCreateSource.isSelected());
        myBuilder.setRelativeSourcesPath(myTfSourceDirectoryName.getText());
    }

    public boolean validate() throws ConfigurationException {
        if (!super.validate()) {
            return false;
        }

        if (myRbCreateSource.isSelected()) {
            final String sourceDirectoryPath = getSourceDirectoryPath();
            final String relativePath = myTfSourceDirectoryName.getText().trim();
            if (relativePath.length() == 0) {
                final String contentRoot = sourceDirectoryPath == null ? " " : "\n\"" + FileUtil.toSystemDependentName(sourceDirectoryPath) + "\"\n";
                final String text = "Relative path to sources is empty.\nWould you like to mark the module content root" + contentRoot + "as a source directory?";
                final int answer = Messages.showDialog(myTfSourceDirectoryName, text, "Mark source directory",
                    new String[] {"Mark", "Do Not Mark", CommonBundle.getCancelButtonText()}, 0, Messages.getQuestionIcon());
                if (answer == 2) {
                    return false; // cancel
                }
                if (answer == 1) { // don't mark
                    myRbNoSource.doClick();
                }
            }
            if (sourceDirectoryPath != null) {
                final File rootDir = new File(getContentRootPath());
                final File srcDir = new File(sourceDirectoryPath);
                try {
                    if (!FileUtil.isAncestor(rootDir, srcDir, false)) {
                        Messages.showErrorDialog(myTfSourceDirectoryName,
                            "Source directory should be under module content root directory",
                            CommonBundle.getErrorTitle());
                        return false;
                    }
                }
                catch (IOException e) {
                    Messages.showErrorDialog(myTfSourceDirectoryName, e.getMessage(), CommonBundle.getErrorTitle());
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    private String getSourceDirectoryPath() {
        final String contentEntryPath = getContentRootPath();
        final String dirName = myTfSourceDirectoryName.getText().trim().replace(File.separatorChar, '/');
        if (contentEntryPath != null) {
            return dirName.length() > 0 ? contentEntryPath + "/" + dirName : contentEntryPath;
        }
        return null;
    }

    @Nullable
    private String getContentRootPath() {
        return myBuilder.getContentEntryPath();
    }

    private class BrowsePathListener extends BrowseFilesListener {
        private final FileChooserDescriptor myChooserDescriptor;
        private final JTextField myField;

        public BrowsePathListener(JTextField textField, final FileChooserDescriptor chooserDescriptor) {
            super(textField, "Select source directory", "", chooserDescriptor);
            myChooserDescriptor = chooserDescriptor;
            myField = textField;
        }

        @Nullable
        private VirtualFile getContentEntryDir() {
            final String contentEntryPath = getContentRootPath();
            if (contentEntryPath != null) {
                return ApplicationManager.getApplication().runWriteAction(new Computable<VirtualFile>() {
                    public VirtualFile compute() {
                        return LocalFileSystem.getInstance().refreshAndFindFileByPath(contentEntryPath);
                    }
                });
            }
            return null;
        }

        public void actionPerformed(ActionEvent e) {
            final VirtualFile contentEntryDir = getContentEntryDir();
            if (contentEntryDir != null) {
                myChooserDescriptor.setRoot(contentEntryDir);
                final String textBefore = myField.getText().trim();
                super.actionPerformed(e);
                if (!textBefore.equals(myField.getText().trim())) {
                    final String fullPath = myField.getText().trim().replace(File.separatorChar, '/');
                    final VirtualFile fileByPath = LocalFileSystem.getInstance().findFileByPath(fullPath);
                    assert fileByPath != null;
                    myField.setText(VfsUtil.getRelativePath(fileByPath, contentEntryDir, File.separatorChar));
                }
            }
        }
    }

    public Icon getIcon() {
        return myIcon;
    }

    public String getHelpId() {
        return myHelpId;
    }

    private interface StateUpdater {
        void updateState(final boolean shouldCreateSourcesDir);
    }
}
