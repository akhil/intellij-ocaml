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
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.ast.util.MultiMarker;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

/**
 * @author Maxim.Manuylov
 *         Date: 13.02.2009
 */
class ExpressionParsing extends Parsing {
    public static void parseExpression(@NotNull final PsiBuilder builder) {
        if (!tryParseExpression(builder)) {
            builder.error(Strings.EXPRESSION_EXPECTED);
        }
    }

    public static void parseParameter(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker parameterMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.TILDE)) {
            if (ignore(builder, OCamlTokenTypes.LPAR)) {
                NameParsing.parseLabelNameProbablyWithColon(builder, new Runnable() {
                    public void run() {
                        TypeParsing.parseTypeExpression(builder);
                    }
                }, true);

                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);
            }
            else {
                NameParsing.parseLabelNameProbablyWithColon(builder, new Runnable() {
                    public void run() {
                        PatternParsing.parsePattern(builder);
                    }
                }, true);
            }
        }
        else if (ignore(builder, OCamlTokenTypes.QUEST)) {
            if (ignore(builder, OCamlTokenTypes.LPAR)) {
                NameParsing.parseLabelNameProbablyWithColon(builder, new Runnable() {
                    public void run() {
                        TypeParsing.parseTypeExpression(builder);
                    }
                }, true);

                if (ignore(builder, OCamlTokenTypes.EQ)) {
                    parseExpression(builder);
                }

                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);
            }
            else {
                NameParsing.parseLabelNameProbablyWithColon(builder, new Runnable() {
                    public void run() {
                        if (ignore(builder, OCamlTokenTypes.LPAR)) {
                            PatternParsing.parsePattern(builder);

                            if (ignore(builder, OCamlTokenTypes.COLON)) {
                                TypeParsing.parseTypeExpression(builder);
                            }

                            if (ignore(builder, OCamlTokenTypes.EQ)) {
                                parseExpression(builder);
                            }

                            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);
                        }
                        else {
                            PatternParsing.parsePattern(builder);
                        }
                    }
                }, true);
            }
        }
        else {
            PatternParsing.parsePattern(builder);
        }

        parameterMarker.done(OCamlElementTypes.PARAMETER);
    }

    public static boolean tryParseArgument(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker argumentMarker = builder.mark();

        if (ignore(builder, TokenSet.create(OCamlTokenTypes.TILDE, OCamlTokenTypes.QUEST))) {
            NameParsing.parseLabelNameProbablyWithColon(builder, new Runnable() {
                public void run() {
                    parseHashDotDotParDotBracketExpression(builder);
                }
            }, false);
        }
        else {
            if (!tryParseHashDotDotParDotBracketExpression(builder)) {
                argumentMarker.drop();
                return false;
            }
        }

        argumentMarker.done(OCamlElementTypes.ARGUMENT);

        return true;
    }

    public static boolean tryParseExpression(@NotNull final PsiBuilder builder) {
        return tryParseLetMatchFunFunctionTryExpressions(builder);
    }

    private static void parseAssignmentExpression(@NotNull final PsiBuilder builder) {
        if (!tryParseAssignmentExpression(builder)) {
            builder.error(Strings.EXPRESSION_EXPECTED);
        }
    }

    private static void parseHashDotDotParDotBracketExpression(@NotNull final PsiBuilder builder) {
        if (!tryParseHashDotDotParDotBracketExpression(builder)) {
            builder.error(Strings.EXPRESSION_EXPECTED);
        }
    }

    private static void parseInstanceDuplicatingExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker instanceDuplicatingExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LBRACE_LT, Strings.LBRACE_LT_EXPECTED);

        if (!ignore(builder, OCamlTokenTypes.GT_RBRACE)) {
            do {
                NameParsing.parseInstVarName(builder, NameParsing.NameType.NONE);

                checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

                if (!tryParseExpressionStartingWithKeyword(builder)) {
                    parseAssignmentExpression(builder);
                }
            } while (ignore(builder, OCamlTokenTypes.SEMICOLON));

            checkMatches(builder, OCamlTokenTypes.GT_RBRACE, Strings.GT_RBRACE_EXPECTED);
        }

        instanceDuplicatingExpressionMarker.done(OCamlElementTypes.INSTANCE_DUPLICATING_EXPRESSION);
    }

    private static void parseRecordExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker recordExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LBRACE, Strings.LBRACE_EXPECTED);

        final PsiBuilder.Marker tempMarker = builder.mark();

        parseAssignmentExpression(builder);

        final boolean withParsed;

        if (ignore(builder, OCamlTokenTypes.WITH_KEYWORD)) {
            withParsed = true;
            tempMarker.drop();
        }
        else {
            withParsed = false;
            tempMarker.rollbackTo();
        }

        parseFieldInitialization(builder);

        while (ignore(builder, OCamlTokenTypes.SEMICOLON)) {
            parseFieldInitialization(builder);
        }

        checkMatches(builder, OCamlTokenTypes.RBRACE, Strings.RBRACE_EXPECTED);

        recordExpressionMarker.done(withParsed ? OCamlElementTypes.INHERITED_RECORD_EXPRESSION : OCamlElementTypes.RECORD_EXPRESSION);
    }

    private static void parseFieldInitialization(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker fieldInitializationMarker = builder.mark();

        NameParsing.parseFieldPath(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        if (!tryParseExpressionStartingWithKeyword(builder)) {
            parseAssignmentExpression(builder);
        }

        fieldInitializationMarker.done(OCamlElementTypes.RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
    }

    private static void parseArrayExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker arrayExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LBRACKET_VBAR, Strings.LBRACKET_VBAR_EXPECTED);

        tryParseSemicolonExpression(builder, false);

        checkMatches(builder, OCamlTokenTypes.VBAR_RBRACKET, Strings.VBAR_RBRACKET_EXPECTED);

        arrayExpressionMarker.done(OCamlElementTypes.ARRAY_EXPRESSION);
    }

    private static void parseListExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker listExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LBRACKET, Strings.LBRACKET_EXPECTED);

        tryParseSemicolonExpression(builder, false);

        checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);

        listExpressionMarker.done(OCamlElementTypes.LIST_EXPRESSION);
    }

    private static void parseParenthesesExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker castingOrTypeConstraintExpressionMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.BEGIN_KEYWORD)) {
            parseExpression(builder);

            checkMatches(builder, OCamlTokenTypes.END_KEYWORD, Strings.END_KEYWORD_EXPECTED);

            castingOrTypeConstraintExpressionMarker.done(OCamlElementTypes.PARENTHESES_EXPRESSION);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LPAR) {
            if (NameParsing.tryParseValueName(builder, NameParsing.NameType.NONE)) {
                castingOrTypeConstraintExpressionMarker.drop();
                return;
            }

            ignore(builder, OCamlTokenTypes.LPAR);

            if (!tryParseExpression(builder)) {
                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);
                castingOrTypeConstraintExpressionMarker.done(OCamlElementTypes.CONSTANT_EXPRESSION);
                return;
            }

            if (ignore(builder, OCamlTokenTypes.COLON)) {
                TypeParsing.parseTypeExpression(builder);

                if (ignore(builder, OCamlTokenTypes.COLON_GT)) {
                    TypeParsing.parseTypeExpression(builder);

                    checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

                    castingOrTypeConstraintExpressionMarker.done(OCamlElementTypes.CASTING_EXPRESSION);
                }
                else {
                    checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_OR_COLON_GT_EXPECTED);

                    castingOrTypeConstraintExpressionMarker.done(OCamlElementTypes.TYPE_CONSTRAINT_EXPRESSION);
                }
            }
            else if (ignore(builder, OCamlTokenTypes.COLON_GT)) {
                TypeParsing.parseTypeExpression(builder);

                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

                castingOrTypeConstraintExpressionMarker.done(OCamlElementTypes.CASTING_EXPRESSION);
            }
            else {
                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_OR_COLON_OR_COLON_GT_EXPECTED);
                castingOrTypeConstraintExpressionMarker.done(OCamlElementTypes.PARENTHESES_EXPRESSION);
            }
        }
        else {
            builder.error(Strings.BEGIN_OR_LPAR_EXPECTED);
            castingOrTypeConstraintExpressionMarker.drop();
        }
    }

    private static void parseWhileExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker whileExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.WHILE_KEYWORD, Strings.WHILE_KEYWORD_EXPECTED);

        parseExpression(builder);

        checkMatches(builder, OCamlTokenTypes.DO_KEYWORD, Strings.DO_KEYWORD_EXPECTED);

        parseExpression(builder);

        checkMatches(builder, OCamlTokenTypes.DONE_KEYWORD, Strings.DONE_KEYWORD_EXPECTED);

        whileExpressionMarker.done(OCamlElementTypes.WHILE_EXPRESSION);
    }

    private static void parseForExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker forExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.FOR_KEYWORD, Strings.FOR_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        NameParsing.parseForExpressionIndexVariableName(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parseExpression(builder);

        if (!ignore(builder, TokenSet.create(OCamlTokenTypes.TO_KEYWORD, OCamlTokenTypes.DOWNTO_KEYWORD))) {
            builder.error(Strings.TO_OR_DOWNTO_EXPECTED);
        }

        parseExpression(builder);

        marker.done(OCamlElementTypes.FOR_EXPRESSION_BINDING);

        checkMatches(builder, OCamlTokenTypes.DO_KEYWORD, Strings.DO_KEYWORD_EXPECTED);

        parseExpression(builder);

        checkMatches(builder, OCamlTokenTypes.DONE_KEYWORD, Strings.DONE_KEYWORD_EXPECTED);

        forExpressionMarker.done(OCamlElementTypes.FOR_EXPRESSION);
    }

    private static void parseIfExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker ifExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.IF_KEYWORD, Strings.IF_KEYWORD_EXPECTED);

        parseExpression(builder);

        checkMatches(builder, OCamlTokenTypes.THEN_KEYWORD, Strings.THEN_KEYWORD_EXPECTED);

        if (!tryParseExpressionStartingWithKeyword(builder)) {
            parseAssignmentExpression(builder);
        }

        if (ignore(builder, OCamlTokenTypes.ELSE_KEYWORD)) {
            if (!tryParseExpressionStartingWithKeyword(builder)) {
                parseAssignmentExpression(builder);
            }
        }

        ifExpressionMarker.done(OCamlElementTypes.IF_EXPRESSION);
    }

    private static void parseTryExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker tryExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.TRY_KEYWORD, Strings.TRY_KEYWORD_EXPECTED);

        parseExpression(builder);

        checkMatches(builder, OCamlTokenTypes.WITH_KEYWORD, Strings.WITH_KEYWORD_EXPECTED);

        parsePatternMatching(builder);

        tryExpressionMarker.done(OCamlElementTypes.TRY_EXPRESSION);
    }

    private static void parseFunctionExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker functionExpressionMatching = builder.mark();

        checkMatches(builder, OCamlTokenTypes.FUNCTION_KEYWORD, Strings.FUNCTION_KEYWORD_EXPECTED);

        parsePatternMatching(builder);

        functionExpressionMatching.done(OCamlElementTypes.FUNCTION_EXPRESSION);
    }

    private static void parseFunExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker funExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.FUN_KEYWORD, Strings.FUN_KEYWORD_EXPECTED);

        parseParameter(builder);

        final Runnable parsing = new Runnable() {
            public void run() {
                parseParameter(builder);
            }
        };

        while (!builder.eof() && !TokenSet.create(OCamlTokenTypes.WHEN_KEYWORD, OCamlTokenTypes.MINUS_GT).contains(builder.getTokenType())) {
            advanceLexerIfNothingWasParsed(builder, parsing);
        }

        doParseMatching(builder);

        funExpressionMarker.done(OCamlElementTypes.FUN_EXPRESSION);
    }

    private static void parseMatchExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker matchExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.MATCH_KEYWORD, Strings.MATCH_KEYWORD_EXPECTED);

        parseExpression(builder);

        checkMatches(builder, OCamlTokenTypes.WITH_KEYWORD, Strings.WITH_KEYWORD_EXPECTED);

        parsePatternMatching(builder);

        matchExpressionMarker.done(OCamlElementTypes.MATCH_EXPRESSION);
    }

    private static void parsePatternMatching(@NotNull final PsiBuilder builder) {
        ignore(builder, OCamlTokenTypes.VBAR);

        PsiBuilder.Marker patternMatchingMarker = builder.mark();

        PatternParsing.parsePattern(builder);

        doParseMatching(builder);

        patternMatchingMarker.done(OCamlElementTypes.PATTERN_MATCHING);

        while (ignore(builder, OCamlTokenTypes.VBAR)) {
            patternMatchingMarker = builder.mark();

            PatternParsing.parsePattern(builder);

            doParseMatching(builder);

            patternMatchingMarker.done(OCamlElementTypes.PATTERN_MATCHING);
        }
    }

    private static void parseAssertExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker assertExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.ASSERT_KEYWORD, Strings.ASSERT_KEYWORD_EXPECTED);

        parseHashDotDotParDotBracketExpression(builder);

        assertExpressionMarker.done(OCamlElementTypes.ASSERT_EXPRESSION);
    }

    private static void parseLazyExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker lazyExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LAZY_KEYWORD, Strings.LAZY_KEYWORD_EXPECTED);

        parseHashDotDotParDotBracketExpression(builder);

        lazyExpressionMarker.done(OCamlElementTypes.LAZY_EXPRESSION);
    }

    private static void doParseMatching(@NotNull final PsiBuilder builder) {
        if (ignore(builder, OCamlTokenTypes.WHEN_KEYWORD)) {
            parseExpression(builder);
        }

        checkMatches(builder, OCamlTokenTypes.MINUS_GT, Strings.MINUS_GT_EXPECTED);

        parseExpression(builder);
    }

    private static boolean tryParseLetMatchFunFunctionTryExpressions(@NotNull final PsiBuilder builder) {
        return doTryParseLetMatchFunFunctionTryExpression(builder) || tryParseSemicolonExpression(builder, true);
    }

    private static boolean tryParseSemicolonExpression(@NotNull final PsiBuilder builder, final boolean useMarker) {
        final PsiBuilder.Marker semicolonExpressionMarker = builder.mark();

        if (!tryParseIfForWhileExpression(builder)) {
            semicolonExpressionMarker.drop();
            return false;
        }

        boolean semicolonParsed = false;

        while (ignore(builder, OCamlTokenTypes.SEMICOLON)) {
            semicolonParsed = true;
            if (!tryParseExpressionStartingWithKeyword(builder)) {
                parseAssignmentExpression(builder);
            }
        }

        if (semicolonParsed && useMarker) {
            semicolonExpressionMarker.done(OCamlElementTypes.SEMICOLON_EXPRESSION);
        } else {
            semicolonExpressionMarker.drop();
        }

        return true;
    }

    private static boolean tryParseIfForWhileExpression(@NotNull final PsiBuilder builder) {
        return doTryParseIfForWhileExpression(builder) || tryParseAssignmentExpression(builder);
    }

    private static boolean tryParseAssignmentExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker assignmentExpressionMarker = new MultiMarker(builder);
        assignmentExpressionMarker.mark();

        if (!tryParseCommaExpression(builder)) {
            assignmentExpressionMarker.drop();
            return false;
        }

        while (ignore(builder, TokenSet.create(OCamlTokenTypes.COLON_EQ, OCamlTokenTypes.LT_MINUS))) {
            assignmentExpressionMarker.mark();

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseCommaExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        assignmentExpressionMarker.dropLast();
        assignmentExpressionMarker.done(OCamlElementTypes.ASSIGNMENT_EXPRESSION);

        return true;
    }

    private static boolean tryParseCommaExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker commaExpressionMarker = builder.mark();

        if (!tryParseOrExpression(builder)) {
            commaExpressionMarker.drop();
            return false;
        }

        boolean commaParsed = false;

        while (ignore(builder, OCamlTokenTypes.COMMA)) {
            commaParsed = true;

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseOrExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        if (commaParsed) {
            commaExpressionMarker.done(OCamlElementTypes.COMMA_EXPRESSION);
        }
        else {
            commaExpressionMarker.drop();
        }

        return true;
    }

    private static boolean tryParseOrExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker orExpressionMarker = new MultiMarker(builder);
        orExpressionMarker.mark();

        if (!tryParseAndExpression(builder)) {
            orExpressionMarker.drop();
            return false;
        }

        while (ignore(builder, TokenSet.create(OCamlTokenTypes.OR_KEYWORD, OCamlTokenTypes.VBAR_VBAR))) {
            orExpressionMarker.mark();

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseAndExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        orExpressionMarker.dropLast();
        orExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);

        return true;
    }

    private static boolean tryParseAndExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker andExpressionMarker = new MultiMarker(builder);
        andExpressionMarker.mark();

        if (!tryParseEqLtGtVbarAmpDollarOperatorExpression(builder)) {
            andExpressionMarker.drop();
            return false;
        }

        while (ignore(builder, TokenSet.create(OCamlTokenTypes.AMP, OCamlTokenTypes.AMP_AMP))) {
            andExpressionMarker.mark();

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseEqLtGtVbarAmpDollarOperatorExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        andExpressionMarker.dropLast();
        andExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);

        return true;
    }

    private static boolean tryParseEqLtGtVbarAmpDollarOperatorExpression(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker binaryExpressionMarker = builder.mark();

        if (!tryParseAtXorOperatorExpression(builder)) {
            binaryExpressionMarker.drop();
            return false;
        }

        while (tryParseInfixOperator(builder, TokenSet.create(OCamlTokenTypes.AMP, OCamlTokenTypes.AMP_AMP, OCamlTokenTypes.VBAR_VBAR), Collections.<String>emptySet(),
                                     TokenSet.EMPTY, Keywords.EQ, Keywords.LT, Keywords.GT, Keywords.VBAR, Keywords.AMP, Keywords.DOLLAR)) {
            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseAtXorOperatorExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }

            binaryExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);
            binaryExpressionMarker = binaryExpressionMarker.precede();
        }

        binaryExpressionMarker.drop();

        return true;
    }

    private static boolean tryParseInfixOperator(@NotNull final PsiBuilder builder, @NotNull final TokenSet ignoredOperators, @NotNull final Set<String> ignoredFirstChars, @NotNull final TokenSet operators, @NotNull final String... firstChars) {
        if (!OCamlTokenTypes.INFIX_OPERATORS.contains(builder.getTokenType())) return false;
        if (ignoredOperators.contains(builder.getTokenType())) return false;

        final String text = builder.getTokenText();
        if (text == null) return false;

        for (final String tokenStart : ignoredFirstChars) {
            if (text.startsWith(tokenStart)) {
                return false;
            }
        }

        boolean startsWithFirstChars = false;

        for (final String tokenStart : firstChars) {
            if (text.startsWith(tokenStart)) {
                startsWithFirstChars = true;
                break;
            }
        }

        if (!startsWithFirstChars && !operators.contains(builder.getTokenType())) return false;

        builder.advanceLexer();

        return true;
    }

    private static boolean tryParseAtXorOperatorExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker binaryExpressionMarker = new MultiMarker(builder);
        binaryExpressionMarker.mark();

        if (!tryParseHeadTailExpression(builder)) {
            binaryExpressionMarker.drop();
            return false;
        }

        while (tryParseInfixOperator(builder, TokenSet.EMPTY, Collections.<String>emptySet(), TokenSet.EMPTY, Keywords.AT, Keywords.XOR)) {
            binaryExpressionMarker.mark();

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseHeadTailExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        binaryExpressionMarker.dropLast();
        binaryExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);

        return true;
    }

    private static boolean tryParseHeadTailExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker headTailExpressionMarker = new MultiMarker(builder);
        headTailExpressionMarker.mark();

        if (!tryParseAddExpression(builder)) {
            headTailExpressionMarker.drop();
            return false;
        }

        while (ignore(builder, OCamlTokenTypes.COLON_COLON)) {
            headTailExpressionMarker.mark();

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseAddExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        headTailExpressionMarker.dropLast();
        headTailExpressionMarker.done(OCamlElementTypes.HEAD_TAIL_EXPRESSION);

        return true;
    }

    private static boolean tryParseAddExpression(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker binaryExpressionMarker = builder.mark();

        if (!tryParseMultExpression(builder)) {
            binaryExpressionMarker.drop();
            return false;
        }

        while (tryParseInfixOperator(builder, TokenSet.EMPTY, Collections.<String>emptySet(), TokenSet.EMPTY, Keywords.PLUS, Keywords.MINUS)) {
            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseMultExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }

            binaryExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);
            binaryExpressionMarker = binaryExpressionMarker.precede();
        }

        binaryExpressionMarker.drop();

        return true;
    }

    private static boolean tryParseMultExpression(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker binaryExpressionMarker = builder.mark();

        if (!tryParsePowerExpression(builder)) {
            binaryExpressionMarker.drop();
            return false;
        }

        while (tryParseInfixOperator(builder, TokenSet.EMPTY, Collections.singleton(Keywords.POWER),
                                     TokenSet.create(OCamlTokenTypes.MOD_KEYWORD, OCamlTokenTypes.LAND_KEYWORD, OCamlTokenTypes.LOR_KEYWORD, OCamlTokenTypes.LXOR_KEYWORD),
                                     Keywords.MULT, Keywords.DIV, Keywords.PERCENT)) {

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParsePowerExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }

            binaryExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);
            binaryExpressionMarker = binaryExpressionMarker.precede();
        }

        binaryExpressionMarker.drop();

        return true;
    }

    private static boolean tryParsePowerExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker binaryExpressionMarker = new MultiMarker(builder);
        binaryExpressionMarker.mark();

        if (!tryParseUnaryMinusExpression(builder)) {
            binaryExpressionMarker.drop();
            return false;
        }

        while (tryParseInfixOperator(builder, TokenSet.EMPTY, Collections.<String>emptySet(),
                                     TokenSet.create(OCamlTokenTypes.LSL_KEYWORD, OCamlTokenTypes.LSR_KEYWORD, OCamlTokenTypes.ASR_KEYWORD), Keywords.POWER)) {
            binaryExpressionMarker.mark();

            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseUnaryMinusExpression(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }
        }

        binaryExpressionMarker.dropLast();
        binaryExpressionMarker.done(OCamlElementTypes.BINARY_EXPRESSION);

        return true;
    }

    private static boolean tryParseUnaryMinusExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker negationExpressionMarker = builder.mark();

        if (ignore(builder, TokenSet.create(OCamlTokenTypes.MINUS, OCamlTokenTypes.MINUS_DOT))) {
            if (!tryParseExpressionStartingWithKeyword(builder) && !tryParseFunctionConstructorAssertLazy(builder)) {
                builder.error(Strings.EXPRESSION_EXPECTED);
            }

            negationExpressionMarker.done(OCamlElementTypes.UNARY_EXPRESSION);
        }
        else {
            negationExpressionMarker.drop();

            if (!tryParseFunctionConstructorAssertLazy(builder)) {
                return false;
            }
        }

        return true;
    }

    private static boolean tryParseFunctionConstructorAssertLazy(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.ASSERT_KEYWORD) {
            parseAssertExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LAZY_KEYWORD) {
            parseLazyExpression(builder);
        }
        else if (!tryParseConstructorApplicationExpression(builder)) {
            return tryParseFunctionApplicationExpression(builder);
        }

        return true;
    }

    private static boolean tryParseFunctionApplicationExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker functionApplicationExpressionMarker = builder.mark();

        if (!tryParseHashDotDotParDotBracketExpression(builder)) {
            functionApplicationExpressionMarker.drop();
            return false;
        }

        if (tryParseArgument(builder)) {
            final boolean[] shouldBreak = { false };

            final Runnable parsing = new Runnable() {
                public void run() {
                    if (!tryParseArgument(builder)) {
                        shouldBreak[0] = true;
                    }
                }
            };

            while (!builder.eof() && !shouldBreak[0]) {
                advanceLexerIfNothingWasParsed(builder, shouldBreak, parsing);
            }

            functionApplicationExpressionMarker.done(OCamlElementTypes.FUNCTION_APPLICATION_EXPRESSION);
        }
        else {
            functionApplicationExpressionMarker.drop();
        }

        return true;
    }

    private static boolean tryParseConstructorApplicationExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker constructorApplicationExpressionMarker = builder.mark();

        if (!NameParsing.tryParseConstructorPath(builder, NameParsing.NameType.EXPRESSION)) {
            constructorApplicationExpressionMarker.drop();
            return false;
        }

        if (tryParseHashDotDotParDotBracketExpression(builder)) {
            constructorApplicationExpressionMarker.done(OCamlElementTypes.CONSTRUCTOR_APPLICATION_EXPRESSION);
        }
        else {
            constructorApplicationExpressionMarker.drop();
        }

        return true;
    }

    private static boolean tryParseHashDotDotParDotBracketExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker hashDotDotParDotBracketExpressionMarker = builder.mark();

        if (!tryParsePrefixOperatorExpression(builder)) {
            hashDotDotParDotBracketExpressionMarker.drop();
            return false;
        }

        if (ignore(builder, OCamlTokenTypes.DOT)) {
            final PsiBuilder.Marker marker = builder.mark();

            if (ignore(builder, OCamlTokenTypes.LPAR)) {
                parseExpression(builder);

                checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

                marker.done(OCamlElementTypes.PARENTHESES);

                hashDotDotParDotBracketExpressionMarker.done(OCamlElementTypes.ARRAY_ELEMENT_ACCESSING_EXPRESSION);
            }
            else {
                marker.drop();

                final PsiBuilder.Marker bracketsMarker = builder.mark();

                if (ignore(builder, OCamlTokenTypes.LBRACKET)) {
                    parseExpression(builder);

                    checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);

                    bracketsMarker.done(OCamlElementTypes.BRACKETS);

                    hashDotDotParDotBracketExpressionMarker.done(OCamlElementTypes.STRING_CHAR_ACCESSING_EXPRESSION);
                }
                else {
                    bracketsMarker.drop();

                    NameParsing.parseFieldPath(builder);

                    hashDotDotParDotBracketExpressionMarker.done(OCamlElementTypes.RECORD_FIELD_ACCESSING_EXPRESSION);
                }
            }
        }
        else {
            if (ignore(builder, OCamlTokenTypes.HASH)) {
                NameParsing.parseMethodName(builder);

                hashDotDotParDotBracketExpressionMarker.done(OCamlElementTypes.CLASS_METHOD_ACCESSING_EXPRESSION);
            }
            else {
                hashDotDotParDotBracketExpressionMarker.drop();
            }
        }

        return true;
    }

    private static boolean tryParsePrefixOperatorExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker unaryExpressionMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.PREFIX_OPERATORS)) {
            unaryExpressionMarker.drop();

            return tryParseTaggedExpression(builder);
        }

        if (!tryParseTaggedExpression(builder)) {
            builder.error(Strings.EXPRESSION_EXPECTED);
        }

        unaryExpressionMarker.done(OCamlElementTypes.UNARY_EXPRESSION);

        return true;
    }

    private static boolean tryParseTaggedExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker taggedExpressionMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.ACCENT)) {
            NameParsing.parseTagName(builder);

            if (tryParseWrappedOrSimpleExpression(builder)) {
                taggedExpressionMarker.done(OCamlElementTypes.TAGGED_EXPRESSION);
            }
            else {
                taggedExpressionMarker.done(OCamlElementTypes.CONSTANT_EXPRESSION);
            }
        }
        else {
            taggedExpressionMarker.drop();

            if (!tryParseWrappedOrSimpleExpression(builder)) {
                return false;
            }
        }

        return true;
    }

    private static boolean tryParseWrappedOrSimpleExpression(@NotNull final PsiBuilder builder) {
        if (TokenSet.create(OCamlTokenTypes.BEGIN_KEYWORD, OCamlTokenTypes.LPAR).contains(builder.getTokenType())) {
            parseParenthesesExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LBRACKET) {
            parseListExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LBRACKET_VBAR) {
            parseArrayExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LBRACE) {
            parseRecordExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.LBRACE_LT) {
            parseInstanceDuplicatingExpression(builder);
        }
        else {
            return tryParseSimpleExpression(builder);
        }

        return true;
    }

    private static boolean tryParseSimpleExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker simpleExpressionMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.OBJECT_KEYWORD)) {
            ClassParsing.doParseClassBody(builder);

            checkMatches(builder, OCamlTokenTypes.END_KEYWORD, Strings.END_KEYWORD_EXPECTED);

            simpleExpressionMarker.done(OCamlElementTypes.OBJECT_CLASS_BODY_END_EXPRESSION);
        }
        else if (ignore(builder, OCamlTokenTypes.NEW_KEYWORD)) {
            NameParsing.parseClassPath(builder);

            simpleExpressionMarker.done(OCamlElementTypes.NEW_INSTANCE_EXPRESSION);
        }
        else {
            simpleExpressionMarker.drop();

            if (!NameParsing.tryParseValuePath(builder) && !NameParsing.tryParseConstant(builder, NameParsing.NameType.EXPRESSION)) {
                return false;
            }
        }

        return true;
    }

    private static boolean tryParseExpressionStartingWithKeyword(@NotNull final PsiBuilder builder) {
        return doTryParseLetMatchFunFunctionTryExpression(builder) || doTryParseIfForWhileExpression(builder);
    }

    private static boolean doTryParseLetMatchFunFunctionTryExpression(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.LET_KEYWORD) {
            LetParsing.parseLetExpression(builder, LetParsing.ExpressionType.Expression);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.MATCH_KEYWORD) {
            parseMatchExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.FUN_KEYWORD) {
            parseFunExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.FUNCTION_KEYWORD) {
            parseFunctionExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.TRY_KEYWORD) {
            parseTryExpression(builder);
        }
        else {
            return false;
        }

        return true;
    }

    private static boolean doTryParseIfForWhileExpression(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.IF_KEYWORD) {
            parseIfExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.FOR_KEYWORD) {
            parseForExpression(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.WHILE_KEYWORD) {
            parseWhileExpression(builder);
        }
        else {
            return false;
        }

        return true;
    }
}
