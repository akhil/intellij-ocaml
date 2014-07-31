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

import com.intellij.ProjectTopics;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.ModuleAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerAdapter;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.util.messages.MessageBusConnection;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 29.04.2010
 */
public class OCamlToolWindowComponent implements ProjectComponent {
    @NotNull private final ToolWindowManager myToolWindowManager;
    @NotNull private final MessageBusConnection myConnection;
    @NotNull private final Project myProject;
    @NotNull private final String TOOL_WINDOW_ID = "OCaml";
    private boolean myToolWindowWasRegistered = false;

    public OCamlToolWindowComponent(@NotNull final Project project) {
        myToolWindowManager = ToolWindowManager.getInstance(project);
        myConnection = project.getMessageBus().connect();
        myConnection.subscribe(ProjectTopics.MODULES, new ModuleAdapter() {
            @Override
            public void moduleAdded(@NotNull final Project project, @NotNull final Module module) {
                registerToolWindowIfNeeded();
            }

            @Override
            public void moduleRemoved(@NotNull final Project project, @NotNull final Module module) {
                unregisterToolWindowIfNeeded();
            }
        });
        myProject = project;
    }

    @NotNull
    public String getComponentName() {
        return "OCAML_TOOL_WINDOW";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
        myConnection.disconnect();
    }

    public void projectOpened() {
        registerToolWindowIfNeeded();
    }

    public void projectClosed() {
        unregisterToolWindowIfNeeded();
    }

    private void registerToolWindowIfNeeded() {
        if (myToolWindowWasRegistered || !toolWindowShouldBeRegistered()) return;

        final ToolWindow toolWindow = myToolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.BOTTOM, false);
        toolWindow.setIcon(OCamlIconUtil.getSmallOCamlIcon());
        toolWindow.setTitle(TOOL_WINDOW_ID);

        myToolWindowWasRegistered = true;

        final ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContentManagerListener(new ContentManagerAdapter() {
            @Override
            public void contentRemoved(@NotNull final ContentManagerEvent event) {
                if (contentManager.getContentCount() == 0) {
                    OCamlToolWindowUtil.addAndSelectStartContent(myProject, contentManager);
                }
            }
        });

        OCamlToolWindowUtil.addAndSelectStartContent(myProject, contentManager);
    }

    private void unregisterToolWindowIfNeeded() {
        if (myToolWindowWasRegistered && !toolWindowShouldBeRegistered()) {
            myToolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
            myToolWindowWasRegistered = false;
        }
    }

    private boolean toolWindowShouldBeRegistered() {
        final Module[] modules = ModuleManager.getInstance(myProject).getModules();
        for (final Module module : modules) {
            if (OCamlModuleUtil.isOCamlModule(module)) {
                return true;
            }
        }
        return false;
    }
}
