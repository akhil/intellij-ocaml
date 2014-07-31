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
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 10.02.2009
 */
class LetParsing extends Parsing {
    public static void parseLetExpression(@NotNull final PsiBuilder builder, @NotNull final ExpressionType expType) {
        final PsiBuilder.Marker letExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LET_KEYWORD, Strings.LET_KEYWORD_EXPECTED);

        ignore(builder, OCamlTokenTypes.REC_KEYWORD);

        do {
            parseLetBinding(builder);
        } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));

        checkMatches(builder, OCamlTokenTypes.IN_KEYWORD, Strings.IN_KEYWORD_EXPECTED);

        switch (expType) {
            case Expression: {
                ExpressionParsing.parseExpression(builder);

                letExpressionMarker.done(OCamlElementTypes.LET_EXPRESSION);

                break;
            }

            case ClassExpression: {
                ClassParsing.parseClassExpression(builder);

                letExpressionMarker.done(OCamlElementTypes.LET_CLASS_EXPRESSION);

                break;
            }
        }
    }

    public static boolean tryParseLetStatement(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker letStatementMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LET_KEYWORD)) {
            letStatementMarker.drop();
            return false;
        }

        ignore(builder, OCamlTokenTypes.REC_KEYWORD);

        do {
            parseLetBinding(builder);
        } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));

        if (builder.getTokenType() == OCamlTokenTypes.IN_KEYWORD) {
            letStatementMarker.rollbackTo();
            return false;
        }

        letStatementMarker.done(OCamlElementTypes.LET_STATEMENT);

        return true;
    }

    private static void parseLetBinding(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker letBindingMarker = builder.mark();

        PatternParsing.parsePattern(builder);

        letBindingMarker.done(OCamlElementTypes.LET_BINDING_PATTERN);

        if (ignore(builder, OCamlTokenTypes.EQ)) {
            ExpressionParsing.parseExpression(builder);
        }
        else {
            final Runnable parsing = new Runnable() {
                public void run() {
                    ExpressionParsing.parseParameter(builder);
                }
            };

            while (!builder.eof() && builder.getTokenType() != OCamlTokenTypes.COLON && builder.getTokenType() != OCamlTokenTypes.EQ) {
                advanceLexerIfNothingWasParsed(builder, parsing);
            }

            if (ignore(builder, OCamlTokenTypes.COLON)) {
                TypeParsing.parseTypeExpression(builder);
            }

            checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

            ExpressionParsing.parseExpression(builder);
        }

        letBindingMarker.precede().done(OCamlElementTypes.LET_BINDING);
    }

    public static enum ExpressionType {
        Expression,
        ClassExpression
    }
}
