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
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 28.02.2009
 */
@Test
public class ExpressionParsingTest extends MLParsingTestCase {
    public void testConstant() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTRUCTOR_NAME_EXPRESSION);
        myTree.addNode(5, UCFC_IDENTIFIER, "Constr");

        doTest("Constr", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, RPAR);

        doTest("()", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Tag");

        doTest("`Tag", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "tag");

        doTest("`tag", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_tag");

        doTest("`_tag", myTree.getStringRepresentation());
    }

    public void testExpressionInParentheses() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, PARENTHESES_EXPRESSION);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(5, RPAR);

        doTest("(5)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, PARENTHESES_EXPRESSION);
        myTree.addNode(5, BEGIN_KEYWORD);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(5, END_KEYWORD);

        doTest("begin 5 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, TYPE_CONSTRAINT_EXPRESSION);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(5, COLON);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, RPAR);

        doTest("(5 : int)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CASTING_EXPRESSION);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(5, COLON_GT);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, RPAR);

        doTest("(5 :> int)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CASTING_EXPRESSION);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");
        myTree.addNode(5, COLON);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, COLON_GT);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, RPAR);

        doTest("(5 : int :> int)", myTree.getStringRepresentation());
    }

    public void testCommaExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, COMMA_EXPRESSION);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "3");

        doTest("1, 2, 3", myTree.getStringRepresentation());
    }

    public void testConstructorApplication() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTRUCTOR_APPLICATION_EXPRESSION);
        myTree.addNode(5, CONSTRUCTOR_NAME_EXPRESSION);
        myTree.addNode(6, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "5");

        doTest("Constr 5", myTree.getStringRepresentation());
    }

    public void testTaggedExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, TAGGED_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Tag");
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "2");

        doTest("`Tag 2", myTree.getStringRepresentation());
    }

    public void testHeadTailExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, HEAD_TAIL_EXPRESSION);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, COLON_COLON);
        myTree.addNode(5, LIST_EXPRESSION);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, RBRACKET);

        doTest("1 :: []", myTree.getStringRepresentation());
    }

    public void testListExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, LIST_EXPRESSION);
        myTree.addNode(5, LBRACKET);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        myTree.addNode(5, RBRACKET);

        doTest("[1; 2]", myTree.getStringRepresentation());
    }

    public void testArrayExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ARRAY_EXPRESSION);
        myTree.addNode(5, LBRACKET_VBAR);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        myTree.addNode(5, VBAR_RBRACKET);

        doTest("[|1; 2|]", myTree.getStringRepresentation());
    }

    public void testRecordExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, RECORD_EXPRESSION);
        myTree.addNode(5, LBRACE);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "1");
        myTree.addNode(5, RBRACE);

        doTest("{a = 0; b = 1}", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, INHERITED_RECORD_EXPRESSION);
        myTree.addNode(5, LBRACE);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "recordValue");
        myTree.addNode(5, WITH_KEYWORD);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "1");
        myTree.addNode(5, RBRACE);

        doTest("{recordValue with a = 0; b = 1}", myTree.getStringRepresentation());
    }

    public void testFunctionApplication() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, FUNCTION_APPLICATION_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "f");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "2");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, CONSTRUCTOR_NAME_EXPRESSION);
        myTree.addNode(7, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, TRUE_KEYWORD);
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, FALSE_KEYWORD);
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, LIST_EXPRESSION);
        myTree.addNode(7, LBRACKET);
        myTree.addNode(7, RBRACKET);
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, RPAR);
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "lbl1");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "lbl2");
        myTree.addNode(6, COLON);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "3");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "lbl3");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "lbl4");
        myTree.addNode(6, COLON);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "4");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "g");

        doTest("f a b 2 c Constr true false [] () `tag ~lbl1 ~lbl2: 3 ?lbl3 ?lbl4: 4 g", myTree.getStringRepresentation());
    }

    public void testPrefixOperator() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, UNARY_EXPRESSION);
        myTree.addNode(5, PREFIX_OPERATOR, "~=");
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
               
        doTest("~=a", myTree.getStringRepresentation());
    }

    public void testInfixOperator() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, BINARY_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, INFIX_OPERATOR, "$+");
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");

        doTest("a$+b", myTree.getStringRepresentation());
    }

    public void testFieldAccessing() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, RECORD_FIELD_ACCESSING_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, DOT);
        myTree.addNode(5, FIELD_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");

        doTest("a.b", myTree.getStringRepresentation());
    }

    public void testArrayElementAccessing() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ARRAY_ELEMENT_ACCESSING_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, DOT);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, RPAR);

        doTest("a.(b)", myTree.getStringRepresentation());
    }

    public void testStringCharAccessing() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, STRING_CHAR_ACCESSING_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, DOT);
        myTree.addNode(5, BRACKETS);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, RBRACKET);

        doTest("a.[b]", myTree.getStringRepresentation());
    }

    public void testAssignment() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ASSIGNMENT_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, LT_MINUS);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "new_val");

        doTest("a <- new_val", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ASSIGNMENT_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, COLON_EQ);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "new_val");

        doTest("a := new_val", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ASSIGNMENT_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, LT_MINUS);
        myTree.addNode(5, ASSIGNMENT_EXPRESSION);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, LT_MINUS);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "new_val");

        doTest("a <- b <- new_val", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ASSIGNMENT_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, COLON_EQ);
        myTree.addNode(5, ASSIGNMENT_EXPRESSION);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, COLON_EQ);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "new_val");

        doTest("a := b := new_val", myTree.getStringRepresentation());
    }

    public void testIfExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, IF_EXPRESSION);
        myTree.addNode(5, IF_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, THEN_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");

        doTest("if a then b", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, IF_EXPRESSION);
        myTree.addNode(5, IF_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, THEN_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, ELSE_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "c");

        doTest("if a then b else c", myTree.getStringRepresentation());
    }

    public void testWhileExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, WHILE_EXPRESSION);
        myTree.addNode(5, WHILE_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, DO_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, DONE_KEYWORD);

        doTest("while a do b done", myTree.getStringRepresentation());
    }

    public void testForExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, FOR_EXPRESSION);
        myTree.addNode(5, FOR_KEYWORD);
        myTree.addNode(5, FOR_EXPRESSION_BINDING);
        myTree.addNode(6, FOR_EXPRESSION_INDEX_VARIABLE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, TO_KEYWORD);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, DO_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "d");
        myTree.addNode(5, DONE_KEYWORD);

        doTest("for a = b to c do d done", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, FOR_EXPRESSION);
        myTree.addNode(5, FOR_KEYWORD);
        myTree.addNode(5, FOR_EXPRESSION_BINDING);
        myTree.addNode(6, FOR_EXPRESSION_INDEX_VARIABLE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, DOWNTO_KEYWORD);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, DO_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "d");
        myTree.addNode(5, DONE_KEYWORD);

        doTest("for a = b downto c do d done", myTree.getStringRepresentation());
    }

    public void testSemicolonExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, SEMICOLON_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "c");
        
        doTest("a; b; c", myTree.getStringRepresentation());
    }

    public void testMatchExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, MATCH_EXPRESSION);
        myTree.addNode(5, MATCH_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "d");
        myTree.addNode(5, WITH_KEYWORD);
        myTree.addNode(5, VBAR);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, WHEN_KEYWORD);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, TRUE_KEYWORD);
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "1");
        myTree.addNode(5, VBAR);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, WHEN_KEYWORD);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, FALSE_KEYWORD);
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "2");

        doTest("match d with | a when true -> 1 | b when false -> 2", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, MATCH_EXPRESSION);
        myTree.addNode(5, MATCH_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "d");
        myTree.addNode(5, WITH_KEYWORD);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "1");
        myTree.addNode(5, VBAR);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "2");

        doTest("match d with a -> 1 | b -> 2", myTree.getStringRepresentation());
    }

    public void testFunctionExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, FUNCTION_EXPRESSION);
        myTree.addNode(5, FUNCTION_KEYWORD);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "2");

        doTest("function x -> 2", myTree.getStringRepresentation());
    }

    public void testFunExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, FUN_EXPRESSION);
        myTree.addNode(5, FUN_KEYWORD);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "y");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "z");
        myTree.addNode(5, MINUS_GT);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "2");

        doTest("fun x y z -> 2", myTree.getStringRepresentation());
    }

    public void testTryExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, TRY_EXPRESSION);
        myTree.addNode(5, TRY_KEYWORD);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, RPAR);
        myTree.addNode(5, WITH_KEYWORD);
        myTree.addNode(5, VBAR);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, CONSTRUCTOR_NAME_PATTERN);
        myTree.addNode(7, UCFC_IDENTIFIER, "A");
        myTree.addNode(6, WHEN_KEYWORD);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, TRUE_KEYWORD);
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, RPAR);
        myTree.addNode(5, VBAR);
        myTree.addNode(5, PATTERN_MATCHING);
        myTree.addNode(6, CONSTRUCTOR_NAME_PATTERN);
        myTree.addNode(7, UCFC_IDENTIFIER, "B");
        myTree.addNode(6, WHEN_KEYWORD);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, FALSE_KEYWORD);
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, RPAR);

        doTest("try () with | A when true -> () | B when false -> ()", myTree.getStringRepresentation());
    }

    public void testNewInstanceExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, NEW_INSTANCE_EXPRESSION);
        myTree.addNode(5, NEW_KEYWORD);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "myClass");

        doTest("new myClass", myTree.getStringRepresentation());
    }

    public void testObjectClassBodyEndExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, OBJECT_CLASS_BODY_END_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, END_KEYWORD);

        doTest("object end", myTree.getStringRepresentation());

        recreateTree();
        
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, OBJECT_CLASS_BODY_END_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME_DEFINITION);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("object val a = 0 end", myTree.getStringRepresentation());
    }

    public void testClassMethodAccessingExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CLASS_METHOD_ACCESSING_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, HASH);
        myTree.addNode(5, METHOD_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");

        doTest("a#b", myTree.getStringRepresentation());
    }

    public void testInstanceDuplicatingExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, INSTANCE_DUPLICATING_EXPRESSION);
        myTree.addNode(5, LBRACE_LT);
        myTree.addNode(5, INST_VAR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, INST_VAR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, CHAR_LITERAL, "'q'");
        myTree.addNode(5, GT_RBRACE);

        doTest("{< a = 0; b = 'q' >}", myTree.getStringRepresentation());
    }

    public void testAssertExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, ASSERT_EXPRESSION);
        myTree.addNode(5, ASSERT_KEYWORD);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, TRUE_KEYWORD);

        doTest("assert true", myTree.getStringRepresentation());
    }

    public void testLazyExpression() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, LAZY_EXPRESSION);
        myTree.addNode(5, LAZY_KEYWORD);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "1");

        doTest("lazy 1", myTree.getStringRepresentation());
    }

    public void testParameter() throws Exception {
        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "d");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "e");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "f");
        myTree.addNode(6, COLON);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "g");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "h");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "i");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "j");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "k");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "l");
        myTree.addNode(6, COLON);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "n");
        myTree.addNode(6, COLON);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "o");
        myTree.addNode(6, COLON);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "p");
        myTree.addNode(6, COLON);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");

        doTest("let a b ~c ~(d) ~(e: int) ~f: q ?g ?(h) ?(i: int) ?(j = 0) ?(k: int = 0) ?l: q ?m: (q) ?n: (q: int) ?o: (q = 0) ?p: (q: int = 0) = 0", myTree.getStringRepresentation());
    }

    public void testUnaryMinusAndFunctionApplication() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, BINARY_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, MINUS);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "2");

        doTest("a -2", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, BINARY_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, MINUS);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");

        doTest("a -b", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, FUNCTION_APPLICATION_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, PARENTHESES_EXPRESSION);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, UNARY_EXPRESSION);
        myTree.addNode(8, MINUS);
        myTree.addNode(8, VALUE_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "b");
        myTree.addNode(7, RPAR);

        doTest("a (-b)", myTree.getStringRepresentation());
    }

    public void testPriorities() throws Exception {
        String expr1 = getTreeIgnoringParentheses("let a = let x = 0 in (); let y = 0 in y in (); let p = 0 in p");
        String expr2 = getTreeIgnoringParentheses("let a = (let x = 0 in ((); (let y = 0 in y))) in ((); (let p = 0 in p))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("match (); match 1 with _ -> 1 with _ -> (); match 1 with _ -> (); match 1 with _ -> 1");
        expr2 = getTreeIgnoringParentheses("match ((); (match 1 with _ -> 1)) with _ -> ((); (match 1 with _ -> ((); (match 1 with _ -> 1))))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("try (); try () with _ -> () with _ -> (); try () with _ -> ()");
        expr2 = getTreeIgnoringParentheses("try ((); (try () with _ -> ())) with _ -> ((); (try () with _ -> ()))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("fun a -> (); fun b -> 1");
        expr2 = getTreeIgnoringParentheses("fun a -> ((); (fun b -> 1))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("function a -> (); function b -> 1");
        expr2 = getTreeIgnoringParentheses("function a -> ((); (function b -> 1))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("if true then (); if true then () else (); if true then ()");
        expr2 = getTreeIgnoringParentheses("(if true then ()); (if true then () else ()); (if true then ())");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("for i = 0 to 1 do (); for i = 0 to 1 do () done done; for i = 0 to 1 do () done");
        expr2 = getTreeIgnoringParentheses("(for i = 0 to 1 do ((); (for i = 0 to 1 do () done)) done); (for i = 0 to 1 do () done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("while true do (); while true do () done done; while true do () done");
        expr2 = getTreeIgnoringParentheses("(while true do ((); (while true do () done)) done); (while true do () done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("if true then a <- 1;; if true then () else a <- 1;; if true then a := 1;; if true then () else a := 1");
        expr2 = getTreeIgnoringParentheses("(if true then (a <- 1));; (if true then () else (a <- 1));; (if true then (a := 1));; (if true then () else (a := 1))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("for i = 0 to 1 do a <- 1 done;; for i = 0 to 1 do a := 1 done");
        expr2 = getTreeIgnoringParentheses("(for i = 0 to 1 do (a <- 1) done);; (for i = 0 to 1 do (a := 1) done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("while true do a <- 1 done;; while true do a := 1 done");
        expr2 = getTreeIgnoringParentheses("(while true do (a <- 1) done);; (while true do (a := 1) done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a <- b, c;; a := b, c");
        expr2 = getTreeIgnoringParentheses("(a <- (b, c));; (a := (b, c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a, b or c;; a, b || c");
        expr2 = getTreeIgnoringParentheses("(a, (b or c));; (a, (b || c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a or b & c;; a or b && c;; a || b & c;; a || b && c");
        expr2 = getTreeIgnoringParentheses("(a or (b & c));; (a or (b && c));; (a || (b & c));; (a || (b && c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a & b = c;; a & b =@ c;; a & b < c;; a & b <@ c;; a & b > c;; a & b >@ c;; a & b |@ c;; a & b &@ c;; a & b $ c;; a & b $@ c");
        expr2 = getTreeIgnoringParentheses("(a & (b = c));; (a & (b =@ c));; (a & (b < c));; (a & (b <@ c));; (a & (b > c));; (a & (b >@ c));; (a & (b |@ c));; (a & (b &@ c));; (a & (b $ c));; (a & (b $@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a && b = c;; a && b =@ c;; a && b < c;; a && b <@ c;; a && b > c;; a && b >@ c;; a && b |@ c;; a && b &@ c;; a && b $ c;; a && b $@ c");
        expr2 = getTreeIgnoringParentheses("(a && (b = c));; (a && (b =@ c));; (a && (b < c));; (a && (b <@ c));; (a && (b > c));; (a && (b >@ c));; (a && (b |@ c));; (a && (b &@ c));; (a && (b $ c));; (a && (b $@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a = b @ c;; a =@ b @ c;; a < b @ c;; a <@ b @ c;; a > b @ c;; a >@ b @ c;; a |@ b @ c;; a &@ b @ c;; a $ b @ c;; a $@ b @ c");
        expr2 = getTreeIgnoringParentheses("(a = (b @ c));; (a =@ (b @ c));; (a < (b @ c));; (a <@ (b @ c));; (a > (b @ c));; (a >@ (b @ c));; (a |@ (b @ c));; (a &@ (b @ c));; (a $ (b @ c));; (a $@ (b @ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a = b @@ c;; a =@ b @@ c;; a < b @@ c;; a <@ b @@ c;; a > b @@ c;; a >@ b @@ c;; a |@ b @@ c;; a &@ b @@ c;; a $ b @@ c;; a $@ b @@ c");
        expr2 = getTreeIgnoringParentheses("(a = (b @@ c));; (a =@ (b @@ c));; (a < (b @@ c));; (a <@ (b @@ c));; (a > (b @@ c));; (a >@ (b @@ c));; (a |@ (b @@ c));; (a &@ (b @@ c));; (a $ (b @@ c));; (a $@ (b @@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a = b ^ c;; a =@ b ^ c;; a < b ^ c;; a <@ b ^ c;; a > b ^ c;; a >@ b ^ c;; a |@ b ^ c;; a &@ b ^ c;; a $ b ^ c;; a $@ b ^ c");
        expr2 = getTreeIgnoringParentheses("(a = (b ^ c));; (a =@ (b ^ c));; (a < (b ^ c));; (a <@ (b ^ c));; (a > (b ^ c));; (a >@ (b ^ c));; (a |@ (b ^ c));; (a &@ (b ^ c));; (a $ (b ^ c));; (a $@ (b ^ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a = b ^@ c;; a =@ b ^@ c;; a < b ^@ c;; a <@ b ^@ c;; a > b ^@ c;; a >@ b ^@ c;; a |@ b ^@ c;; a &@ b ^@ c;; a $ b ^@ c;; a $@ b ^@ c");
        expr2 = getTreeIgnoringParentheses("(a = (b ^@ c));; (a =@ (b ^@ c));; (a < (b ^@ c));; (a <@ (b ^@ c));; (a > (b ^@ c));; (a >@ (b ^@ c));; (a |@ (b ^@ c));; (a &@ (b ^@ c));; (a $ (b ^@ c));; (a $@ (b ^@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a @ b :: c;; a @@ b :: c;; a ^ b :: c;; a ^@ b :: c");
        expr2 = getTreeIgnoringParentheses("(a @ (b :: c));; (a @@ (b :: c));; (a ^ (b :: c));; (a ^@ (b :: c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a :: b + c;; a :: b +@ c;; a :: b - c;; a :: b -@ c");
        expr2 = getTreeIgnoringParentheses("(a :: (b + c));; (a :: (b +@ c));; (a :: (b - c));; (a :: (b -@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a + b * c;; a + b *@ c;; a + b / c;; a + b /@ c;; a + b % c;; a + b %@ c;; a + b mod c;; a + b land c;; a + b lor c;; a + b lxor c");
        expr2 = getTreeIgnoringParentheses("(a + (b * c));; (a + (b *@ c));; (a + (b / c));; (a + (b /@ c));; (a + (b % c));; (a + (b %@ c));; (a + (b mod c));; (a + (b land c));; (a + (b lor c));; (a + (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a +@ b * c;; a +@ b *@ c;; a +@ b / c;; a +@ b /@ c;; a +@ b % c;; a +@ b %@ c;; a +@ b mod c;; a +@ b land c;; a +@ b lor c;; a +@ b lxor c");
        expr2 = getTreeIgnoringParentheses("(a +@ (b * c));; (a +@ (b *@ c));; (a +@ (b / c));; (a +@ (b /@ c));; (a +@ (b % c));; (a +@ (b %@ c));; (a +@ (b mod c));; (a +@ (b land c));; (a +@ (b lor c));; (a +@ (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a - b * c;; a - b *@ c;; a - b / c;; a - b /@ c;; a - b % c;; a - b %@ c;; a - b mod c;; a - b land c;; a - b lor c;; a - b lxor c");
        expr2 = getTreeIgnoringParentheses("(a - (b * c));; (a - (b *@ c));; (a - (b / c));; (a - (b /@ c));; (a - (b % c));; (a - (b %@ c));; (a - (b mod c));; (a - (b land c));; (a - (b lor c));; (a - (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a -@ b * c;; a -@ b *@ c;; a -@ b / c;; a -@ b /@ c;; a -@ b % c;; a -@ b %@ c;; a -@ b mod c;; a -@ b land c;; a -@ b lor c;; a -@ b lxor c");
        expr2 = getTreeIgnoringParentheses("(a -@ (b * c));; (a -@ (b *@ c));; (a -@ (b / c));; (a -@ (b /@ c));; (a -@ (b % c));; (a -@ (b %@ c));; (a -@ (b mod c));; (a -@ (b land c));; (a -@ (b lor c));; (a -@ (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a * b ** c;; a * b **@ c;; a * b lsl c;; a * b lsr c;; a * b asr c;; a *@ b ** c;; a *@ b **@ c;; a *@ b lsl c;; a *@ b lsr c;; a *@ b asr c");
        expr2 = getTreeIgnoringParentheses("(a * (b ** c));; (a * (b **@ c));; (a * (b lsl c));; (a * (b lsr c));; (a * (b asr c));; (a *@ (b ** c));; (a *@ (b **@ c));; (a *@ (b lsl c));; (a *@ (b lsr c));; (a *@ (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a / b ** c;; a / b **@ c;; a / b lsl c;; a / b lsr c;; a / b asr c;; a /@ b ** c;; a /@ b **@ c;; a /@ b lsl c;; a /@ b lsr c;; a /@ b asr c");
        expr2 = getTreeIgnoringParentheses("(a / (b ** c));; (a / (b **@ c));; (a / (b lsl c));; (a / (b lsr c));; (a / (b asr c));; (a /@ (b ** c));; (a /@ (b **@ c));; (a /@ (b lsl c));; (a /@ (b lsr c));; (a /@ (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a % b ** c;; a % b **@ c;; a % b lsl c;; a % b lsr c;; a % b asr c;; a %@ b ** c;; a %@ b **@ c;; a %@ b lsl c;; a %@ b lsr c;; a %@ b asr c");
        expr2 = getTreeIgnoringParentheses("(a % (b ** c));; (a % (b **@ c));; (a % (b lsl c));; (a % (b lsr c));; (a % (b asr c));; (a %@ (b ** c));; (a %@ (b **@ c));; (a %@ (b lsl c));; (a %@ (b lsr c));; (a %@ (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a mod b ** c;; a mod b **@ c;; a mod b lsl c;; a mod b lsr c;; a mod b asr c");
        expr2 = getTreeIgnoringParentheses("(a mod (b ** c));; (a mod (b **@ c));; (a mod (b lsl c));; (a mod (b lsr c));; (a mod (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a land b ** c;; a land b **@ c;; a land b lsl c;; a land b lsr c;; a land b asr c");
        expr2 = getTreeIgnoringParentheses("(a land (b ** c));; (a land (b **@ c));; (a land (b lsl c));; (a land (b lsr c));; (a land (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a lor b ** c;; a lor b **@ c;; a lor b lsl c;; a lor b lsr c;; a lor b asr c");
        expr2 = getTreeIgnoringParentheses("(a lor (b ** c));; (a lor (b **@ c));; (a lor (b lsl c));; (a lor (b lsr c));; (a lor (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a lxor b ** c;; a lxor b **@ c;; a lxor b lsl c;; a lxor b lsr c;; a lxor b asr c");
        expr2 = getTreeIgnoringParentheses("(a lxor (b ** c));; (a lxor (b **@ c));; (a lxor (b lsl c));; (a lxor (b lsr c));; (a lxor (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("-a ** b;; -.a ** b;; -a lsl b;; -.a lsl b;; -a lsr b;; -.a lsr b;; -a asr b;; -.a asr b");
        expr2 = getTreeIgnoringParentheses("((-a) ** b);; ((-.a) ** b);; ((-a) lsl b);; ((-.a) lsl b);; ((-a) lsr b);; ((-.a) lsr b);; ((-a) asr b);; ((-.a) asr b)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("-a b;; -Constr d;; -assert a;; -lazy a");
        expr2 = getTreeIgnoringParentheses("(-(a b));; (-(Constr d));; (-(assert a));; (-(lazy a))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("a b.c;; a b.(c);; a b.[c];; A b.c;; A b.(c);; A b.[c];; assert b.c;; assert b.(c);; assert b.[c];; lazy b.c;; lazy b.(c);; lazy b.[c]");
        expr2 = getTreeIgnoringParentheses("(a (b.c));; (a (b.(c)));; (a (b.[c]));; (A (b.c));; (A (b.(c)));; (A (b.[c]));; (assert (b.c));; (assert (b.(c)));; (assert (b.[c]));; (lazy (b.c));; (lazy (b.(c)));; (lazy (b.[c]))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParentheses("!a.b;; !@a.b;; ?@a.b;; ~@a.b;; !a.(b);; !@a.(b);; ?@a.(b);; ~@a.(b);; !a.[b];; !@a.[b];; ?@a.[b];; ~@a.[b]");
        expr2 = getTreeIgnoringParentheses("((!a).b);; ((!@a).b);; ((?@a).b);; ((~@a).b);; ((!a).(b));; ((!@a).(b));; ((?@a).(b));; ((~@a).(b));; ((!a).[b]);; ((!@a).[b]);; ((?@a).[b]);; ((~@a).[b])");

        assertEquals(expr1, expr2);
    }

    public void testPossibilities() throws Exception {
        assertIsAllowed("(let x = 0 in x)");
        assertIsAllowed("(();1)");
        assertIsAllowed("begin let x = 0 in x end");
        assertIsAllowed("begin ();1 end");
        assertIsAllowed("(let x = 0 in x : int)");
        assertIsAllowed("(();1 : int)");
        assertIsAllowed("a, let x = 0 in x");
        assertIsAllowed("a, ();1");
        assertIsNotAllowed("a let x = 0 in x");
        assertIsNotAllowed("A let x = 0 in x");
        assertIsNotAllowed("`tag let x = 0 in x");
        assertIsAllowed("1 :: let x = 0 in []");
        assertIsAllowed("1 :: ();[]");
        assertIsAllowed("[7; let x = 0 in x]");
        assertIsAllowed("[|7; let x = 0 in x|]");
        assertIsAllowed("{a = let x = 0 in x}");
        assertIsNotAllowed("{a = ();1}");
        assertIsNotAllowed("{let x = 0 in x with a = 0}");
        assertIsNotAllowed("{();b with a = 0}");
        assertIsNotAllowed("a let x = 0 in x");
        assertIsNotAllowed("!! let x = 0 in x");
        assertIsAllowed("let x = 0 in x + let x = 0 in x");
        assertIsAllowed("a <- let x = 0 in x");
        assertIsAllowed("a <- ();1");
        assertIsAllowed("a.(let x = 0 in x)");
        assertIsAllowed("a.(();1)");
        assertIsAllowed("a.[let x = 0 in x]");
        assertIsAllowed("a.[();1]");
        assertIsAllowed("if let x = 0 in true then ()");
        assertIsAllowed("if ();true then ()");
        assertIsAllowed("if true then let x = 0 in x");
        assertIsAllowed("if true then () else let x = 0 in x");
        assertIsAllowed("while let x = 0 in true do () done");
        assertIsAllowed("while ();true do () done");
        assertIsAllowed("while true do let x = 0 in () done");
        assertIsAllowed("while true do ();() done");
        assertIsAllowed("for i = let x = 0 in x to let x = 0 in x do () done");
        assertIsAllowed("for i = ();0 to ();0 do () done");
        assertIsAllowed("for i = 0 to 0 do let x = 0 in () done");
        assertIsAllowed("for i = 0 to 0 do ();() done");
        assertIsAllowed("();let x = 0 in x");
        assertIsAllowed("match let x = 0 in x with _ -> 0");
        assertIsAllowed("match ();0 with _ -> 0");
        assertIsAllowed("match 0 with _ -> let x = 0 in x");
        assertIsAllowed("match 0 with _ -> ();0");
        assertIsAllowed("function a -> let x = 0 in x");
        assertIsAllowed("function a -> ();0");
        assertIsAllowed("fun a -> let x = 0 in x");
        assertIsAllowed("fun a -> ();0");
        assertIsAllowed("try let x = 0 in 0 with _ -> 0");
        assertIsAllowed("try ();0 with _ -> 0");
        assertIsAllowed("try 0 with _ -> let x = 0 in x");
        assertIsAllowed("try 0 with _ -> ();0");
        assertIsAllowed("let a = let x = 0 in x in a");
        assertIsAllowed("let a = ();0 in a");
        assertIsAllowed("let a = 0 in let x = 0 in x");
        assertIsAllowed("let a = 0 in ();a");
        assertIsAllowed("(let x = 0 in x :> int)");
        assertIsAllowed("(();0 :> int)");
        assertIsAllowed("(let x = 0 in x : int :> int)");
        assertIsAllowed("(();0 : int :> int)");
        assertIsAllowed("{< a = let x = 0 in x >}");
        assertIsNotAllowed("{< a = ();1 >}");
        assertIsNotAllowed("assert let x = 0 in true");
        assertIsNotAllowed("lazy let x = 0 in true");
    }

    public void testAssociativity() throws Exception {
        doTestRightAssociativity("or");
        doTestRightAssociativity("||");
        doTestRightAssociativity("&");
        doTestRightAssociativity("&&");
        doTestLeftAssociativity("=");
        doTestLeftAssociativity("=@");
        doTestLeftAssociativity("<");
        doTestLeftAssociativity("<@");
        doTestLeftAssociativity(">");
        doTestLeftAssociativity(">@");
        doTestLeftAssociativity("|@");
        doTestLeftAssociativity("&@");
        doTestLeftAssociativity("$");
        doTestLeftAssociativity("$@");
        doTestRightAssociativity("@");
        doTestRightAssociativity("@@");
        doTestRightAssociativity("^");
        doTestRightAssociativity("^@");
        doTestRightAssociativity("::");
        doTestLeftAssociativity("+");
        doTestLeftAssociativity("+@");
        doTestLeftAssociativity("-");
        doTestLeftAssociativity("-@");
        doTestLeftAssociativity("*");
        doTestLeftAssociativity("*@");
        doTestLeftAssociativity("/");
        doTestLeftAssociativity("/@");
        doTestLeftAssociativity("%");
        doTestLeftAssociativity("%@");
        doTestLeftAssociativity("mod");
        doTestLeftAssociativity("land");
        doTestLeftAssociativity("lor");
        doTestLeftAssociativity("lxor");
        doTestRightAssociativity("**");
        doTestRightAssociativity("**@");
        doTestRightAssociativity("lsl");
        doTestRightAssociativity("lsr");
        doTestRightAssociativity("asr");
        doTestRightAssociativity("asr");
        doTestRightAssociativity("<-");
        doTestRightAssociativity(":=");             
    }

    private void doTestLeftAssociativity(@NotNull final String operator) throws Exception {
        final String expr1 = getTreeIgnoringParentheses("a " + operator + " b " + operator + " c");
        final String expr2 = getTreeIgnoringParentheses("((a " + operator + " b) " + operator + " c)");

        assertEquals(expr1, expr2);
    }

    private void doTestRightAssociativity(@NotNull final String operator) throws Exception {
        final String expr1 = getTreeIgnoringParentheses("a " + operator + " b " + operator + " c");
        final String expr2 = getTreeIgnoringParentheses("(a " + operator + " (b " + operator + " c))");

        assertEquals(expr1, expr2);
    }
}
