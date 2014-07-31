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

package manuylov.maxim.ocaml.lang.parser.psi.element.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlParenthesesExpression;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 13.05.2010
 */
public class OCamlParenthesesExpressionImpl extends OCamlParenthesesImpl implements OCamlParenthesesExpression {
    public OCamlParenthesesExpressionImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public boolean endsCorrectly() {
        final ASTNode firstChildNode = getNode().getFirstChildNode();
        //noinspection SimplifiableIfStatement
        if (firstChildNode == null) return false;
        final IElementType type = firstChildNode.getElementType();
        return type == OCamlTokenTypes.BEGIN_KEYWORD && OCamlPsiUtil.endsWith(this, OCamlTokenTypes.END_KEYWORD)
            || type == OCamlTokenTypes.LPAR && OCamlPsiUtil.endsWith(this, OCamlTokenTypes.RPAR);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitParenthesesExpression(this);
    }
}
