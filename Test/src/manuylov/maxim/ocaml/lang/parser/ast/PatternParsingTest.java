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
 *         Date: 19.03.2009
 */
@Test
public class PatternParsingTest extends MLParsingTestCase {
    public void testValueName() throws Exception {
        addStartNodes();
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        addEndNodes();

        doTest("match a with a -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, OPERATOR_NAME);
        myTree.addNode(8, PREFIX_OPERATOR, "!!");
        myTree.addNode(7, RPAR);
        addEndNodes();

        doTest("match a with (!!) -> 0", myTree.getStringRepresentation());
    }

    public void testUnderscore() throws Exception {
        addStartNodes();
        myTree.addNode(6, UNDERSCORE_PATTERN);
        myTree.addNode(7, UNDERSCORE); 
        addEndNodes();

        doTest("match a with _ -> 0", myTree.getStringRepresentation());
    }

    public void testCharRange() throws Exception {
        addStartNodes();
        myTree.addNode(6, CHAR_RANGE_PATTERN);
        myTree.addNode(7, CHAR_LITERAL, "'a'");
        myTree.addNode(7, DOT_DOT);
        myTree.addNode(7, CHAR_LITERAL, "'z'");
        addEndNodes();

        doTest("match a with 'a' .. 'z' -> 0", myTree.getStringRepresentation());
    }

    public void testEmptyListPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, LIST_PATTERN);
        myTree.addNode(7, LBRACKET);
        myTree.addNode(7, RBRACKET);
        addEndNodes();

