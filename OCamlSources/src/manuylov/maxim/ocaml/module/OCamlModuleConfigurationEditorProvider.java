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

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleConfigurationEditor;
import com.intellij.openapi.roots.ui.configuration.DefaultModuleConfigurationEditorFactory;
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationEditorProvider;
import com.intellij.openapi.roots.ui.configuration.ModuleConfigurationState;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 03.04.2010
 */
public class OCamlModuleConfigurationEditorProvider implements ModuleConfigurationEditorProvider {
    public ModuleConfigurationEditor[] createEditors(@NotNull final ModuleConfigurationState state) {
        final Module module = state.getRootModel().getModule();
        if (!OCamlModuleUtil.isOCamlModule(module)) return ModuleConfigurationEditor.EMPTY;
        final DefaultModuleConfigurationEditorFactory editorFactory = DefaultModuleConfigurationEditorFactory.getInstance();
        final List<ModuleConfigurationEditor> editors = new ArrayList<ModuleConfigurationEditor>();
        editors.add(editorFactory.createModuleContentRootsEditor(state));
        editors.add(editorFactory.createOutputEditor(state));
        editors.add(editorFactory.createClasspathEditor(state));
        return editors.toArray(new ModuleConfigurationEditor[editors.size()]);
    }
}