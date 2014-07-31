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
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.ast.util.MultiMarker;
import org.jetbrains.annotations.NotNull;

class TypeParsing extends Parsing {
    public static void parseTypeDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeDefinitionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.TYPE_KEYWORD, Strings.TYPE_KEYWORD_EXPECTED);

        do {
            parseTypeBinding(builder);
        } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));

        typeDefinitionMarker.done(OCamlElementTypes.TYPE_DEFINITION);
    }

    public static void parseTypeExpression(@NotNull final PsiBuilder builder) {
        if (!tryParseTypeExpression(builder)) {
            builder.error(Strings.TYPE_EXPRESSION_EXPECTED);
        }
    }

    public static void parsePolyTypeExpression(@NotNull final PsiBuilder builder) {
        if (!tryParsePolyTypeExpression(builder)) {
            builder.error(Strings.POLYMORPHIC_TYPE_EXPRESSION_EXPECTED);
        }
    }

    public static void parseTypeParameter(@NotNull final PsiBuilder builder, final boolean parsePlusAndMinus, final boolean isDefinition) {
        final PsiBuilder.Marker typeParameterMarker = builder.mark();

        if (parsePlusAndMinus) {
            ignore(builder, TokenSet.create(OCamlTokenTypes.PLUS, OCamlTokenTypes.MINUS));
        }

        checkMatches(builder, OCamlTokenTypes.QUOTE, Strings.QUOTE_EXPECTED);

        NameParsing.parseTypeParameterName(builder);

        if (isDefinition) {
            typeParameterMarker.done(parsePlusAndMinus ? OCamlElementTypes.PLUS_MINUS_TYPE_PARAMETER : OCamlElementTypes.TYPE_PARAMETER_DEFINITION);
        }
        else {
            typeParameterMarker.done(OCamlElementTypes.TYPE_PARAMETER);
        }
    }

    private static boolean tryParseTypeExpression(@NotNull final PsiBuilder builder) {
        return tryParseAsTypeExpression(builder);
    }

    private static void parseTypeBinding(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker typeParameterizedBindingMarker = null;

        if (builder.getTokenType() != OCamlTokenTypes.LCFC_IDENTIFIER) {
            typeParameterizedBindingMarker = builder.mark();
            parseTypeParameters(builder);
        }

        final PsiBuilder.Marker typeBindingMarker = builder.mark();

        NameParsing.parseTypeConstructorName(builder);

        if (builder.getTokenType() == OCamlTokenTypes.EQ || builder.getTokenType() == OCamlTokenTypes.CONSTRAINT_KEYWORD) {
            parseTypeInformation(builder);
        }

        typeBindingMarker.done(OCamlElementTypes.TYPE_BINDING);

        if (typeParameterizedBindingMarker != null) {
            typeParameterizedBindingMarker.done(OCamlElementTypes.TYPE_PARAMETERIZED_BINDING);
        }
    }

    private static void parseTypeInformation(@NotNull final PsiBuilder builder) {
        if (ignore(builder, OCamlTokenTypes.EQ) && !tryParseTypeExpression(builder)) {
            if (builder.getTokenType() == OCamlTokenTypes.LBRACE) {
                parseRecordDeclaration(builder);
            }
            else {
                parseVariantTypeDeclaration(builder);
            }
        }

        while (ignore(builder, OCamlTokenTypes.CONSTRAINT_KEYWORD)) {
            parseTypeConstraint(builder);
        }
    }

    private static void parseTypeConstraint(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeConstraintMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.QUOTE, Strings.QUOTE_EXPECTED);

        NameParsing.parseAnyIdentifier(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parseTypeExpression(builder);

        typeConstraintMarker.done(OCamlElementTypes.TYPE_DEFINITION_CONSTRAINT);
    }

    private static void parseVariantTypeDeclaration(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker variantTypeDeclarationMarker = builder.mark();

        do {
            parseTypeConstructorDefinition(builder);
        } while (ignore(builder, OCamlTokenTypes.VBAR));

        variantTypeDeclarationMarker.done(OCamlElementTypes.VARIANT_TYPE_DEFINITION);
    }

    private static void parseTypeConstructorDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeConstructorDefinitionMarker = builder.mark();

        NameParsing.parseConstructorName(builder, NameParsing.NameType.DEFINITION);

        if (ignore(builder, OCamlTokenTypes.OF_KEYWORD)) {
            parseTypeExpression(builder);
        }

        typeConstructorDefinitionMarker.done(OCamlElementTypes.CONSTRUCTOR_DEFINITION);
    }

    private static void parseRecordDeclaration(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker recordTypeDeclarationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LBRACE, Strings.LBRACE_EXPECTED);

        do {
            parseRecordFieldDeclaration(builder);
        } while (ignore(builder, OCamlTokenTypes.SEMICOLON));

        checkMatches(builder, OCamlTokenTypes.RBRACE, Strings.RBRACE_EXPECTED);

        recordTypeDeclarationMarker.done(OCamlElementTypes.RECORD_TYPE_DEFINITION);
    }

    private static void parseRecordFieldDeclaration(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker recordFieldDeclarationMarker = builder.mark();

        ignore(builder, OCamlTokenTypes.MUTABLE_KEYWORD);

        NameParsing.parseFieldName(builder);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        parsePolyTypeExpression(builder);

        recordFieldDeclarationMarker.done(OCamlElementTypes.RECORD_FIELD_DEFINITION);
    }

    private static void parseTypeParameters(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LPAR)) {
            do {
                parseTypeParameter(builder, true, true);
            } while (ignore(builder, OCamlTokenTypes.COMMA));

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            marker.done(OCamlElementTypes.PARENTHESES_TYPE_PARAMETERS);
        }
        else {
            marker.drop();

            parseTypeParameter(builder, true, true);
        }
    }

    private static void parseTagSpecFull(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker tagSpecFullMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.ACCENT)) {
            NameParsing.parseTagName(builder);

            if (ignore(builder, OCamlTokenTypes.OF_KEYWORD)) {
                do {
                    parseTypeExpression(builder);
                } while (ignore(builder, OCamlTokenTypes.AMP));
            }

            tagSpecFullMarker.done(OCamlElementTypes.TAG_SPEC_FULL);
        }
        else {
            tagSpecFullMarker.drop();            
            parseTypeExpression(builder);
        }
    }

    private static void parseTagSpec(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker tagSpecMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.ACCENT)) {
            NameParsing.parseTagName(builder);

            if (ignore(builder, OCamlTokenTypes.OF_KEYWORD)) {
                parseTypeExpression(builder);
            }

            tagSpecMarker.done(OCamlElementTypes.TAG_SPEC);
        }
        else {
            tagSpecMarker.drop();
            parseTypeExpression(builder);
        }
    }

    private static boolean tryParseAsTypeExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker asTypeExpressionMarker = builder.mark();

        if (!tryParseFunctionTypeExpression(builder)) {
            asTypeExpressionMarker.drop();
            return false;
        }

        if (ignore(builder, OCamlTokenTypes.AS_KEYWORD)) {
            parseTypeParameter(builder, false, true);
            asTypeExpressionMarker.done(OCamlElementTypes.AS_TYPE_EXPRESSION);
        }
        else {
            asTypeExpressionMarker.drop();
        }

        return true;
    }

    private static boolean tryParseFunctionTypeExpression(@NotNull final PsiBuilder builder) {
        final MultiMarker functionTypeExpressionMarker = new MultiMarker(builder);
        functionTypeExpressionMarker.mark();

        boolean lastItemHasLabel = NameParsing.tryParseQuestAndLabel(builder);

        if (!tryParseTupleTypeExpression(builder)) {
            if (lastItemHasLabel) {
                builder.error(Strings.TYPE_EXPRESSION_EXPECTED);
            }
            else {
                functionTypeExpressionMarker.rollbackToTheFirstMark();
                return false;
            }
        }

        while (ignore(builder, OCamlTokenTypes.MINUS_GT)) {
            functionTypeExpressionMarker.mark();

            lastItemHasLabel = NameParsing.tryParseQuestAndLabel(builder);

            if (!tryParseTupleTypeExpression(builder)) {
                builder.error(Strings.TYPE_EXPRESSION_EXPECTED);
            }
        }

        if (lastItemHasLabel) {
            builder.error(Strings.MINUS_GT_EXPECTED);
        }
        else {
            functionTypeExpressionMarker.dropLast();
        }

        functionTypeExpressionMarker.done(OCamlElementTypes.FUNCTION_TYPE_EXPRESSION);

        return true;
    }

    private static boolean tryParseTupleTypeExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker tupleTypeExpressionMarker = builder.mark();

        if (!tryParseTypeConstructorOrSuperClassTypeExpression(builder)) {
            tupleTypeExpressionMarker.drop();
            return false;
        }

        boolean multParsed = false;

        while (ignore(builder, OCamlTokenTypes.MULT)) {
            multParsed = true;

            if (!tryParseTypeConstructorOrSuperClassTypeExpression(builder)) {
                builder.error(Strings.TYPE_EXPRESSION_EXPECTED);
            }
        }

        if (multParsed) {
            tupleTypeExpressionMarker.done(OCamlElementTypes.TUPLE_TYPE_EXPRESSION);
        }
        else {
            tupleTypeExpressionMarker.drop();
        }

        return true;
    }

    private static boolean tryParseTypeConstructorOrSuperClassTypeExpression(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker typeExpressionMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LPAR)) {
            if (!tryParseTypeExpression(builder)) {
                typeExpressionMarker.rollbackTo();
                return false;
            }

            int expressionCount = 1;

            while (ignore(builder, OCamlTokenTypes.COMMA)) {
                expressionCount++;

                if (!tryParseTypeExpression(builder)) {
                    builder.error(Strings.TYPE_EXPRESSION_EXPECTED);
                }
            }

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            boolean isTypeExpression = false;
            if (expressionCount == 1) {
                final PsiBuilder.Marker marker = builder.mark();
                if (!ignore(builder, OCamlTokenTypes.HASH) && !NameParsing.tryParseTypeConstructorPath(builder)) {
                    isTypeExpression = true;
                }
                marker.rollbackTo();
            }

            typeExpressionMarker.done(isTypeExpression ? OCamlElementTypes.PARENTHESES_TYPE_EXPRESSION : OCamlElementTypes.PARENTHESES);
            typeExpressionMarker = typeExpressionMarker.precede();

            if (ignore(builder, OCamlTokenTypes.HASH)) {
                NameParsing.parseClassPath(builder);
                typeExpressionMarker.done(OCamlElementTypes.SUPER_CLASS_TYPE_EXPRESSION);
            }
            else if (NameParsing.tryParseTypeConstructorPath(builder)) {
                typeExpressionMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION);
            }
            else if (expressionCount > 1) {
                builder.error(Strings.TYPE_CONSTRUCTOR_OR_HASH_EXPECTED);
                typeExpressionMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION);
            }
            else {
                typeExpressionMarker.drop();
            }
        }
        else if (tryParseSimpleTypeExpression(builder)) {
            if (ignore(builder, OCamlTokenTypes.HASH)) {
                NameParsing.parseClassPath(builder);
                typeExpressionMarker.done(OCamlElementTypes.SUPER_CLASS_TYPE_EXPRESSION);
            }
            else if (NameParsing.tryParseTypeConstructorPath(builder)) {
                typeExpressionMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION);
            }
            else {
                typeExpressionMarker.drop();
            }
        }
        else {
            typeExpressionMarker.drop();
            return false;
        }

        return true;
    }

    private static boolean tryParseSimpleTypeExpression(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.QUOTE) {
            parseTypeParameter(builder, false, false);
        } else if (!tryParseUnderscore(builder) &&
                   !tryParseObjectInterfaceTypeExpression(builder) &&
                   !tryParseVariantTypeExpression(builder)) {
            final PsiBuilder.Marker typeExpressionMarker = builder.mark();

            if (ignore(builder, OCamlTokenTypes.HASH)) {
                NameParsing.parseClassPath(builder);
                typeExpressionMarker.done(OCamlElementTypes.SUPER_CLASS_TYPE_EXPRESSION);
            }
            else if (NameParsing.tryParseTypeConstructorPath(builder)) {
                typeExpressionMarker.drop();
            }
            else {
                typeExpressionMarker.drop();
                return false;
            }
        }

        return true;
    }

    private static boolean tryParseUnderscore(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.UNDERSCORE)) {
            marker.done(OCamlElementTypes.UNDERSCORE_TYPE_EXPRESSION);
            return true;
        }

        marker.drop();
        return false;
    }

    private static boolean tryParseVariantTypeExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker variantTypeExpressionMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LBRACKET)) {
            ignore(builder, OCamlTokenTypes.VBAR);

            parseTagSpec(builder);

            while (ignore(builder, OCamlTokenTypes.VBAR)) {
                parseTagSpec(builder);
            }
        }
        else if (ignore(builder, OCamlTokenTypes.LBRACKET_GT)) { //todo ignore(builder, OCamlTokenTypes.VBAR); ???
            parseTagSpec(builder);

            while (ignore(builder, OCamlTokenTypes.VBAR)) {
                parseTagSpec(builder);
            }
        }
        else if (ignore(builder, OCamlTokenTypes.LBRACKET_LT)) {
            ignore(builder, OCamlTokenTypes.VBAR);

            parseTagSpecFull(builder);

            while (ignore(builder, OCamlTokenTypes.VBAR)) {
                parseTagSpecFull(builder);
            }

            if (ignore(builder, OCamlTokenTypes.GT)) {
                checkMatches(builder, OCamlTokenTypes.ACCENT, Strings.ACCENT_EXPECTED);
                NameParsing.parseTagName(builder);

                while (ignore(builder, OCamlTokenTypes.ACCENT)) {
                    NameParsing.parseTagName(builder);
                }
            }
        }
        else {
            variantTypeExpressionMarker.drop();
            return false;
        }

        checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);

        variantTypeExpressionMarker.done(OCamlElementTypes.VARIANT_TYPE_TYPE_EXPRESSION);

        return true;
    }

    private static boolean tryParseObjectInterfaceTypeExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker recordTypeExpressionMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LT)) {
            recordTypeExpressionMarker.drop();
            return false;
        }

        if (!ignore(builder, OCamlTokenTypes.DOT_DOT) && builder.getTokenType() != OCamlTokenTypes.GT) {
            parseMethodType(builder);

            while (ignore(builder, OCamlTokenTypes.SEMICOLON)) {
                if (ignore(builder, OCamlTokenTypes.DOT_DOT)) {
                    break;
                } else {
                    parseMethodType(builder);
                }
            }
        }

        checkMatches(builder, OCamlTokenTypes.GT, Strings.GT_EXPECTED);

        recordTypeExpressionMarker.done(OCamlElementTypes.OBJECT_INTERFACE_TYPE_EXPRESSION);

        return true;
    }

    private static void parseMethodType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker methodTypeMarker = builder.mark();

        NameParsing.parseMethodName(builder);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        parsePolyTypeExpression(builder);

        methodTypeMarker.done(OCamlElementTypes.METHOD_TYPE);
    }

    private static boolean tryParsePolyTypeExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker polyTypeExpressionMarker = builder.mark();

        int parameterCount = doParsePolyTypeExpressionParameters(builder, true);

        if (parameterCount == 0) {
            polyTypeExpressionMarker.drop();
            return tryParseTypeExpression(builder);
        }
        else if (parameterCount == 1) {
            if (ignore(builder, OCamlTokenTypes.DOT)) {
                parseTypeExpression(builder);
            }
            else {
                polyTypeExpressionMarker.rollbackTo();
                doParsePolyTypeExpressionParameters(builder, false);
                return true;
            }
        }
        else {
            checkMatches(builder, OCamlTokenTypes.DOT, Strings.DOT_EXPECTED);
            parseTypeExpression(builder);
        }

        polyTypeExpressionMarker.done(OCamlElementTypes.POLY_TYPE_EXPRESSION);

        return true;
    }

    private static int doParsePolyTypeExpressionParameters(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        int parameterCount = 0;

        while (builder.getTokenType() == OCamlTokenTypes.QUOTE) {
            parseTypeParameter(builder, false, isDefinition);
            parameterCount++;
        }

        return parameterCount;
    }

    public static void parseTupleTypeExpression(@NotNull final PsiBuilder builder) {
        if (!tryParseTupleTypeExpression(builder)) {
            builder.error(Strings.TYPE_EXPRESSION_EXPECTED);
        }
    }
}