        doTest("match a with [] -> 0", myTree.getStringRepresentation());
    }

    public void testEmptyArrayPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, ARRAY_PATTERN);
        myTree.addNode(7, LBRACKET_VBAR);
        myTree.addNode(7, VBAR_RBRACKET);
        addEndNodes();

        doTest("match a with [||] -> 0", myTree.getStringRepresentation());
    }

    public void testConstant() throws Exception {
        addStartNodes();
        myTree.addNode(6, CONSTANT_PATTERN);
        myTree.addNode(7, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with 1 -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(6, CONSTANT_PATTERN);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, RPAR);
        addEndNodes();

        doTest("match a with () -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(6, CONSTRUCTOR_NAME_PATTERN);
        myTree.addNode(7, UCFC_IDENTIFIER, "Constr");
        addEndNodes();

        doTest("match a with Constr -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(6, CONSTANT_PATTERN);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Tag");
        addEndNodes();

        doTest("match a with `Tag -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(6, CONSTANT_PATTERN);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        addEndNodes();

        doTest("match a with `tag -> 0", myTree.getStringRepresentation());
    }

    public void testAsPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, AS_PATTERN);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, AS_KEYWORD);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        addEndNodes();

        doTest("match a with 1 as a -> 0", myTree.getStringRepresentation());
    }

    public void testPatternInParentheses() throws Exception {
        addStartNodes();
        myTree.addNode(6, PARENTHESES_PATTERN);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, RPAR);
        addEndNodes();

        doTest("match a with (1) -> 0", myTree.getStringRepresentation());
    }

    public void testTypeConstraintPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, TYPE_CONSTRAINT_PATTERN);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        addEndNodes();

        doTest("match a with (1 : int) -> 0", myTree.getStringRepresentation());
    }

    public void testOrPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, OR_PATTERN);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, VBAR);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "2");
        addEndNodes();

        doTest("match a with 1 | 2 -> 0", myTree.getStringRepresentation());
    }

    public void testConstructorApplicationPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, CONSTRUCTOR_APPLICATION_PATTERN);
        myTree.addNode(7, CONSTRUCTOR_NAME_PATTERN);
        myTree.addNode(8, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with Constr 1 -> 0", myTree.getStringRepresentation());
    }

    public void testTaggedPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, TAGGED_PATTERN);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with `tag 1 -> 0", myTree.getStringRepresentation());
    }

    public void testTypeConstructorPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, TYPE_CONSTRUCTOR_PATTERN);
        myTree.addNode(7, HASH);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "ttt");
        addEndNodes();

        doTest("match a with #ttt -> 0", myTree.getStringRepresentation());
    }

    public void testCommaPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, COMMA_PATTERN);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "2");
        addEndNodes();

        doTest("match a with 1, 2 -> 0", myTree.getStringRepresentation());
    }

    public void testRecordPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, RECORD_PATTERN);
        myTree.addNode(7, LBRACE);
        myTree.addNode(7, RECORD_FIELD_INITIALIZATION_IN_PATTERN);
        myTree.addNode(8, FIELD_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, EQ);
        myTree.addNode(8, CONSTANT_PATTERN);
        myTree.addNode(9, INTEGER_LITERAL, "0");
        myTree.addNode(7, SEMICOLON);
        myTree.addNode(7, RECORD_FIELD_INITIALIZATION_IN_PATTERN);
        myTree.addNode(8, FIELD_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "b");
        myTree.addNode(8, EQ);
        myTree.addNode(8, CONSTANT_PATTERN);
        myTree.addNode(9, INTEGER_LITERAL, "1");
        myTree.addNode(7, RBRACE);
        addEndNodes();

        doTest("match a with {a = 0; b = 1} -> 0", myTree.getStringRepresentation());
    }

    public void testListPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, LIST_PATTERN);
        myTree.addNode(7, LBRACKET);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, SEMICOLON);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "2");
        myTree.addNode(7, RBRACKET);
        addEndNodes();

        doTest("match a with [1; 2] -> 0", myTree.getStringRepresentation());
    }

    public void testHeadTailPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, HEAD_TAIL_PATTERN);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, COLON_COLON);
        myTree.addNode(7, LIST_PATTERN);
        myTree.addNode(8, LBRACKET);
        myTree.addNode(8, CONSTANT_PATTERN);
        myTree.addNode(9, INTEGER_LITERAL, "2");
        myTree.addNode(8, RBRACKET);
        addEndNodes();

        doTest("match a with 1 :: [2] -> 0", myTree.getStringRepresentation());
    }

    public void testArrayPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, ARRAY_PATTERN);
        myTree.addNode(7, LBRACKET_VBAR);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        myTree.addNode(7, SEMICOLON);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "2");
        myTree.addNode(7, VBAR_RBRACKET);
        addEndNodes();

        doTest("match a with [|1; 2|] -> 0", myTree.getStringRepresentation());
    }

    public void testLazyPattern() throws Exception {
        addStartNodes();
        myTree.addNode(6, LAZY_PATTERN);
        myTree.addNode(7, LAZY_KEYWORD);
        myTree.addNode(7, CONSTANT_PATTERN);
        myTree.addNode(8, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with lazy 1 -> 0", myTree.getStringRepresentation());
    }

    public void testPriorities() throws Exception {
        String text1 = getTreeIgnoringParentheses("match a with a | b as c -> 0");
        String text2 = getTreeIgnoringParentheses("match a with ((a | b) as c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with a | b, c -> 0");
        text2 = getTreeIgnoringParentheses("match a with (a | (b, c)) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with a, b | c -> 0");
        text2 = getTreeIgnoringParentheses("match a with ((a, b) | c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with a, b :: c -> 0");
        text2 = getTreeIgnoringParentheses("match a with (a, (b :: c)) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with a :: b, c -> 0");
        text2 = getTreeIgnoringParentheses("match a with ((a :: b), c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with a :: Constr b -> 0");
        text2 = getTreeIgnoringParentheses("match a with (a :: (Constr b)) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with Constr a :: b -> 0");
        text2 = getTreeIgnoringParentheses("match a with ((Constr a) :: b) -> 0");

        assertEquals(text1, text2);
    }

    public void testAssociativity() throws Exception {
        String text1 = getTreeIgnoringParentheses("match a with a | b | c -> 0");
        String text2 = getTreeIgnoringParentheses("match a with ((a | b) | c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("match a with a :: b :: c -> 0");
        text2 = getTreeIgnoringParentheses("match a with (a :: (b :: c)) -> 0");

        assertEquals(text1, text2);
    }

    private void addStartNodes() {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, MATCH_EXPRESSION);
        myTree.addNode(5, MATCH_KEYWORD);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, WITH_KEYWORD);
        myTree.addNode(5, PATTERN_MATCHING);
    }

    private void addEndNodes() {
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
    }
}
