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
 *         Date: 11.02.2009
 */
class ClassParsing extends Parsing {
    public static void parseClassOrClassTypeDefinition(@NotNull final PsiBuilder builder) {
        if (getNextTokenType(builder) == OCamlTokenTypes.TYPE_KEYWORD) {
            parseClassTypeDefinition(builder);
        }
        else {
            parseClassDefinition(builder);
        }
    }

    public static void parseClassExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classExpressionMarker = builder.mark();

        if (builder.getTokenType() == OCamlTokenTypes.OBJECT_KEYWORD) {
            parseObjectClassExpression(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.LET_KEYWORD) {
            LetParsing.parseLetExpression(builder, LetParsing.ExpressionType.ClassExpression);
        } else if (builder.getTokenType() == OCamlTokenTypes.FUN_KEYWORD) {
            parseFunctionClassExpression(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.LPAR) {
            doParseClassExpressionInParentheses(builder);
        } else {
            parseClassPathClassExpression(builder);
        }

        final boolean[] shouldBreak = { false };
        final boolean[] hasArguments = { false };

        final Runnable parsing = new Runnable() {
            public void run() {
                if (ExpressionParsing.tryParseArgument(builder)) {
                    hasArguments[0] = true;
                }
                else {
                    shouldBreak[0] = true;
                }
            }
        };

        while (!builder.eof() && !shouldBreak[0]) {
            advanceLexerIfNothingWasParsed(builder, shouldBreak, parsing);
        }

        if (hasArguments[0]) {
            classExpressionMarker.done(OCamlElementTypes.FUNCTION_APPLICATION_CLASS_EXPRESSION);
        }
        else {
            classExpressionMarker.drop();
        }
    }

    public static void doParseClassBody(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LPAR)) {
            PatternParsing.parsePattern(builder);

            if (ignore(builder, OCamlTokenTypes.COLON)) {
                TypeParsing.parseTypeExpression(builder);
            }

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            marker.done(OCamlElementTypes.OBJECT_SELF_DEFINITION);
        }
        else {
            marker.drop();
        }

        final Runnable parsing = new Runnable() {
            public void run() {
                parseClassField(builder);
            }
        };

