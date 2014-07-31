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

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.ui.content.ContentManager;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import manuylov.maxim.ocaml.settings.OCamlSettings;
import manuylov.maxim.ocaml.util.OCamlSystemUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
class OCamlTopLevelConsoleView extends BaseOCamlToolWindowView {
    private static int ourLastConsoleNumber = 0;
    private final int myConsoleNumber;

    @NotNull private final ConsoleView myConsoleView;
    @NotNull private final OSProcessHandler myProcessHandler;

    public OCamlTopLevelConsoleView(@NotNull final Project project, @NotNull final ContentManager contentManager, final Sdk topLevelSdk) throws ExecutionException {
        super(project, contentManager);
        myConsoleNumber = ++ourLastConsoleNumber;

        final GeneralCommandLine cmd = createCommandLine(topLevelSdk);
        myConsoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        myProcessHandler = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
        myConsoleView.attachToProcess(myProcessHandler);
        ProcessTerminatedListener.attach(myProcessHandler);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        final DefaultActionGroup group = new DefaultActionGroup();
        group.add(getOCamlToolWindowOpenCloseAction(true, false));
        group.addAll(myConsoleView.createConsoleActions());
        group.add(getOCamlToolWindowSettingsAction());
        group.add(getOCamlToolWindowOpenCloseAction(false, true));
        final JComponent toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, group, false).getComponent();
        toolbar.setMaximumSize(new Dimension(toolbar.getPreferredSize().width, Integer.MAX_VALUE));

        add(toolbar);
        add(myConsoleView.getComponent());

        myConsoleView.getComponent().requestFocus();
        myProcessHandler.startNotify();
    }

    public int getConsoleNumber() {
        return myConsoleNumber;
    }

    @NotNull
    private GeneralCommandLine createCommandLine(@NotNull final Sdk topLevelSdk) {
        final String sdkHomePath = topLevelSdk.getHomePath();
        final String consoleExePath = OCamlSdkType.getTopLevelExecutable(sdkHomePath).getAbsolutePath();
        final GeneralCommandLine cmd = new GeneralCommandLine();
        final OCamlSettings settings = OCamlSettings.getInstance();
        final String workingDir = settings.getTopLevelCmdWorkingDir().trim();
        cmd.setWorkDirectory(workingDir.isEmpty() ? sdkHomePath : workingDir);
        cmd.setExePath(consoleExePath);
        OCamlSystemUtil.addStdPaths(cmd, topLevelSdk);
        final String cmdOptions = settings.getTopLevelCmdOptions().trim();
        if (!cmdOptions.isEmpty()) {
            cmd.getParametersList().addParametersString(cmdOptions);
        }
        return cmd;
    }

    @Override
    public void dispose() {
        myConsoleView.dispose();
        myProcessHandler.destroyProcess();
    }
}