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
class ModuleParsing extends Parsing {
    public static void parseModuleOrModuleTypeDefinition(@NotNull final PsiBuilder builder) {
        if (getNextTokenType(builder) == OCamlTokenTypes.TYPE_KEYWORD) {
            parseModuleTypeDefinition(builder);
        }
        else {
            parseModuleDefinition(builder);
        }
    }

    public static void parseModuleExpression(@NotNull final PsiBuilder builder) {
        PsiBuilder.Marker moduleExpressionMarker = builder.mark();

        if (builder.getTokenType() == OCamlTokenTypes.STRUCT_KEYWORD) {
            parseStructModuleExpression(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.FUNCTOR_KEYWORD) {
            parseFunctorModuleExpression(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.LPAR) {
            parseModuleExpressionInParentheses(builder, true);
        } else if (!NameParsing.tryParseModulePath(builder)) {
            builder.error(Strings.MODULE_EXPRESSION_EXPECTED);
        }

        while (builder.getTokenType() == OCamlTokenTypes.LPAR) {
            parseModuleExpressionInParentheses(builder, false);

            moduleExpressionMarker.done(OCamlElementTypes.FUNCTOR_APPLICATION_MODULE_EXPRESSION);
            moduleExpressionMarker = moduleExpressionMarker.precede();
        }

        moduleExpressionMarker.drop();
    }

    public static void parseModuleType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypeMarker = builder.mark();

        if (builder.getTokenType() == OCamlTokenTypes.LPAR) {
            parseModuleTypeInParentheses(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.FUNCTOR_KEYWORD) {
            parseFunctorModuleType(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.SIG_KEYWORD) {
            parseSigModuleType(builder);
        } else if (!NameParsing.tryParseModuleTypePath(builder)) {
            builder.error(Strings.MODULE_TYPE_EXPECTED);
        }

        if (ignore(builder, OCamlTokenTypes.WITH_KEYWORD)) {
            do {
                parseModuleConstraint(builder);
            } while (ignore(builder, OCamlTokenTypes.AND_KEYWORD));

            moduleTypeMarker.done(OCamlElementTypes.MODULE_TYPE_WITH_CONSTRAINTS);
        }
        else {
            moduleTypeMarker.drop();
        }
    }

    public static void parseModuleOrModuleTypeSpecification(@NotNull final PsiBuilder builder) {
        if (getNextTokenType(builder) == OCamlTokenTypes.TYPE_KEYWORD) {
            parseModuleTypeSpecification(builder);
        }
        else {
            parseModuleSpecification(builder);
        }
    }

    private static void parseModuleTypeDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypeDefinitionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.MODULE_KEYWORD, Strings.MODULE_KEYWORD_EXPECTED);

        checkMatches(builder, OCamlTokenTypes.TYPE_KEYWORD, Strings.TYPE_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        NameParsing.parseModuleTypeName(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parseModuleType(builder);

        marker.done(OCamlElementTypes.MODULE_TYPE_DEFINITION_BINDING);

        moduleTypeDefinitionMarker.done(OCamlElementTypes.MODULE_TYPE_DEFINITION);
    }

    private static void parseModuleDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleDefinitionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.MODULE_KEYWORD, Strings.MODULE_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        doParseModuleBindingStarting(builder);

        if (ignore(builder, OCamlTokenTypes.COLON)) {
            parseModuleType(builder);
        }

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parseModuleExpression(builder);

        marker.done(OCamlElementTypes.MODULE_DEFINITION_BINDING);

        moduleDefinitionMarker.done(OCamlElementTypes.MODULE_DEFINITION);
    }

    private static void parseModuleExpressionInParentheses(@NotNull final PsiBuilder builder, final boolean isParenthesesModuleExpression) {
        final PsiBuilder.Marker moduleExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LPAR, Strings.LPAR_EXPECTED);

        parseModuleExpression(builder);

        boolean colonParsed = false;

        if (isParenthesesModuleExpression && ignore(builder, OCamlTokenTypes.COLON)) {
            colonParsed = true;

            parseModuleType(builder);
        }

        checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

        if (colonParsed) {
            moduleExpressionMarker.done(OCamlElementTypes.MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION);
        }
        else if (isParenthesesModuleExpression) {
            moduleExpressionMarker.done(OCamlElementTypes.PARENTHESES_MODULE_EXPRESSION);
        }
        else {
            moduleExpressionMarker.done(OCamlElementTypes.PARENTHESES);
        }
    }

    private static void parseFunctorModuleExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker functorModuleExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.FUNCTOR_KEYWORD, Strings.FUNCTOR_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LPAR, Strings.LPAR_EXPECTED);

        parseModuleParameterInner(builder);

        checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

        marker.done(OCamlElementTypes.PARENTHESES);

        checkMatches(builder, OCamlTokenTypes.MINUS_GT, Strings.MINUS_GT_EXPECTED);

        parseModuleExpression(builder);

