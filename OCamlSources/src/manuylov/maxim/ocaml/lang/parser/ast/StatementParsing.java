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
public class StatementParsing extends Parsing {
    public static void parseDefinitionsAndExpressions(@NotNull final PsiBuilder builder, @NotNull final Condition exitCondition) {
        final Appearance[] lastDoubleSemicolon = { Appearance.None };

        final Runnable parsing = new Runnable() {
            public void run() {
                if (!tryParseDefinition(builder)) {
                    final PsiBuilder.Marker tempMarker = builder.mark();

                    if (tryParseExpressionStatement(builder)) {
                        if (lastDoubleSemicolon[0] == Appearance.No) {
                            tempMarker.rollbackTo();
                            builder.mark().error(Strings.SEMICOLON_SEMICOLON_EXPECTED);
                            parseExpressionStatement(builder);
                        }
                        else {
                            tempMarker.drop();
                        }
                    }
                    else {
                        tempMarker.error(Strings.DEFINITION_OR_EXPRESSION_EXPECTED);
                    }
                }

                lastDoubleSemicolon[0] = Appearance.create(ignore(builder, OCamlTokenTypes.SEMICOLON_SEMICOLON));
            }
        };

        if (ignore(builder, OCamlTokenTypes.SEMICOLON_SEMICOLON)) {
            lastDoubleSemicolon[0] = Appearance.Yes;
        }

        while (!exitCondition.test()) {
            advanceLexerIfNothingWasParsed(builder, parsing);
        }
    }

    public static void parseSpecifications(@NotNull final PsiBuilder builder, @NotNull final Condition exitCondition) {
        final Runnable parsing = new Runnable() {
            public void run() {
                if (!tryParseSpecification(builder)) {
                    builder.error(Strings.SPECIFICATION_EXPECTED);
                }

                ignore(builder, OCamlTokenTypes.SEMICOLON_SEMICOLON);
            }
        };

        ignore(builder, OCamlTokenTypes.SEMICOLON_SEMICOLON);

        while (!exitCondition.test()) {
            advanceLexerIfNothingWasParsed(builder, parsing);
        }
    }

    private static boolean tryParseExpressionStatement(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (ExpressionParsing.tryParseExpression(builder)) {
            marker.done(OCamlElementTypes.EXPRESSION_STATEMENT);
            return true;
        }

        marker.rollbackTo();
        return false;
    }

    private static void parseExpressionStatement(@NotNull final PsiBuilder builder) {
        if (!tryParseExpressionStatement(builder)) {
            builder.error(Strings.EXPRESSION_EXPECTED);
        }
    }

    private static boolean tryParseSpecification(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.VAL_KEYWORD) {
            parseValueSpecification(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.EXTERNAL_KEYWORD) {
            parseExternalDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.TYPE_KEYWORD) {
            TypeParsing.parseTypeDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.EXCEPTION_KEYWORD) {
            parseExceptionSpecification(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.CLASS_KEYWORD) {
            ClassParsing.parseClassSpecificationOrClassTypeDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.MODULE_KEYWORD) {
            ModuleParsing.parseModuleOrModuleTypeSpecification(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.OPEN_KEYWORD) {
            parseOpenDirective(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.INCLUDE_KEYWORD) {
            parseIncludeSpecification(builder);
        } else {
            return false;
        }

        return true;
    }

    private static boolean tryParseDefinition(@NotNull final PsiBuilder builder) {
        if (builder.getTokenType() == OCamlTokenTypes.LET_KEYWORD) {
            return LetParsing.tryParseLetStatement(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.EXTERNAL_KEYWORD) {
            parseExternalDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.TYPE_KEYWORD) {
            TypeParsing.parseTypeDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.EXCEPTION_KEYWORD) {
            parseExceptionDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.CLASS_KEYWORD) {
            ClassParsing.parseClassOrClassTypeDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.MODULE_KEYWORD) {
            ModuleParsing.parseModuleOrModuleTypeDefinition(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.OPEN_KEYWORD) {
            parseOpenDirective(builder);
        } else if (builder.getTokenType() == OCamlTokenTypes.INCLUDE_KEYWORD) {
            parseIncludeDefinition(builder);
        } else {
            return false;
        }

        return true;
    }

    private static void parseValueSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker valueSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.VAL_KEYWORD, Strings.VAL_KEYWORD_EXPECTED);

        NameParsing.parseValueName(builder, NameParsing.NameType.PATTERN);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        TypeParsing.parseTypeExpression(builder);

        valueSpecificationMarker.done(OCamlElementTypes.VALUE_SPECIFICATION);
    }

    private static void parseIncludeSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker includeSpecificationMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.INCLUDE_KEYWORD, Strings.INCLUDE_KEYWORD_EXPECTED);

        ModuleParsing.parseModuleType(builder);

        includeSpecificationMarker.done(OCamlElementTypes.INCLUDE_DIRECTIVE_SPECIFICATION);
    }

    private static void parseExceptionSpecification(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker exceptionSpecificationMarker = builder.mark();

        doParseExceptionSpecification(builder);

        if (ignore(builder, OCamlTokenTypes.OF_KEYWORD)) {
            TypeParsing.parseTypeExpression(builder);
        }
        
        exceptionSpecificationMarker.done(OCamlElementTypes.EXCEPTION_SPECIFICATION);
    }

    private static void parseIncludeDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker includeDirectiveMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.INCLUDE_KEYWORD, Strings.INCLUDE_KEYWORD_EXPECTED);

