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

package manuylov.maxim.ocaml.actions;

import com.intellij.openapi.fileTypes.FileType;
import manuylov.maxim.ocaml.fileType.ml.MLFileType;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 13.04.2010
 */
public class CreateMLFileAction extends BaseCreateOCamlFileAction {
    protected CreateMLFileAction() {
        super("OCaml Module Implementation", "Create New OCaml Module Implementation File", OCamlIconUtil.getMLFileIcon());
    }

    @NotNull
    protected String getCapitalizedType() {
        return "Implementation";
    }

    @NotNull
    protected FileType getFileType() {
        return MLFileType.INSTANCE;
    }
}
