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
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import manuylov.maxim.ocaml.settings.OCamlSettings;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
@SuppressWarnings({"ComponentNotRegistered"})
public class OCamlToolWindowOpenCloseAction extends AnAction {
    @NotNull private final ContentManager myContentManager;
    @NotNull private final Project myProject;
    private final boolean myOpenConsole;
    private final boolean myCloseView;

    public OCamlToolWindowOpenCloseAction(@NotNull final Project project, @NotNull final ContentManager contentManager, final boolean openConsole, final boolean closeView) {
        super(null, openConsole ? (closeView ? "Open OCaml top level interactive console" : "Open one more OCaml top level interactive console") : "Close",
            openConsole ? OCamlIconUtil.getOpenConsoleIcon() : OCamlIconUtil.getCloseViewIcon());
        myContentManager = contentManager;
        myProject = project;
        myOpenConsole = openConsole;
        myCloseView = closeView;
    }

    @Override
    public void actionPerformed(final AnActionEvent e) {
        if (myOpenConsole) {
            final Sdk topLevelSdk = OCamlSettings.getInstance().getTopLevelSdk();
            if (topLevelSdk == null) {
                final Sdk projectSdk = ProjectRootManager.getInstance(myProject).getProjectJdk();
                if (!OCamlModuleUtil.isOCamlSdk(projectSdk)) {
                    Messages.showErrorDialog("Please select OCaml SDK to run top level interactive console (project default SDK is not a valid OCaml SDK).", "Error");
                    final OCamlToolWindowSettingsAction settingsAction = new OCamlToolWindowSettingsAction(myProject, new Runnable() {
                        public void run() {
                            Sdk choosenSdk = OCamlSettings.getInstance().getTopLevelSdk();
                            if (choosenSdk == null) {
                                choosenSdk = ProjectRootManager.getInstance(myProject).getProjectJdk();
                            }
                            if (choosenSdk != null) {
                                OCamlToolWindowUtil.addAndSelectTopLevelConsoleContent(myProject, myContentManager, choosenSdk);
                            }
                        }
                    });
                    settingsAction.showSettingsDialog();
                }
                else {
                    OCamlToolWindowUtil.addAndSelectTopLevelConsoleContent(myProject, myContentManager, projectSdk);
                }
            }
            else {
                OCamlToolWindowUtil.addAndSelectTopLevelConsoleContent(myProject, myContentManager, topLevelSdk);
            }
        }
        if (myCloseView && myContentManager.isSingleSelection()) {
            final Content selectedContent = myContentManager.getSelectedContent();
            if (selectedContent != null) {
                myContentManager.removeContent(selectedContent, true);
            }
        }
    }
}
