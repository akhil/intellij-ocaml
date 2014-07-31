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

package manuylov.maxim.ocaml.fileType.mli.parser;

import com.intellij.lang.PsiParser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import manuylov.maxim.ocaml.fileType.mli.parser.psi.element.impl.MLIFile;
import manuylov.maxim.ocaml.lang.parser.BaseParserDefinition;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 09.02.2009
 */
public class MLIParserDefinition extends BaseParserDefinition {
    @NotNull
    public PsiParser createParser(@NotNull final Project project) {
        return new MLIParser();
    }

    @NotNull
    public IFileElementType getFileNodeType() {
        return OCamlElementTypes.MLI_FILE;
    }

    @NotNull
    public PsiFile createFile(@NotNull final FileViewProvider fileViewProvider) {
        return new MLIFile(fileViewProvider);
    }
}