        while (!builder.eof() && builder.getTokenType() != OCamlTokenTypes.END_KEYWORD) {
            advanceLexerIfNothingWasParsed(builder, parsing);
        }
    }

    public static void parseClassSpecificationOrClassTypeDefinition(@NotNull final PsiBuilder builder) {
        if (getNextTokenType(builder) == OCamlTokenTypes.TYPE_KEYWORD) {
            parseClassTypeDefinition(builder);
        }
        else {
            parseClassSpecification(builder);
        }
    }

    private static void parseClassTypeDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classTypeDefinitionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.CLASS_KEYWORD, Strings.CLASS_EXPECTED);
        checkMatches(builder, OCamlTokenTypes.TYPE_KEYWORD, Strings.TYPE_EXPECTED);

        do {
            parseClassTypeBinding(builder);
        } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));

        classTypeDefinitionMarker.done(OCamlElementTypes.CLASS_TYPE_DEFINITION);
    }

    private static void parseClassTypeBinding(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classTypeBindingMarker = builder.mark();

        doParseBindingStart(builder, true);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        tryParseClassBodyType(builder);

        classTypeBindingMarker.done(OCamlElementTypes.CLASS_TYPE_BINDING);
    }

    private static void parseClassDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classDefinitionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.CLASS_KEYWORD, Strings.CLASS_EXPECTED);

        do {
            parseClassBinding(builder);
        } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));

        classDefinitionMarker.done(OCamlElementTypes.CLASS_DEFINITION);
    }

    private static void parseClassBinding(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classBindingMarker = builder.mark();

        doParseBindingStart(builder, false);

        final Runnable parsing = new Runnable() {
            public void run() {
                ExpressionParsing.parseParameter(builder);
            }
        };

        while (!builder.eof() && builder.getTokenType() != OCamlTokenTypes.COLON && builder.getTokenType() != OCamlTokenTypes.EQ) {
            advanceLexerIfNothingWasParsed(builder, parsing);
        }

        if (ignore(builder, OCamlTokenTypes.COLON)) {
            parseClassType(builder);
        }

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parseClassExpression(builder);

        classBindingMarker.done(OCamlElementTypes.CLASS_BINDING);
    }

    private static void parseClassType(@NotNull final PsiBuilder builder) {
        if (!tryParseClassType(builder)) {
            builder.error(Strings.CLASS_TYPE_EXPECTED);
        }
    }

    private static void parseClassPathClassExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classPathClassExpressionMarker = builder.mark();

        boolean lbracketParsed = false;

        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LBRACKET)) {
            lbracketParsed = true;

            do {
                TypeParsing.parseTypeExpression(builder);
            } while (ignore(builder, OCamlTokenTypes.COMMA));

            checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);

            marker.done(OCamlElementTypes.BRACKETS);
        }
        else {
            marker.drop();
        }

        if (!NameParsing.tryParseClassPath(builder)) {
            builder.error(lbracketParsed ? Strings.CLASS_PATH_EXPECTED : Strings.CLASS_EXPRESSION_EXPECTED);    
        }

        if (lbracketParsed) {
            classPathClassExpressionMarker.done(OCamlElementTypes.CLASS_PATH_APPLICATION);
        }
        else {
            classPathClassExpressionMarker.drop();
        }
    }

    private static void parseFunctionClassExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker functionClassExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.FUN_KEYWORD, Strings.FUN_KEYWORD_EXPECTED);

        ExpressionParsing.parseParameter(builder);

        final Runnable parsing = new Runnable() {
            public void run() {
                ExpressionParsing.parseParameter(builder);
            }
        };

        while (!builder.eof() && builder.getTokenType() != OCamlTokenTypes.MINUS_GT) {
            advanceLexerIfNothingWasParsed(builder, parsing);
        }

        checkMatches(builder, OCamlTokenTypes.MINUS_GT, Strings.MINUS_GT_EXPECTED);

        parseClassExpression(builder);

        functionClassExpressionMarker.done(OCamlElementTypes.FUNCTION_CLASS_EXPRESSION);
    }

    private static void parseObjectClassExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker objectClassExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.OBJECT_KEYWORD, Strings.OBJECT_KEYWORD_EXPECTED);

        doParseClassBody(builder);

        checkMatches(builder, OCamlTokenTypes.END_KEYWORD, Strings.END_KEYWORD_EXPECTED);

        objectClassExpressionMarker.done(OCamlElementTypes.OBJECT_END_CLASS_EXPRESSION);
    }

    private static void parseClassField(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.INHERIT_KEYWORD) {
            parseInheritClassField(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.VAL_KEYWORD) {
            parseValueClassField(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.METHOD_KEYWORD) {
            parseMethodClassField(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.CONSTRAINT_KEYWORD) {
            parseConstraintClassField(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.INITIALIZER_KEYWORD) {
            parseInitializerClassField(builder);
        } else {
            builder.error(Strings.INHERIT_OR_VAL_OR_METHOD_OR_CONSTRAINT_OR_INITIALIZER_EXPECTED);
        }
    }

    private static void parseMethodClassField(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker methodClassFieldMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.METHOD_KEYWORD, Strings.METHOD_KEYWORD_EXPECTED);

        ignore(builder, OCamlTokenTypes.PRIVATE_KEYWORD);
        final boolean virtual = ignore(builder, OCamlTokenTypes.VIRTUAL_KEYWORD);

        NameParsing.parseMethodName(builder);

        if (virtual) {
            checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

            TypeParsing.parsePolyTypeExpression(builder);
        }
        else {
            final int[] parametersCount = { 0 };

            final Runnable parsing = new Runnable() {
                public void run() {
                    ExpressionParsing.parseParameter(builder);
                    parametersCount[0]++;
                }
            };

            while (!builder.eof() && builder.getTokenType() != OCamlTokenTypes.COLON && builder.getTokenType() != OCamlTokenTypes.EQ) {
                advanceLexerIfNothingWasParsed(builder, parsing);
            }

            if (ignore(builder, OCamlTokenTypes.COLON)) {
                if (parametersCount[0] == 0) {
                    TypeParsing.parsePolyTypeExpression(builder);
                } else {
                    TypeParsing.parseTypeExpression(builder);
                }
            }

            checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

            ExpressionParsing.parseExpression(builder);
        }

        methodClassFieldMarker.done(OCamlElementTypes.METHOD_CLASS_FIELD_DEFINITION);
    }

    private static void parseValueClassField(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker valueClassFieldMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.VAL_KEYWORD, Strings.VAL_KEYWORD_EXPECTED);

        ignore(builder, OCamlTokenTypes.MUTABLE_KEYWORD);
        final boolean virtual = ignore(builder, OCamlTokenTypes.VIRTUAL_KEYWORD);

        NameParsing.parseInstVarName(builder, NameParsing.NameType.DEFINITION);

        if (virtual) {
            checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

            TypeParsing.parseTypeExpression(builder);
        }
        else {
            if (ignore(builder, OCamlTokenTypes.COLON)) {
                TypeParsing.parseTypeExpression(builder);
            }

            checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

            ExpressionParsing.parseExpression(builder);
        }

        valueClassFieldMarker.done(OCamlElementTypes.VALUE_CLASS_FIELD_DEFINITION);
    }

    private static void parseConstraintClassField(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker constraintClassFieldMarker = builder.mark();

        doParseConstraint(builder);

        constraintClassFieldMarker.done(OCamlElementTypes.CONSTRAINT_CLASS_FIELD_DEFINITION);
    }

    private static void parseInitializerClassField(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker initializerClassFieldMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.INITIALIZER_KEYWORD, Strings.INITIALIZER_KEYWORD_EXPECTED);

        ExpressionParsing.parseExpression(builder);

        initializerClassFieldMarker.done(OCamlElementTypes.INITIALIZER_CLASS_FIELD_DEFINITION);
    }

    private static void parseInheritClassField(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker inheritClassFieldMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.INHERIT_KEYWORD, Strings.INHERIT_KEYWORD_EXPECTED);

        parseClassExpression(builder);

        if (ignore(builder, OCamlTokenTypes.AS_KEYWORD)) {
            NameParsing.parseInstVarName(builder, NameParsing.NameType.DEFINITION);
        }

        inheritClassFieldMarker.done(OCamlElementTypes.INHERIT_CLASS_FIELD_DEFINITION);
    }

    private static boolean tryParseClassType(@NotNull final PsiBuilder builder) {
        return tryParseClassFunctionType(builder) || tryParseClassBodyType(builder);
    }

    private static boolean tryParseClassBodyType(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.OBJECT_KEYWORD) {
            parseObjectClassBodyType(builder);
        } else if (!NameParsing.tryParseClassPath(builder) && !tryParseClassPathApplicationClassBodyType(builder)){
            return false;
        }

        return true;
    }

    private static boolean tryParseClassPathApplicationClassBodyType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classBodyTypeMarker = builder.mark();

        final PsiBuilder.Marker marker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LBRACKET)) {
            marker.drop();
            classBodyTypeMarker.drop();
            return false;
        }

        do {
            TypeParsing.parseTypeExpression(builder);
        } while (ignore(builder, OCamlTokenTypes.COMMA));

        checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);

        marker.done(OCamlElementTypes.BRACKETS);

        NameParsing.parseClassPath(builder);

        classBodyTypeMarker.done(OCamlElementTypes.CLASS_PATH_APPLICATION);

        return true;
    }

    private static void parseObjectClassBodyType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classObjectBodyTypeMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.OBJECT_KEYWORD, Strings.OBJECT_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LPAR)) {
            TypeParsing.parseTypeExpression(builder);

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            marker.done(OCamlElementTypes.OBJECT_SELF_SPECIFICATION);
        }
        else {
            marker.drop();
        }

        final boolean[] shouldBreak = { false };

        final Runnable parsing = new Runnable() {
            public void run() {
                if (!tryParseClassFieldSpecification(builder)) {
                    shouldBreak[0] = true;
                }
            }
        };

        while (!builder.eof() && !shouldBreak[0]) {
            advanceLexerIfNothingWasParsed(builder, shouldBreak, parsing);
        }

        checkMatches(builder, OCamlTokenTypes.END_KEYWORD, Strings.END_KEYWORD_EXPECTED);

        classObjectBodyTypeMarker.done(OCamlElementTypes.OBJECT_END_CLASS_BODY_TYPE);
    }

    private static boolean tryParseClassFieldSpecification(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.INHERIT_KEYWORD) {
            return tryParseInheritClassFieldSpecification(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.VAL_KEYWORD) {
            parseValueClassFieldSpecification(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.METHOD_KEYWORD) {
            parseMethodClassFieldSpecification(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.CONSTRAINT_KEYWORD) {
            parseConstraintClassFieldSpecification(builder);
        } else {
            return false;
        }

        return true;
    }

    private static void parseConstraintClassFieldSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker constraintClassFieldSpecificationMarker = builder.mark();

        doParseConstraint(builder);

        constraintClassFieldSpecificationMarker.done(OCamlElementTypes.CONSTRAINT_CLASS_FIELD_SPECIFICATION);
    }

    private static void parseMethodClassFieldSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker methodClassFieldSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.METHOD_KEYWORD, Strings.METHOD_KEYWORD_EXPECTED);

        ignore(builder, OCamlTokenTypes.PRIVATE_KEYWORD);
        ignore(builder, OCamlTokenTypes.VIRTUAL_KEYWORD);

        NameParsing.parseMethodName(builder);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        TypeParsing.parsePolyTypeExpression(builder);

        methodClassFieldSpecificationMarker.done(OCamlElementTypes.METHOD_CLASS_FIELD_SPECIFICATION);
    }

    private static void parseValueClassFieldSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker valueClassFieldSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.VAL_KEYWORD, Strings.VAL_KEYWORD_EXPECTED);

        ignore(builder, OCamlTokenTypes.MUTABLE_KEYWORD);
        ignore(builder, OCamlTokenTypes.VIRTUAL_KEYWORD);

        NameParsing.parseInstVarName(builder, NameParsing.NameType.DEFINITION);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        TypeParsing.parseTypeExpression(builder);

        valueClassFieldSpecificationMarker.done(OCamlElementTypes.VALUE_CLASS_FIELD_SPECIFICATION);
    }

    private static boolean tryParseInheritClassFieldSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker inheritClassFiledSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.INHERIT_KEYWORD, Strings.INHERIT_KEYWORD_EXPECTED);

        if (!tryParseClassType(builder)) {
            inheritClassFiledSpecificationMarker.drop();
            return false;
        }

        inheritClassFiledSpecificationMarker.done(OCamlElementTypes.INHERIT_CLASS_FILED_SPECIFICATION);

        return true;
    }

    private static void parseTypeParameters(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LBRACKET, Strings.LBRACKET_EXPECTED);

        do {
            TypeParsing.parseTypeParameter(builder, false, true);
        } while (ignore(builder, OCamlTokenTypes.COMMA));

        checkMatches(builder, OCamlTokenTypes.RBRACKET, Strings.RBRACKET_EXPECTED);

        marker.done(OCamlElementTypes.BRACKETS);
    }

    private static void parseClassSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.CLASS_KEYWORD, Strings.CLASS_KEYWORD_EXPECTED);

        do {
            parseClassSpecificationBinding(builder);
        } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));


        classSpecificationMarker.done(OCamlElementTypes.CLASS_SPECIFICATION);
    }

    private static void parseClassSpecificationBinding(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classSpecificationBindingMarker = builder.mark();

        doParseBindingStart(builder, false);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        tryParseClassType(builder);

        classSpecificationBindingMarker.done(OCamlElementTypes.CLASS_SPECIFICATION_BINDING);
    }

    private static void doParseConstraint(@NotNull final PsiBuilder builder) {
        checkMatches(builder, OCamlTokenTypes.CONSTRAINT_KEYWORD, Strings.CONSTRAINT_KEYWORD_EXPECTED);

        TypeParsing.parseTypeExpression(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        TypeParsing.parseTypeExpression(builder);
    }

    private static void doParseBindingStart(@NotNull final PsiBuilder builder, final boolean isClassType) {
        ignore(builder, OCamlTokenTypes.VIRTUAL_KEYWORD);

        if (builder.getTokenType() == OCamlTokenTypes.LBRACKET) {
            parseTypeParameters(builder);
        }

        if (isClassType) {
            NameParsing.parseTypeConstructorName(builder);
        }
        else {
            NameParsing.parseClassName(builder);
        }
    }

    private static void doParseClassExpressionInParentheses(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LPAR, Strings.LPAR_EXPECTED);

        parseClassExpression(builder);

        boolean colonParsed = false;

        if (ignore(builder, OCamlTokenTypes.COLON)) {
            colonParsed = true;

            tryParseClassType(builder);
        }

        checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

        marker.done(colonParsed ? OCamlElementTypes.CLASS_TYPE_CONSTRAINT : OCamlElementTypes.PARENTHESES_CLASS_EXPRESSION);
    }

    private static boolean tryParseClassFunctionType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classFunctionTypeMarker = builder.mark();

        final boolean labelParsed = NameParsing.tryParseQuestAndLabel(builder);

        TypeParsing.parseTupleTypeExpression(builder); //todo test it (int -> int -> myClass) BaseClassParsingTest.testClassType

        if (!ignore(builder, OCamlTokenTypes.MINUS_GT)) {
            if (labelParsed) {
                builder.error(Strings.MINUS_GT_EXPECTED);
            }
            else {
                classFunctionTypeMarker.rollbackTo();
                return false;
            }
        }

        tryParseClassType(builder);

        classFunctionTypeMarker.done(OCamlElementTypes.FUNCTION_CLASS_TYPE);

        return true;
    }
}
