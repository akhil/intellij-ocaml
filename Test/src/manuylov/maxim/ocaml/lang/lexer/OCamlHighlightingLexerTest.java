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

package manuylov.maxim.ocaml.lang.lexer;

import com.intellij.lexer.Lexer;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public class OCamlHighlightingLexerTest extends BaseLexerTest {
    public void testCharLiteral() {
        doTest("'a", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.LCFC_IDENTIFIER, "a"));
        doTest("'a'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "a"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\\\'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\\\"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\\"'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\\""), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\''", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\'"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\n'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\n"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\t'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\t"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\b'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\b"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\r'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\r"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\ '", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\ "), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\123'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\123"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
        doTest("'\\xBD'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\xBD"), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
    }

    public void testStringLiteral() {
        doTest("\"a", token(OCamlTokenTypes.OPENING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "a"));
        doTest("\"a\"", token(OCamlTokenTypes.OPENING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "a"), token(OCamlTokenTypes.CLOSING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE));
        doTest("\"abcdefskdn3\"", token(OCamlTokenTypes.OPENING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "abcdefskdn3"), token(OCamlTokenTypes.CLOSING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE));
        doTest("\"abc\\n\\t\\rdef\"", token(OCamlTokenTypes.OPENING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "abc"), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\n\\t\\r"), token(OCamlTokenTypes.REGULAR_CHARS, "def"), token(OCamlTokenTypes.CLOSING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE));
        doTest("\"abc\\\n   \t \\rdef\"", token(OCamlTokenTypes.OPENING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "abc"), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\\n   \t \\r"), token(OCamlTokenTypes.REGULAR_CHARS, "def"), token(OCamlTokenTypes.CLOSING_DOUBLE_QUOTE, Keywords.DOUBLE_QUOTE));
    }

    public void testQuote() {
        doTest("'", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE));
    }

    public void testEmptyChar() {
        doTest("''", token(OCamlTokenTypes.OPENING_QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.CLOSING_QUOTE, Keywords.QUOTE));
    }

    protected Lexer createLexer() {
        return new OCamlHighlightingLexer();
    }
}
