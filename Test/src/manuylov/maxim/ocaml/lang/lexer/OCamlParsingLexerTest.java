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
import com.intellij.psi.TokenType;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public class OCamlParsingLexerTest extends BaseLexerTest {
    public void testCharLiteral() {
        doTest("'a", token(OCamlTokenTypes.QUOTE, Keywords.QUOTE), token(OCamlTokenTypes.LCFC_IDENTIFIER, "a"));
        doTest("'a'", token(OCamlTokenTypes.CHAR_LITERAL, "'a'"));
        doTest("'\\\\'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\\\'"));
        doTest("'\\\"'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\\"'"));
        doTest("'\\''", token(OCamlTokenTypes.CHAR_LITERAL, "'\\''"));
        doTest("'\\n'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\n'"));
        doTest("'\\t'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\t'"));
        doTest("'\\b'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\b'"));
        doTest("'\\r'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\r'"));
        doTest("'\\ '", token(OCamlTokenTypes.CHAR_LITERAL, "'\\ '"));
        doTest("'\\123'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\123'"));
        doTest("'\\xBD'", token(OCamlTokenTypes.CHAR_LITERAL, "'\\xBD'"));
    }

    public void testStringLiteral() {
        doTest("\"a", token(TokenType.BAD_CHARACTER, Keywords.DOUBLE_QUOTE), token(OCamlTokenTypes.LCFC_IDENTIFIER, "a"));
        doTest("\"a\"", token(OCamlTokenTypes.STRING_LITERAL, "\"a\""));
        doTest("\"abcdefskdn3\"", token(OCamlTokenTypes.STRING_LITERAL, "\"abcdefskdn3\""));
        doTest("\"abc\\n\\t\\rdef\"", token(OCamlTokenTypes.STRING_LITERAL, "\"abc\\n\\t\\rdef\""));
        doTest("\"abc\\\n   \t \rdef\"", token(OCamlTokenTypes.STRING_LITERAL, "\"abc\\\n   \t \rdef\""));
    }

    public void testQuote() {
        doTest("'", token(OCamlTokenTypes.QUOTE, Keywords.QUOTE));
    }

    public void testEmptyChar() {
        doTest("''", token(OCamlTokenTypes.EMPTY_CHAR_LITERAL, "''"));
    }

    protected Lexer createLexer() {
        return new OCamlParsingLexer();
    }
}
