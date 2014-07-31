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

package manuylov.maxim.ocaml.editor;

import com.intellij.codeInsight.editorActions.QuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 08.05.2010
 */
public class OCamlQuoteHandler implements QuoteHandler {
    public boolean isClosingQuote(@NotNull final HighlighterIterator iterator, final int offset) {
        return isClosingQuote(iterator);
    }

    public boolean isOpeningQuote(@NotNull final HighlighterIterator iterator, final int offset) {
        return isOpeningQuote(iterator);
    }

    public boolean hasNonClosedLiteral(@NotNull final Editor editor, @NotNull final HighlighterIterator iterator, final int offset) {
        int start = iterator.getStart();
        try {
            if (isOpeningQuote(iterator)) {
                iterator.advance();
            }

            while (!iterator.atEnd() && isStringLiteral(iterator)) {
                iterator.advance();
            }

            return iterator.atEnd() || !isClosingQuote(iterator);  
        }
        finally {
            while (iterator.atEnd() || iterator.getStart() != start) iterator.retreat();
        }        
    }

    public boolean isInsideLiteral(@NotNull final HighlighterIterator iterator) {
        return isStringLiteral(iterator) || isClosingQuote(iterator);
    }

    private boolean isOpeningQuote(@NotNull final HighlighterIterator iterator) {
        return OCamlTokenTypes.QH_OPENING_QUOTES.contains(iterator.getTokenType());
    }

    private boolean isClosingQuote(@NotNull final HighlighterIterator iterator) {
        return OCamlTokenTypes.QH_CLOSING_QUOTES.contains(iterator.getTokenType());
    }

    private boolean isStringLiteral(@NotNull final HighlighterIterator iterator) {
        return OCamlTokenTypes.QH_STRING_LITERALS.contains(iterator.getTokenType());
    }
}