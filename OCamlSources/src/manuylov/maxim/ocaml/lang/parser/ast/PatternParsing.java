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
import manuylov.maxim.ocaml.lang.parser.ast.util.MultiMarker;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 22.02.2009
 */
class PatternParsing extends Parsing {
    public static void parsePattern(@NotNull final PsiBuilder builder) {
        if (!tryParsePattern(builder)) {
            builder.error(Strings.PATTERN_EXPECTED);
        }
    }

    private static boolean tryParsePattern(@NotNull final PsiBuilder builder) {
        return tryParseAsPattern(builder);
    }

    private static boolean tryParseAsPattern(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker asPatternMarker = builder.mark();

        if (!tryParseOrPattern(builder)) {
            asPatternMarker.drop();
            return false;
        }

        if (ignore(builder, OCamlTokenTypes.AS_KEYWORD)) {
            NameParsing.parseValueName(builder, NameParsing.NameType.PATTERN);

            asPatternMarker.done(OCamlElementTypes.AS_PATTERN);
        }
        else {
            asPatternMarker.drop();
        }


        return true;
    }

    private static boolean tryParseOrPattern(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker orPatternMarker = builder.mark();

        if (!tryParseCommaPattern(builder)) {
            orPatternMarker.drop();
            return false;
        }

        while (ignore(builder, OCamlTokenTypes.VBAR)) {
            if (!tryParseCommaPattern(builder)) {
                builder.error(Strings.PATTERN_EXPECTED);
            }

            orPatternMarker.done(OCamlElementTypes.OR_PATTERN);
            orPatternMarker = orPatternMarker.precede();
        }

        orPatternMarker.drop();

        return true;
    }

    private static boolean tryParseCommaPattern(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker commaPatternMarker = builder.mark();

        if (!tryParseHeadTailPattern(builder)) {
            commaPatternMarker.drop();
            return false;
        }

        boolean commaParsed = false;

        while (ignore(builder, OCamlTokenTypes.COMMA)) {
            commaParsed = true;

            if (!tryParseHeadTailPattern(builder)) {
                builder.error(Strings.PATTERN_EXPECTED);
            }
        }

        if (commaParsed) {
            commaPatternMarker.done(OCamlElementTypes.COMMA_PATTERN);
        }
        else {
            commaPatternMarker.drop();
        }

        return true;
    }

    private static boolean tryParseHeadTailPattern(@NotNull final PsiBuilder builder) {
        final MultiMarker headTailPatternMarker = new MultiMarker(builder);
        headTailPatternMarker.mark();

        if (!tryParseConstructorPattern(builder)) {
            headTailPatternMarker.drop();
            return false;
        }

        while (ignore(builder, OCamlTokenTypes.COLON_COLON)) {
            headTailPatternMarker.mark();

            if (!tryParseConstructorPattern(builder)) {
                builder.error(Strings.PATTERN_EXPECTED);
            }
        }

        headTailPatternMarker.dropLast();
        headTailPatternMarker.done(OCamlElementTypes.HEAD_TAIL_PATTERN);

        return true;
    }

    private static boolean tryParseConstructorPattern(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker constructorApplicationPatternMarker = builder.mark();

        if (NameParsing.tryParseConstructorPath(builder, NameParsing.NameType.PATTERN)) {
            if (tryParseSimplePattern(builder)) {
                constructorApplicationPatternMarker.done(OCamlElementTypes.CONSTRUCTOR_APPLICATION_PATTERN);
            }
            else {
                constructorApplicationPatternMarker.drop();
            }
        }
        else {
            constructorApplicationPatternMarker.drop();

            if (!tryParseSimplePattern(builder)) {
                return false;
            }
        }

        return true;
    }

