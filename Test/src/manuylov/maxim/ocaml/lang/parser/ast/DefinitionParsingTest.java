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
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.ast.util.TreeStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static com.intellij.psi.TokenType.ERROR_ELEMENT;
import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 28.02.2009
 */
@Test
public class DefinitionParsingTest extends BaseStatementParsingTest {
    public void testDoubleSemicolon() throws Exception {
        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "7");
        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");
        myTree.addNode(3, SEMICOLON_SEMICOLON);
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, INTEGER_LITERAL, "4");

        doTest("let a = 7 let c = 0;; 4", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");
        myTree.addNode(3, ERROR_ELEMENT, Strings.SEMICOLON_SEMICOLON_EXPECTED);
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, LET_EXPRESSION);
        myTree.addNode(5, LET_KEYWORD);
        myTree.addNode(5, LET_BINDING);
        myTree.addNode(6, LET_BINDING_PATTERN);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "s");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "2");
        myTree.addNode(5, IN_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "s");

        doTest("let c = 0 let s = 2 in s", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, WHILE_EXPRESSION);
        myTree.addNode(5, WHILE_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, DO_KEYWORD);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, RPAR);
        myTree.addNode(5, DONE_KEYWORD);
        myTree.addNode(3, ERROR_ELEMENT, Strings.SEMICOLON_SEMICOLON_EXPECTED);
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, INTEGER_LITERAL, "4");

        doTest("while a do () done 4", myTree.getStringRepresentation());
    }

    public void testIncludeDefinition() throws Exception {
        myTree.addNode(3, INCLUDE_DIRECTIVE_DEFINITION);
        myTree.addNode(4, INCLUDE_KEYWORD);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("include Module", myTree.getStringRepresentation());
    }

    public void testExceptionDefinition() throws Exception {
        myTree.addNode(3, EXCEPTION_DEFINITION);
        myTree.addNode(4, EXCEPTION_KEYWORD);
        myTree.addNode(4, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(5, UCFC_IDENTIFIER, "Error");

        doTest("exception Error", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXCEPTION_DEFINITION);
        myTree.addNode(4, EXCEPTION_KEYWORD);
        myTree.addNode(4, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(5, UCFC_IDENTIFIER, "Error1");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTRUCTOR_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Error");

        doTest("exception Error1 = Error", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXCEPTION_DEFINITION);
        myTree.addNode(4, EXCEPTION_KEYWORD);
        myTree.addNode(4, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(5, UCFC_IDENTIFIER, "Error1");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTRUCTOR_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, CONSTRUCTOR_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Error");

        doTest("exception Error1 = Module1.Error", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXCEPTION_DEFINITION);
        myTree.addNode(4, EXCEPTION_KEYWORD);
        myTree.addNode(4, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(5, UCFC_IDENTIFIER, "Error");
        myTree.addNode(4, OF_KEYWORD);
        myTree.addNode(4, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "int");

        doTest("exception Error of int", myTree.getStringRepresentation());
    }

    public void testEmptyFile() throws Exception {
        myTree = new TreeStringBuilder(false);
        myTree.addNode(0, ML_FILE);
        myTree.addNode(1, FILE_MODULE_DEFINITION_BINDING);
        myTree.addNode(2, FILE_MODULE_EXPRESSION, "");

        doTest("", myTree.getStringRepresentation());
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
