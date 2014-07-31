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

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 10.02.2009
 */
abstract class Parsing {
    protected static void checkMatches(@NotNull final PsiBuilder builder, @NotNull final IElementType token, @NotNull final String message) {
        if (!ignore(builder, token)) {
            builder.error(message);
        }
    }

    protected static boolean ignore(@NotNull final PsiBuilder builder, @NotNull final IElementType token) {
        return ignore(builder, TokenSet.create(token));
    }

    protected static boolean ignore(@NotNull final PsiBuilder builder, @NotNull final TokenSet tokens) {
        if (tokens.contains(builder.getTokenType())) {
            builder.advanceLexer();
            return true;
        }
        return false;
    }

    protected static void advanceLexerIfNothingWasParsed(@NotNull final PsiBuilder builder, @NotNull final Runnable parsing) {
        advanceLexerIfNothingWasParsed(builder, new boolean[] { false }, parsing);
    }

    protected static void advanceLexerIfNothingWasParsed(@NotNull final PsiBuilder builder, @NotNull final boolean[] shouldBreak, @NotNull final Runnable parsing) {
        final int offsetBefore = builder.getCurrentOffset();

        parsing.run();

        if (builder.getCurrentOffset() == offsetBefore && !shouldBreak[0]) {
            if (builder.getTokenType() == null) {
                builder.error(Strings.UNEXPECTED_END_OF_FILE);
            }
            else {
                final PsiBuilder.Marker marker = builder.mark();
                builder.advanceLexer();
                marker.error(Strings.UNEXPECTED_TOKEN);
            }
        }
    }

    @Nullable
    protected static IElementType getNextTokenType(@NotNull final PsiBuilder builder) {
        return getTokenType(builder, 1);
    }

    @Nullable
    private static IElementType getTokenType(@NotNull final PsiBuilder builder, final int skipTokensCount) {
        final PsiBuilder.Marker marker = builder.mark();

        for (int i = 0; i < skipTokensCount; i++) {
            builder.getTokenType();
            builder.advanceLexer();
        }

        final IElementType token = builder.getTokenType();

        marker.rollbackTo();

        return token;
    }
}