    private static boolean tryParseSimplePattern(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.ACCENT)) {
            NameParsing.parseTagName(builder);

            if (tryParsePattern(builder)) {
                marker.done(OCamlElementTypes.TAGGED_PATTERN);
            }
            else {
                marker.done(OCamlElementTypes.CONSTANT_PATTERN);
            }
        }
        else if (ignore(builder, OCamlTokenTypes.HASH)) {
            NameParsing.parseTypeConstructorName(builder);

            marker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_PATTERN);
        }
        else if (ignore(builder, OCamlTokenTypes.LAZY_KEYWORD)) {
            parsePattern(builder);

            marker.done(OCamlElementTypes.LAZY_PATTERN);
        }
        else if (ignore(builder, OCamlTokenTypes.LBRACKET)) {
            if (!ignore(builder, OCamlTokenTypes.RBRACKET)) {
                parsePattern(builder);

                while (ignore(builder, OCamlTokenTypes.SEMICOLON)) {
                    parsePattern(builder);
                }

                checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);
            }

            marker.done(OCamlElementTypes.LIST_PATTERN);
        }
        else if (ignore(builder, OCamlTokenTypes.LBRACKET_VBAR)) {
            if (!ignore(builder, OCamlTokenTypes.VBAR_RBRACKET)) {
                parsePattern(builder);

                while (ignore(builder, OCamlTokenTypes.SEMICOLON)) {
                    parsePattern(builder);
                }

                checkMatches(builder, OCamlTokenTypes.VBAR_RBRACKET, Strings.VBAR_RBRACKET_EXPECTED);
            }

            marker.done(OCamlElementTypes.ARRAY_PATTERN);
        }
        else if (ignore(builder, OCamlTokenTypes.LBRACE)) {
            parseRecordFieldInitialization(builder);

            while (ignore(builder, OCamlTokenTypes.SEMICOLON)) {
                parseRecordFieldInitialization(builder);
            }

            checkMatches(builder, OCamlTokenTypes.RBRACE, Strings.RBRACE_EXPECTED);

            marker.done(OCamlElementTypes.RECORD_PATTERN);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LPAR) {
            if (NameParsing.tryParseValueName(builder, NameParsing.NameType.PATTERN)) {
                marker.drop();
                return true;
            }

            ignore(builder, OCamlTokenTypes.LPAR);

            final boolean patternParsed = tryParsePattern(builder);

            if (ignore(builder, OCamlTokenTypes.COLON)) {
                TypeParsing.parseTypeExpression(builder);

                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

                marker.done(OCamlElementTypes.TYPE_CONSTRAINT_PATTERN);
            }
            else if (ignore(builder, OCamlTokenTypes.RPAR)) {
                if (patternParsed) {
                    marker.done(OCamlElementTypes.PARENTHESES_PATTERN);
                }
                else {
                    marker.done(OCamlElementTypes.CONSTANT_PATTERN);
                }
            }
            else {
                marker.drop();

                builder.error(Strings.RPAR_OR_COLON_EXPECTED);
            }
        }
        else if (OCamlTokenTypes.CHAR_LITERALS.contains(builder.getTokenType()) && getNextTokenType(builder) == OCamlTokenTypes.DOT_DOT) {
            NameParsing.parseCharLiteral(builder);
            builder.advanceLexer();
            NameParsing.parseCharLiteral(builder);

            marker.done(OCamlElementTypes.CHAR_RANGE_PATTERN);
        }
        else {
            marker.drop();

            if (!tryParseUnderscore(builder) && !NameParsing.tryParseValueName(builder, NameParsing.NameType.PATTERN) &&
                !NameParsing.tryParseConstant(builder, NameParsing.NameType.PATTERN)) {
                return false;
            }
        }

        return true;
    }

    private static boolean tryParseUnderscore(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.UNDERSCORE)) {
            marker.done(OCamlElementTypes.UNDERSCORE_PATTERN);
            return true;
        }

        marker.drop();
        return false;
    }

    private static void parseRecordFieldInitialization(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker fieldInitializationMarker = builder.mark();

        NameParsing.parseFieldPath(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parsePattern(builder);

        fieldInitializationMarker.done(OCamlElementTypes.RECORD_FIELD_INITIALIZATION_IN_PATTERN);
    }
}