        ModuleParsing.parseModuleExpression(builder);

        includeDirectiveMarker.done(OCamlElementTypes.INCLUDE_DIRECTIVE_DEFINITION);
    }

    private static void parseOpenDirective(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker openDirectiveMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.OPEN_KEYWORD, Strings.OPEN_KEYWORD_EXPECTED);

        NameParsing.parseModulePath(builder);

        openDirectiveMarker.done(OCamlElementTypes.OPEN_DIRECTIVE);
    }

    private static void parseExceptionDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker exceptionDefinitionMarker = builder.mark();

        doParseExceptionSpecification(builder);

        if (ignore(builder, OCamlTokenTypes.EQ)) {
            NameParsing.parseConstructorPath(builder, NameParsing.NameType.NONE);
        }
        else if (ignore(builder, OCamlTokenTypes.OF_KEYWORD)) {
            TypeParsing.parseTypeExpression(builder);
        }

        exceptionDefinitionMarker.done(OCamlElementTypes.EXCEPTION_DEFINITION);
    }

    private static void parseExternalDefinition(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker externalDefinitionMarker = builder.mark();

        checkMatches(builder, OCamlTokenTypes.EXTERNAL_KEYWORD, Strings.EXTERNAL_KEYWORD_EXPECTED);

        NameParsing.parseValueName(builder, NameParsing.NameType.PATTERN);

        checkMatches(builder, OCamlTokenTypes.COLON, Strings.COLON_EXPECTED);

        TypeParsing.parseTypeExpression(builder);

        checkMatches(builder, OCamlTokenTypes.EQ, Strings.EQ_EXPECTED);

        parseExternalDeclaration(builder);

        externalDefinitionMarker.done(OCamlElementTypes.EXTERNAL_DEFINITION);
    }

    private static void parseExternalDeclaration(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker externalDeclarationMarker = builder.mark();

        while (ignore(builder, OCamlTokenTypes.STRING_LITERAL)) {}

        externalDeclarationMarker.done(OCamlElementTypes.EXTERNAL_DECLARATION);
    }

    private static void doParseExceptionSpecification(@NotNull final PsiBuilder builder) {
        checkMatches(builder, OCamlTokenTypes.EXCEPTION_KEYWORD, Strings.EXCEPTION_KEYWORD_EXPECTED);

        NameParsing.parseConstructorName(builder, NameParsing.NameType.DEFINITION);
    }

    public static interface Condition {
        boolean test();
    }

    private static enum Appearance {
        None,
        Yes,
        No;

        @NotNull
        public static Appearance create(final boolean appearance) {
            return appearance ? Yes : No;
        }
    }
}
