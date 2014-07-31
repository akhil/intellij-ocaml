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

import manuylov.maxim.ocaml.lang.parser.ast.testCase.MLParsingTestCase;
import org.testng.annotations.Test;

import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 25.02.2009
 */
@Test
public class LetParsingTest extends MLParsingTestCase {
    public void testLetStatement() throws Exception {
        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "y");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "8");

        doTest("let x = 5 and y = 8", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(5, EQ);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "y");
        myTree.addNode(5, EQ);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");

        doTest("let x = a and y = b", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, REC_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "y");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "8");

        doTest("let rec x = 5 and y = 8", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");

        doTest("let x a b = 5", myTree.getStringRepresentation());
    }

    public void testLetExpression() throws Exception {
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
        myTree.addNode(5, AND_KEYWORD);
        myTree.addNode(5, LET_BINDING);
        myTree.addNode(6, LET_BINDING_PATTERN);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "y");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, IN_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "s");

        doTest("let s = 2 and y = 0 in s", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, LET_EXPRESSION);
        myTree.addNode(5, LET_KEYWORD);
        myTree.addNode(5, REC_KEYWORD);
        myTree.addNode(5, LET_BINDING);
        myTree.addNode(6, LET_BINDING_PATTERN);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "s");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "2");
        myTree.addNode(5, AND_KEYWORD);
        myTree.addNode(5, LET_BINDING);
        myTree.addNode(6, LET_BINDING_PATTERN);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "y");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, IN_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "s");

        doTest("let rec s = 2 and y = 0 in s", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, LET_EXPRESSION);
        myTree.addNode(5, LET_KEYWORD);
        myTree.addNode(5, LET_BINDING);
        myTree.addNode(6, LET_BINDING_PATTERN);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "5");
        myTree.addNode(5, IN_KEYWORD);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "1");

        doTest("let x a b = 5 in 1", myTree.getStringRepresentation());
    }

    public void testLetClassExpression() throws Exception {
        myTree.addNode(3, CLASS_DEFINITION);
        myTree.addNode(4, CLASS_KEYWORD);
        myTree.addNode(4, CLASS_BINDING);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "myClass");
        myTree.addNode(5, EQ);
        myTree.addNode(5, LET_CLASS_EXPRESSION);
        myTree.addNode(6, LET_KEYWORD);
        myTree.addNode(6, LET_BINDING);
        myTree.addNode(7, LET_BINDING_PATTERN);
        myTree.addNode(8, VALUE_NAME_PATTERN);
        myTree.addNode(9, LCFC_IDENTIFIER, "s");
        myTree.addNode(7, EQ);
        myTree.addNode(7, CONSTANT_EXPRESSION);
        myTree.addNode(8, INTEGER_LITERAL, "2");
        myTree.addNode(6, AND_KEYWORD);
        myTree.addNode(6, LET_BINDING);
        myTree.addNode(7, LET_BINDING_PATTERN);
        myTree.addNode(8, VALUE_NAME_PATTERN);
        myTree.addNode(9, LCFC_IDENTIFIER, "y");
        myTree.addNode(7, EQ);
        myTree.addNode(7, CONSTANT_EXPRESSION);
        myTree.addNode(8, INTEGER_LITERAL, "0");
        myTree.addNode(6, IN_KEYWORD);
        myTree.addNode(6, CLASS_PATH);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module");
        myTree.addNode(7, DOT);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "SubModule");
        myTree.addNode(7, DOT);
        myTree.addNode(7, CLASS_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "className");

        doTest("class myClass = let s = 2 and y = 0 in Module.SubModule.className", myTree.getStringRepresentation());
    }
}
