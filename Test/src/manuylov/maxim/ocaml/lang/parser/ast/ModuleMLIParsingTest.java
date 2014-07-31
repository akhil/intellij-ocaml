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
public class ModuleMLIParsingTest extends BaseModuleParsingTest {
    public void testModuleType() throws Exception {
        myTree.addNode(3, MODULE_TYPE_SPECIFICATION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, MODULE_TYPE_SPECIFICATION_BINDING);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");

        doTest("module type m", myTree.getStringRepresentation());
    }

    public void testModuleSpecification() throws Exception {
        myTree.addNode(3, MODULE_SPECIFICATION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_SPECIFICATION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, COLON);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleTypeName");

        doTest("module MyModule : ModuleTypeName", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, MODULE_SPECIFICATION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_SPECIFICATION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_PARAMETER);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(7, COLON);
        myTree.addNode(7, MODULE_TYPE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "ModuleTypeName1");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_PARAMETER);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(7, COLON);
        myTree.addNode(7, MODULE_TYPE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "ModuleTypeName2");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, COLON);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleTypeName");

        doTest("module MyModule (Module1 : ModuleTypeName1) (Module2 : ModuleTypeName2) : ModuleTypeName", myTree.getStringRepresentation());
    }

    @NotNull
    protected IElementType getMainElement() {
        return MODULE_TYPE_SPECIFICATION;
    }

    @NotNull
    @Override
    protected IElementType getTypeBindingElement() {
        return MODULE_TYPE_SPECIFICATION_BINDING;
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