        functorModuleExpressionMarker.done(OCamlElementTypes.FUNCTOR_MODULE_EXPRESSION);
    }

    private static void parseModuleParameterInner(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        NameParsing.parseModuleName(builder);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        parseModuleType(builder);

        marker.done(OCamlElementTypes.MODULE_PARAMETER);
    }

    private static void parseStructModuleExpression(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker structModuleExpressionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.STRUCT_KEYWORD, Strings.STRUCT_KEYWORD_EXPECTED);

        StatementParsing.parseDefinitionsAndExpressions(builder, createExitOnEndKeywordOrEofCondition(builder));

        structModuleExpressionMarker.done(OCamlElementTypes.STRUCT_END_MODULE_EXPRESSION);
    }

    @NotNull
    private static StatementParsing.Condition createExitOnEndKeywordOrEofCondition(@NotNull final PsiBuilder builder) {
        return new StatementParsing.Condition() {
            public boolean test() {
                if (ignore(builder, OCamlTokenTypes.END_KEYWORD)) {
                    return true;
                }
                else if (builder.eof()) {
                    builder.error(Strings.END_KEYWORD_EXPECTED);
                    return true;
                }
                else {
                    return false;
                }
            }
        };
    }

    private static void parseModuleConstraint(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.TYPE_KEYWORD) {
            parseTypeModuleConstraint(builder);
        }
        else if (builder.getTokenType() == OCamlTokenTypes.MODULE_KEYWORD) {
            parseModuleModuleConstraint(builder);
        }
        else {
            builder.error(Strings.TYPE_OR_MODULE_EXPECTED);
        }
    }

    private static void parseModuleModuleConstraint(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleModuleConstraintMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.MODULE_KEYWORD, Strings.MODULE_KEYWORD_EXPECTED);

        NameParsing.parseModulePath(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        NameParsing.parseExtendedModulePath(builder);

        moduleModuleConstraintMarker.done(OCamlElementTypes.MODULE_TYPE_MODULE_CONSTRAINT);
    }

    private static void parseTypeModuleConstraint(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeModuleConstraintMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.TYPE_KEYWORD, Strings.TYPE_KEYWORD_EXPECTED);

        if (builder.getTokenType() == OCamlTokenTypes.QUOTE) {
            do {
                TypeParsing.parseTypeParameter(builder, false, false);
            } while (ignore(builder, OCamlTokenTypes.COMMA));
        }

        NameParsing.parseTypeConstructorPath(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        TypeParsing.parseTypeExpression(builder);

        typeModuleConstraintMarker.done(OCamlElementTypes.MODULE_TYPE_TYPE_CONSTRAINT);
    }

    private static void parseSigModuleType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker sigModuleTypeMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.SIG_KEYWORD, Strings.SIG_KEYWORD_EXPECTED);

        StatementParsing.parseSpecifications(builder, createExitOnEndKeywordOrEofCondition(builder));

        sigModuleTypeMarker.done(OCamlElementTypes.SIG_END_MODULE_TYPE);
    }

    private static void parseModuleTypePathModuleType(@NotNull final PsiBuilder builder) {
        NameParsing.parseModuleTypePath(builder);
    }

    private static void parseFunctorModuleType(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker functorModuleTypeMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.FUNCTOR_KEYWORD, Strings.FUNCTOR_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LPAR, Strings.LPAR_EXPECTED);

        parseModuleParameterInner(builder);

        checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

        marker.done(OCamlElementTypes.PARENTHESES);

        checkMatches(builder, OCamlTokenTypes.MINUS_GT, Strings.MINUS_GT_EXPECTED);

        parseModuleType(builder);

        functorModuleTypeMarker.done(OCamlElementTypes.FUNCTOR_MODULE_TYPE);
    }

    private static void parseModuleTypeInParentheses(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.LPAR, Strings.LPAR_EXPECTED);

        parseModuleType(builder);

        checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

        marker.done(OCamlElementTypes.PARENTHESES_MODULE_TYPE);
    }

    private static void parseModuleSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.MODULE_KEYWORD, Strings.MODULE_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        doParseModuleBindingStarting(builder);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        parseModuleType(builder);

        marker.done(OCamlElementTypes.MODULE_SPECIFICATION_BINDING);

        moduleSpecificationMarker.done(OCamlElementTypes.MODULE_SPECIFICATION);
    }

    private static void parseModuleTypeSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypeSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.MODULE_KEYWORD, Strings.MODULE_KEYWORD_EXPECTED);

        checkMatches(builder, OCamlTokenTypes.TYPE_KEYWORD, Strings.TYPE_KEYWORD_EXPECTED);

        final PsiBuilder.Marker marker = builder.mark();

        NameParsing.parseModuleTypeName(builder);

        if (ignore(builder, OCamlTokenTypes.EQ)) {
            parseModuleType(builder);
        }

        marker.done(OCamlElementTypes.MODULE_TYPE_SPECIFICATION_BINDING);

        moduleTypeSpecificationMarker.done(OCamlElementTypes.MODULE_TYPE_SPECIFICATION);
    }

    private static void doParseModuleBindingStarting(@NotNull final PsiBuilder builder) {
        NameParsing.parseModuleName(builder);

        PsiBuilder.Marker marker = builder.mark();

        while (ignore(builder, OCamlTokenTypes.LPAR)) {
            parseModuleParameterInner(builder);

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            marker.done(OCamlElementTypes.PARENTHESES);
            marker = builder.mark();
        }

        marker.drop();
    }
}
