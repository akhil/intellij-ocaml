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

import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.parser.ast.testCase.ParsingTestCase;
import org.testng.annotations.Test;

import static com.intellij.psi.TokenType.ERROR_ELEMENT;
import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 20.03.2009
 */
@Test
public abstract class BaseStatementParsingTest extends ParsingTestCase {
    public void testExternal() throws Exception {
        myTree.addNode(3, EXTERNAL_DEFINITION);
        myTree.addNode(4, EXTERNAL_KEYWORD);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "myFunc");
        myTree.addNode(4, COLON);
        myTree.addNode(4, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, MINUS_GT);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(4, EQ);
        myTree.addNode(4, EXTERNAL_DECLARATION);
        myTree.addNode(5, STRING_LITERAL, "\"myFunc\"");

        doTest("external myFunc : int -> int = \"myFunc\"", myTree.getStringRepresentation());
    }

    public void testOpenDirective() throws Exception {
        myTree.addNode(3, OPEN_DIRECTIVE);
        myTree.addNode(4, OPEN_KEYWORD);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("open Module", myTree.getStringRepresentation());
    }

    public void testSemicolonSemicolon() throws Exception {
        myTree.addNode(3, SEMICOLON_SEMICOLON);

        doTest(";;", myTree.getStringRepresentation());
    }

    public void testCommentBlock() throws Exception {
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, " comment ");
        myTree.addNode(4, COMMENT_END, "*)");

        doTest("(* comment *)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_END, "*)");

        doTest("(**)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT_END, "*)");

        doTest("(*(**)(**)*)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT, "c");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT, "c");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_END, "*)");

        doTest("(*c(*c*)c(*c*)c*)", myTree.getStringRepresentation());
    }

    public void testUnclosedComment() throws Exception {
        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, " comment");
        myTree.addNode(3, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(* comment", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(3, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, UNCLOSED_COMMENT);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(3, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*(*", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(3, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*(**)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(3, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*(**)(**)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT, "c");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_BLOCK);
        myTree.addNode(5, COMMENT_BEGIN, "(*");
        myTree.addNode(5, COMMENT, "c");
        myTree.addNode(5, COMMENT_END, "*)");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(3, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*c(*c*)c(*c*)c", myTree.getStringRepresentation());
    }
}
