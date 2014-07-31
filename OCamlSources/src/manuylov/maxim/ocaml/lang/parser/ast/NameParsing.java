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
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 12.02.2009
 */
class NameParsing extends Parsing {
    public static void parseModulePath(@NotNull final PsiBuilder builder) {
        if (!tryParseModulePath(builder)) {
            builder.error(Strings.MODULE_PATH_EXPECTED);
        }
    }

    public static void parseModuleName(@NotNull final PsiBuilder builder) {
        if (!tryParseModuleName(builder)) {
            builder.error(Strings.MODULE_NAME_EXPECTED);
        }
    }

    public static void parseConstructorPath(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        if (!tryParseConstructorPath(builder, type)) {
            builder.error(Strings.CONSTRUCTOR_PATH_EXPECTED);
        }
    }

    public static void parseAnyIdentifier(@NotNull final PsiBuilder builder) {
        if (!tryParseAnyIdentifier(builder)) {
            builder.error(Strings.IDENTIFIER_EXPECTED);
        }
    }

    public static void parseTypeParameterName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        parseAnyIdentifier(builder);

        marker.done(OCamlElementTypes.TYPE_PARAMETER_NAME);
    }

    public static void parseForExpressionIndexVariableName(@NotNull final PsiBuilder builder) {
        if (!tryParseForExpressionIndexVariableName(builder)) {
            builder.error(Strings.INDEX_VARIABLE_NAME_EXPECTED);
        }
    }

    public static boolean tryParseForExpressionIndexVariableName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            marker.drop();
            return false;
        }

        marker.done(OCamlElementTypes.FOR_EXPRESSION_INDEX_VARIABLE_NAME);

        return true;
    }

    public static void parseClassPath(@NotNull final PsiBuilder builder) {
        if (!tryParseClassPath(builder)) {
            builder.error(Strings.CLASS_PATH_EXPECTED);
        }
    }

    public static void parseMethodName(@NotNull final PsiBuilder builder) {
        if (!tryParseMethodName(builder)) {
            builder.error(Strings.METHOD_NAME_EXPECTED);
        }
    }

    public static void parseLabelNameProbablyWithColon(@NotNull final PsiBuilder builder, @NotNull final Runnable afterColonProcessing, final boolean isDefinition) {
        doParseLabelNameWithColon(builder, afterColonProcessing, false, isDefinition);
    }

    private static void doParseLabelNameWithColon(final PsiBuilder builder, final Runnable afterColonProcessing, final boolean colonIsMandatory, final boolean isDefinition) {
        final PsiBuilder.Marker labelNameMarker = builder.mark();

        int offset = builder.getCurrentOffset();

        if (builder.getTokenType() == OCamlTokenTypes.LCFC_IDENTIFIER) {
            final String tokenText = builder.getTokenText();
            if (tokenText != null) {
                offset += tokenText.length();
                builder.advanceLexer();
            }
        }
        else {
            builder.error(Strings.LABEL_NAME_EXPECTED);
        }

        labelNameMarker.done(OCamlElementTypes.LABEL_NAME);
        if (isDefinition) {
            labelNameMarker.precede().done(OCamlElementTypes.LABEL_DEFINITION);
        }

        if (offset == builder.getCurrentOffset() && ignore(builder, OCamlTokenTypes.COLON)) {
            afterColonProcessing.run();
        }
        else if (colonIsMandatory) {
            builder.error(Strings.COLON_EXPECTED);
        }
    }

    public static void parseModuleTypeName(@NotNull final PsiBuilder builder) {
        if (!tryParseModuleTypeName(builder)) {
            builder.error(Strings.MODULE_TYPE_NAME_EXPECTED);
        }
    }

    public static void parseExtendedModulePath(@NotNull final PsiBuilder builder) {
        if (!tryParseExtendedModulePath(builder)) {
            builder.error(Strings.EXTENDED_MODULE_PATH_EXPECTED);
        }
    }

    private static boolean tryParseExtendedModulePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker extendedModulePathMarker = builder.mark();

        final boolean extendedModuleNameParsed = doTryParseExtendedModuleNames(builder);

        if (extendedModuleNameParsed) {
            if (!tryParseExtendedModuleName(builder, false)) {
                builder.error(Strings.EXTENDED_MODULE_NAME_EXPECTED);
            }

            extendedModulePathMarker.done(OCamlElementTypes.EXTENDED_MODULE_PATH);
        }
        else {
            extendedModulePathMarker.drop();

            return tryParseExtendedModuleName(builder, false);
        }

        return true;
    }

    public static void parseTypeConstructorPath(@NotNull final PsiBuilder builder) {
        if (!tryParseTypeConstructorPath(builder)) {
            builder.error(Strings.TYPE_CONSTRUCTOR_PATH_EXPECTED);
        }
    }

    public static void parseModuleTypePath(@NotNull final PsiBuilder builder) {
        if (!tryParseModuleTypePath(builder)) {
            builder.error(Strings.MODULE_TYPE_PATH_EXPECTED);
        }
    }

    public static boolean tryParseModuleTypePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypePathMarker = builder.mark();

        final boolean extendedModuleNameParsed = doTryParseExtendedModuleNames(builder);

        if (extendedModuleNameParsed) {
            if (!tryParseModuleTypeName(builder)) {
                builder.error(Strings.MODULE_TYPE_NAME_EXPECTED);
            }

            moduleTypePathMarker.done(OCamlElementTypes.MODULE_TYPE_PATH);
        }
        else {
            moduleTypePathMarker.drop();

            return tryParseModuleTypeName(builder);
        }

        return true;
    }

    public static void parseTypeConstructorName(@NotNull final PsiBuilder builder) {
        if (!tryParseTypeConstructorName(builder)) {
            builder.error(Strings.TYPE_CONSTRUCTOR_NAME_EXPECTED);
        }
    }

    public static void parseTagName(@NotNull final PsiBuilder builder) {
        if (!tryParseTagName(builder)) {
            builder.error(Strings.TAG_NAME_EXPECTED);
        }
    }

    public static void parseClassName(@NotNull final PsiBuilder builder) {
        if (!tryParseClassName(builder)) {
            builder.error(Strings.CLASS_NAME_EXPECTED);
        }
    }

    public static void parseConstructorName(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        if (!tryParseConstructorName(builder, type)) {
            builder.error(Strings.CONSTRUCTOR_NAME_EXPECTED);
        }
    }

    public static void parseInstVarName(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        if (!tryParseInstVarName(builder, type)) {
            builder.error(Strings.INSTANCE_VARIABLE_NAME_EXPECTED);
        }
    }

    public static void parseValueName(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        if (!tryParseValueName(builder, type)) {
            builder.error(Strings.VALUE_NAME_EXPECTED);
        }
    }

    public static void parseFieldName(@NotNull final PsiBuilder builder) {
        if (!tryParseFieldName(builder)) {
            builder.error(Strings.FIELD_NAME_EXPECTED);
        }
    }

    public static void parseFieldPath(@NotNull final PsiBuilder builder) {
        if (!tryParseFieldPath(builder)) {
            builder.error(Strings.FIELD_PATH_EXPECTED);
        }
    }

    public static boolean tryParseValueName(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        final PsiBuilder.Marker valueNameMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LPAR)) {
            if (!tryParseOperatorName(builder) || !ignore(builder, OCamlTokenTypes.RPAR)) {
                valueNameMarker.rollbackTo();
                return false;
            }
        }
        else {
            if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
                valueNameMarker.drop();
                return false;
            }
        }

        valueNameMarker.done(type == NameType.PATTERN ? OCamlElementTypes.VALUE_NAME_PATTERN : OCamlElementTypes.VALUE_NAME);

        return true;
    }

    private static boolean tryParseModuleName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.UCFC_IDENTIFIER)) {
            moduleNameMarker.drop();
            return false;
        }

        moduleNameMarker.done(OCamlElementTypes.MODULE_NAME);

        return true;
    }

    public static boolean tryParseConstructorPath(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        return tryParseModulePathWithLastPart(builder, getConstructorPathType(type), type);
    }

    @NotNull
    private static IElementType getConstructorPathType(@NotNull final NameType type) {
        switch (type) {
            case EXPRESSION: {
                return OCamlElementTypes.CONSTRUCTOR_PATH_EXPRESSION;
            }

            case PATTERN: {
                return OCamlElementTypes.CONSTRUCTOR_PATH_PATTERN;
            }

            case NONE: {
                return OCamlElementTypes.CONSTRUCTOR_PATH;
            }
        }

        throw new IllegalArgumentException();
    }

    private static boolean tryParseConstructorName(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        final PsiBuilder.Marker constructorNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.UCFC_IDENTIFIER)) {
            constructorNameMarker.drop();
            return false;
        }

        switch (type) {
            case DEFINITION: {
                constructorNameMarker.done(OCamlElementTypes.CONSTRUCTOR_NAME_DEFINITION);
                break;
            }

            case EXPRESSION: {
                constructorNameMarker.done(OCamlElementTypes.CONSTRUCTOR_NAME_EXPRESSION);
                break;
            }

            case PATTERN: {
                constructorNameMarker.done(OCamlElementTypes.CONSTRUCTOR_NAME_PATTERN);
                break;
            }

            case NONE: {
                constructorNameMarker.done(OCamlElementTypes.CONSTRUCTOR_NAME);
                break;
            }
        }

        return true;
    }

    private static boolean tryParseTypeConstructorName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeConstructorNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            typeConstructorNameMarker.drop();
            return false;
        }

        typeConstructorNameMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_NAME);

        return true;
    }

    private static boolean tryParseAnyIdentifier(@NotNull final PsiBuilder builder) {
        return ignore(builder, OCamlTokenTypes.IDENTIFIERS);
    }

    private static boolean tryParseFieldName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker fieldNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            fieldNameMarker.drop();
            return false;
        }

        fieldNameMarker.done(OCamlElementTypes.FIELD_NAME);

        return true;
    }

    public static boolean tryParseClassPath(@NotNull final PsiBuilder builder) {
        return tryParseModulePathWithLastPart(builder, OCamlElementTypes.CLASS_PATH, NameType.NONE);
    }

    public static boolean tryParseModulePath(@NotNull final PsiBuilder builder) {
        return tryParseModulePathWithLastPart(builder, OCamlElementTypes.MODULE_PATH, NameType.NONE);
    }

    private static boolean tryParseClassName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            classNameMarker.drop();
            return false;
        }

        classNameMarker.done(OCamlElementTypes.CLASS_NAME);

        return true;
    }

    private static boolean tryParseMethodName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker methodNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            methodNameMarker.drop();
            return false;
        }

        methodNameMarker.done(OCamlElementTypes.METHOD_NAME);

        return true;
    }

    private static boolean tryParseInstVarName(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        final PsiBuilder.Marker instVarNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            instVarNameMarker.drop();
            return false;
        }

        instVarNameMarker.done(type == NameType.DEFINITION ? OCamlElementTypes.INST_VAR_NAME_DEFINITION : OCamlElementTypes.INST_VAR_NAME);

        return true;
    }

    private static boolean tryParseModuleTypeName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypeNameMarker = builder.mark();

        if (!tryParseAnyIdentifier(builder)) {
            moduleTypeNameMarker.drop();
            return false;
        }

        moduleTypeNameMarker.done(OCamlElementTypes.MODULE_TYPE_NAME);

        return true;
    }

    public static boolean tryParseTypeConstructorPath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeConstructorPathMarker = builder.mark();

        final boolean extendedModuleNameParsed = doTryParseExtendedModuleNames(builder);

        if (extendedModuleNameParsed) {
            if (!tryParseTypeConstructorName(builder)) {
                builder.error(Strings.TYPE_CONSTRUCTOR_NAME_EXPECTED);
            }

            typeConstructorPathMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_PATH);
        }
        else {
            typeConstructorPathMarker.drop();

            return tryParseTypeConstructorName(builder);
        }

        return true;
    }

    public static boolean tryParseQuestAndLabel(@NotNull final PsiBuilder builder) {
        if (ignore(builder, OCamlTokenTypes.QUEST)) {
            doParseLabelNameWithMandatoryColon(builder, true);
            return true;
        }
        else if (getNextTokenType(builder) == OCamlTokenTypes.COLON) {
            doParseLabelNameWithMandatoryColon(builder, true);
            return true;
        }
        return false;
    }

    private static boolean tryParseTagName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker tagNameMarker = builder.mark();

        if (!tryParseAnyIdentifier(builder)) {
            tagNameMarker.drop();
            return false;
        }

        tagNameMarker.done(OCamlElementTypes.TAG_NAME);

        return true;
    }

    public static boolean tryParseValuePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker pathMarker = builder.mark();

        boolean dotParsed = false;

        while (tryParseModuleName(builder)) {
            checkMatches(builder, OCamlTokenTypes.DOT, Strings.DOT_EXPECTED);
            dotParsed = true;
        }

        if (!tryParseValueName(builder, NameType.NONE)) {
            pathMarker.rollbackTo();
            return false;
        }

        if (dotParsed) {
            pathMarker.done(OCamlElementTypes.VALUE_PATH);
        }
        else {
            pathMarker.drop();
        }

        return true;
    }

    private static boolean tryParseFieldPath(@NotNull final PsiBuilder builder) {
        return tryParseModulePathWithLastPart(builder, OCamlElementTypes.FIELD_PATH, NameType.NONE);
    }

    public static boolean tryParseConstant(@NotNull final PsiBuilder builder, @NotNull final NameType type) {
        if (tryParseConstructorPath(builder, type)) {
            return true;
        }

        final PsiBuilder.Marker constantMarker = builder.mark();

        if (!tryParseCharLiteral(builder) &&
            !ignore(builder, TokenSet.create(OCamlTokenTypes.INTEGER_LITERAL, OCamlTokenTypes.FLOAT_LITERAL,
                OCamlTokenTypes.STRING_LITERAL, OCamlTokenTypes.FALSE_KEYWORD, OCamlTokenTypes.TRUE_KEYWORD))) {
            constantMarker.drop();
            return false;
        }

        constantMarker.done(type == NameType.PATTERN ? OCamlElementTypes.CONSTANT_PATTERN : OCamlElementTypes.CONSTANT_EXPRESSION);

        return true;
    }

    public static void parseCharLiteral(@NotNull final PsiBuilder builder) {
        if (!tryParseCharLiteral(builder)) {
            builder.error(Strings.CHAR_LITERAL_EXPECTED);
        }
    }

    public static boolean tryParseCharLiteral(@NotNull final PsiBuilder builder) {
        if (ignore(builder, OCamlTokenTypes.CHAR_LITERAL)) return true;

        final PsiBuilder.Marker errorMarker = builder.mark();
        if (ignore(builder, OCamlTokenTypes.EMPTY_CHAR_LITERAL)) {
            errorMarker.error(Strings.ILLEGAL_CHAR_LITERAL);
            return true;
        }
        else {
            errorMarker.drop();
            return false;
        }
    }

    private static void doParseLabelNameWithMandatoryColon(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        doParseLabelNameWithColon(builder, new Runnable() {
            public void run() {
                //do nothing
            }
        }, true, isDefinition);
    }

    @NotNull private static final TokenSet ourConstructorPathTypes = TokenSet.create(
        OCamlElementTypes.CONSTRUCTOR_PATH,
        OCamlElementTypes.CONSTRUCTOR_PATH_EXPRESSION,
        OCamlElementTypes.CONSTRUCTOR_PATH_PATTERN
    );

    private static boolean tryParseModulePathWithLastPart(@NotNull final PsiBuilder builder, @NotNull final IElementType pathType, @NotNull final NameType type) {
        final PsiBuilder.Marker pathMarker = builder.mark();

        boolean dotParsed = false;

        while (getNextTokenType(builder) == OCamlTokenTypes.DOT) {
            if (!tryParseModuleName(builder)) {
                pathMarker.rollbackTo();
                return false;
            }
            checkMatches(builder, OCamlTokenTypes.DOT, Strings.DOT_EXPECTED);
            dotParsed = true;
        }

        final boolean lastPartParseResult;

        if (pathType == OCamlElementTypes.CLASS_PATH) {
            lastPartParseResult = tryParseClassName(builder);
        }
        else if (pathType == OCamlElementTypes.FIELD_PATH) {
            lastPartParseResult = tryParseFieldName(builder);
        }
        else if (pathType == OCamlElementTypes.MODULE_PATH) {
            lastPartParseResult = tryParseModuleName(builder);
        }
        else {
            //noinspection SimplifiableIfStatement
            if (ourConstructorPathTypes.contains(pathType)) {
                lastPartParseResult = tryParseConstructorName(builder, type);
            }
            else {
                lastPartParseResult = false;
            }
        }

        if (!lastPartParseResult) {
            pathMarker.rollbackTo();
            return false;
        }

        if (dotParsed) {
            pathMarker.done(pathType);
        }
        else {
            pathMarker.drop();
        }

        return true;
    }

    private static boolean tryParseOperatorName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker operatorNameMarker = builder.mark();

        if (!ignore(builder, TokenSet.orSet(OCamlTokenTypes.PREFIX_OPERATORS, OCamlTokenTypes.INFIX_OPERATORS))) {
            operatorNameMarker.drop();
            return false;
        }

        operatorNameMarker.done(OCamlElementTypes.OPERATOR_NAME);

        return true;
    }

    private static boolean tryParseExtendedModuleName(@NotNull final PsiBuilder builder, final boolean parseDot) {
        final PsiBuilder.Marker extendedModuleNameMarker = builder.mark();

        if (!tryParseModuleName(builder)) {
            extendedModuleNameMarker.drop();
            return false;
        }

        boolean extendedModulePathParsed = false;

        PsiBuilder.Marker marker = builder.mark();

        while (ignore(builder, OCamlTokenTypes.LPAR)) {
            parseExtendedModulePath(builder);

            extendedModulePathParsed = true;

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            marker.done(OCamlElementTypes.PARENTHESES);
            marker = builder.mark();
        }

        marker.drop();

        if (parseDot) {
            if (builder.getTokenType() == OCamlTokenTypes.DOT) {
                if (extendedModulePathParsed) {
                    extendedModuleNameMarker.done(OCamlElementTypes.EXTENDED_MODULE_NAME);
                }
                else {
                    extendedModuleNameMarker.drop();
                }
                builder.advanceLexer();
            }
            else {
                extendedModuleNameMarker.rollbackTo();
                return false;
            }
        }
        else {
            if (extendedModulePathParsed) {
                extendedModuleNameMarker.done(OCamlElementTypes.EXTENDED_MODULE_NAME);
            }
            else {
                extendedModuleNameMarker.drop();
            }
        }

        return true;
    }

    private static boolean doTryParseExtendedModuleNames(@NotNull final PsiBuilder builder) {
        final boolean[] extendedModuleNameParsed = { false };
        final boolean[] shouldBreak = { false };

        final Runnable parsing = new Runnable() {
            public void run() {
                if (tryParseExtendedModuleName(builder, true)) {
                    extendedModuleNameParsed[0] = true;
                }
                else {
                    shouldBreak[0] = true;
                }
            }
        };

        while (!builder.eof() && !shouldBreak[0]) {
            advanceLexerIfNothingWasParsed(builder, shouldBreak, parsing);
        }

        return extendedModuleNameParsed[0];
    }

    public static enum NameType {
        DEFINITION,
        PATTERN,
        EXPRESSION,
        NONE
    }
}
