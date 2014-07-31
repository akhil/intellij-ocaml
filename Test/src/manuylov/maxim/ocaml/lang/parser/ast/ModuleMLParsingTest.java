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
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
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
public class ModuleMLParsingTest extends BaseModuleParsingTest {
    public void testModuleDefinition() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule = Module", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, COLON);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule : ModuleTypeName = Module", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
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
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule (Module1 : ModuleTypeName1) (Module2 : ModuleTypeName2) = Module", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
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
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule (Module1 : ModuleTypeName1) (Module2 : ModuleTypeName2) : ModuleTypeName = Module", myTree.getStringRepresentation());
    }

    public void testModulePathExpression() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule = Module1.Module", myTree.getStringRepresentation());
    }

    public void testStructEndExpression() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, STRUCT_END_MODULE_EXPRESSION);
        myTree.addNode(6, STRUCT_KEYWORD);
        myTree.addNode(6, EXCEPTION_DEFINITION);
        myTree.addNode(7, EXCEPTION_KEYWORD);
        myTree.addNode(7, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(8, UCFC_IDENTIFIER, "Error");
        myTree.addNode(6, END_KEYWORD);

        doTest("module MyModule = struct exception Error end", myTree.getStringRepresentation());
    }

    public void testFunctorExpression() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, FUNCTOR_MODULE_EXPRESSION);
        myTree.addNode(6, FUNCTOR_KEYWORD);
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, MODULE_PARAMETER);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(8, COLON);
        myTree.addNode(8, MODULE_TYPE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, STRUCT_END_MODULE_EXPRESSION);
        myTree.addNode(7, STRUCT_KEYWORD);
        myTree.addNode(7, END_KEYWORD);

        doTest("module MyModule = functor (ModuleName : ModuleTypeName) -> struct end", myTree.getStringRepresentation());
    }

    public void testFunctorApplicationExpression() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, FUNCTOR_APPLICATION_MODULE_EXPRESSION);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "ModuleName2");
        myTree.addNode(7, RPAR);

        doTest("module MyModule = ModuleName(ModuleName2)", myTree.getStringRepresentation());
    }

    public void testParenthesesedExpression() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, PARENTHESES_MODULE_EXPRESSION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(6, RPAR);

        doTest("module MyModule = (ModuleName)", myTree.getStringRepresentation());
    }

    public void testModuleTypeConstraintExpression() throws Exception {
        myTree.addNode(3, MODULE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, MODULE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(6, COLON);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(6, RPAR);

        doTest("module MyModule = (ModuleName : ModuleTypeName)", myTree.getStringRepresentation());
    }

    @NotNull
    protected IElementType getMainElement() {
        return MODULE_TYPE_DEFINITION;
    }

    @NotNull
    @Override
    protected IElementType getTypeBindingElement() {
        return MODULE_TYPE_DEFINITION_BINDING;
    }

    @NotNull
    protected ParserDefinition getParserDefinition() {
        return MLFileTypeLanguage.INSTANCE.getParserDefinition();
    }

    @NotNull
    protected IElementType getModuleExpressionNodeType() {
        return OCamlElementTypes.FILE_MODULE_EXPRESSION;
    }

    @NotNull
    protected IElementType getModuleBindingNodeType() {
        return OCamlElementTypes.FILE_MODULE_DEFINITION_BINDING;
    }
}
