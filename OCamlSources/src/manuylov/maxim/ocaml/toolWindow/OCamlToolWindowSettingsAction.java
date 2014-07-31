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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import manuylov.maxim.ocaml.settings.OCamlSettings;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class OCamlToolWindowSettingsAction extends AnAction {
    @NotNull private final Project myProject;
    @Nullable private final Runnable myAction;

    public OCamlToolWindowSettingsAction(@NotNull final Project project, @Nullable final Runnable action) {
        super(null, "Configure settings", OCamlIconUtil.getSettingsIcon());
        myProject = project;
        myAction = action;
    }

    @Override
    public void actionPerformed(final AnActionEvent e) {
        showSettingsDialog();
    }

    public void showSettingsDialog() {
        final OCamlToolWindowSettingsForm settingsForm = new OCamlToolWindowSettingsForm(myProject);
        settingsForm.setSelectedSdk(OCamlSettings.getInstance().getTopLevelSdk());
        settingsForm.setCmdParams(OCamlSettings.getInstance().getTopLevelCmdOptions());
        settingsForm.setWorkingDirectory(OCamlSettings.getInstance().getTopLevelCmdWorkingDir());

        final DialogBuilder dialogBuilder = new DialogBuilder(myProject);
        dialogBuilder.setCenterPanel(settingsForm.getRootPanel());
        dialogBuilder.addOkAction().setText("Ok");
        dialogBuilder.addCancelAction().setText("Cancel");
        dialogBuilder.setPreferedFocusComponent(settingsForm.getSdkComboBox());
        dialogBuilder.setTitle("OCaml Top Level Console Settings");
        dialogBuilder.setOkOperation(new Runnable() {
            public void run() {
                OCamlSettings.getInstance().setTopLevelSdk(settingsForm.getSelectedSdk());
                OCamlSettings.getInstance().setTopLevelCmdOptions(settingsForm.getCmdParams());
                OCamlSettings.getInstance().setTopLevelCmdWorkingDir(settingsForm.getWorkingDirectory());
                dialogBuilder.getWindow().setVisible(false);
                if (myAction != null) {
                    myAction.run();
                }
            }
        });
        dialogBuilder.show();
    }
}
