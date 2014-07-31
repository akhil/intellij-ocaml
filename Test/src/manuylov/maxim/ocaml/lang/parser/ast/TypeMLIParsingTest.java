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

package manuylov.maxim.ocaml.lang.parser.ast;

import com.intellij.lang.ParserDefinition;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.fileType.mli.MLIFileTypeLanguage;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 18.03.2009
 */
@Test
public class TypeMLIParsingTest extends BaseTypeParsingTest {
    @NotNull
    protected ParserDefinition getParserDefinition() {
        return MLIFileTypeLanguage.INSTANCE.getParserDefinition();
    }

    @NotNull
    protected IElementType getModuleExpressionNodeType() {
        return OCamlElementTypes.FILE_MODULE_TYPE;
    }

    @NotNull
    protected IElementType getModuleBindingNodeType() {
        return OCamlElementTypes.FILE_MODULE_SPECIFICATION_BINDING;
    }
}