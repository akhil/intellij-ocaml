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

package manuylov.maxim.ocaml.lang.lexer.testCase;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.BaseOCamlTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public abstract class LexerTestCase extends BaseOCamlTestCase {
    protected void doTest(@NotNull final String text, @NotNull final String... expectedTokens) {
        final List<String> expectedTokensStr = new ArrayList<String>(expectedTokens.length);
        expectedTokensStr.addAll(Arrays.asList(expectedTokens));
        doTest(text, expectedTokensStr);
    }

    protected String token(@NotNull final IElementType tokenType, @NotNull final String tokenText) {
        return tokenType.toString() + "('" + tokenText + "')";
    }

    private void doTest(@NotNull final String text, @NotNull final List<String> expectedTokens) {
        final Lexer lexer = createLexer();
        lexer.start(text);
        final List<String> actualTokens = new ArrayList<String>();
        while (true) {
            final IElementType tokenType = lexer.getTokenType();
            if (tokenType == null) break;
            actualTokens.add(token(tokenType, getTokenText(lexer)));
            lexer.advance();
        }
        assertEquals(actualTokens, expectedTokens);
    }

    private String getTokenText(@NotNull final Lexer lexer) {
        return lexer.getBufferSequence().subSequence(lexer.getTokenStart(), lexer.getTokenEnd()).toString();
    }

    protected abstract Lexer createLexer();
}
