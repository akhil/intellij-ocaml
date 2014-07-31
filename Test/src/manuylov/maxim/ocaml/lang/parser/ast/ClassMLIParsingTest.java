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

import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public class ClassMLIParsingTest extends BaseClassParsingTest {
    public void testSeveralSpecificationBindings() throws Exception {
        myTree.addNode(3, CLASS_SPECIFICATION);
        myTree.addNode(4, CLASS_KEYWORD);
        myTree.addNode(4, CLASS_SPECIFICATION_BINDING);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, COLON);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, CLASS_SPECIFICATION_BINDING);
        myTree.addNode(5, VIRTUAL_KEYWORD);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, COLON);
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, END_KEYWORD);
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, CLASS_SPECIFICATION_BINDING);
        myTree.addNode(5, BRACKETS);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, RBRACKET);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, COLON);
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, END_KEYWORD);
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, CLASS_SPECIFICATION_BINDING);
        myTree.addNode(5, VIRTUAL_KEYWORD);
        myTree.addNode(5, BRACKETS);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, RBRACKET);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, COLON);
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, END_KEYWORD);

        doTest("class class1 : class0 and virtual class1 : object end and ['a, 'b, 'c] class1 : object end and virtual ['a, 'b, 'c] class1 : object end", myTree.getStringRepresentation());
    }
    
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
