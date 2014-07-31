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

package manuylov.maxim.ocaml.fileType.ml;

import manuylov.maxim.ocaml.fileType.OCamlFileType;
import manuylov.maxim.ocaml.fileType.mli.MLIFileType;
import manuylov.maxim.ocaml.util.OCamlIconUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 05.02.2009
 */
public class MLFileType extends OCamlFileType {
    @NotNull public static final MLFileType INSTANCE = new MLFileType();

    private MLFileType() {
        super(MLFileTypeLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return "OCAML_ML_FILE";
    }

    @NotNull
    public String getDescription() {
        return "OCaml module implementation files";
    }

    @NotNull
    public String getDefaultExtension() {
        return "ml";
    }

    @NotNull
    public Icon getIcon() {
        return OCamlIconUtil.getMLFileIcon();
    }

    @NotNull
    @Override
    public OCamlFileType getAnotherFileType() {
        return MLIFileType.INSTANCE;
    }
}
