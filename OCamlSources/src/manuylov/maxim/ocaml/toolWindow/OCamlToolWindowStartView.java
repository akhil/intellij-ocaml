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

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
class OCamlToolWindowStartView extends BaseOCamlToolWindowView {
    public OCamlToolWindowStartView(@NotNull final Project project, @NotNull final ContentManager contentManager) {
        super(project, contentManager);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        final Box line1 = Box.createHorizontalBox();
        add(line1);
        add(Box.createVerticalGlue());
        final Box line2 = Box.createHorizontalBox();
        add(line2);
        add(Box.createVerticalGlue());

        line1.add(Box.createHorizontalGlue());

        line1.add(new JLabel("Press"));
        line1.add(createSingleActionToolbarComponent(getOCamlToolWindowOpenCloseAction(true, true)));
        line1.add(new JLabel(" to open OCaml top level interactive console"));

        line1.add(Box.createHorizontalGlue());

        line2.add(Box.createHorizontalGlue());

        line2.add(new JLabel("Press"));
        line2.add(createSingleActionToolbarComponent(getOCamlToolWindowSettingsAction()));
        line2.add(new JLabel(" to customize OCaml top level interactive console settings"));

        line2.add(Box.createHorizontalGlue());
    }

    private JComponent createSingleActionToolbarComponent(@NotNull final AnAction action) {
        final DefaultActionGroup group = new DefaultActionGroup(action);
        final JComponent toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN, group, false).getComponent();
        toolbar.setMaximumSize(toolbar.getPreferredSize());
        return toolbar;
    }
}
