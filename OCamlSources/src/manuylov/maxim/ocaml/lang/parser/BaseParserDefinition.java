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

package manuylov.maxim.ocaml.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.lexer.OCamlParsingLexer;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 22.02.2009
 */
public abstract class BaseParserDefinition implements ParserDefinition {
    @NotNull
    public Lexer createLexer(@NotNull final Project project) {
        return doCreateLexer();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return OCamlTokenTypes.WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return OCamlTokenTypes.COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return OCamlTokenTypes.STRING_LITERALS;
    }

    @NotNull
    public SpaceRequirements spaceExistanceTypeBetweenTokens(@NotNull final ASTNode left, @NotNull final ASTNode right) {
        if (left.getElementType() == OCamlElementTypes.LABEL_NAME && right.getElementType() == OCamlTokenTypes.COLON) {
            return SpaceRequirements.MUST_NOT;
        }
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, doCreateLexer());
    }

    @NotNull
    public PsiElement createElement(@NotNull final ASTNode astNode) {
        return OCamlElementFactory.INSTANCE.createElement(astNode);
    }

    @NotNull
    private Lexer doCreateLexer() {
        return new OCamlParsingLexer();
    }
}
