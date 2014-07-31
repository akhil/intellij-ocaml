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
import manuylov.maxim.ocaml.lang.feature.resolving.ResolvingBuilder;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlExpression;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlExpressionStatement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 20.05.2010
 */
public class OCamlExpressionStatementImpl extends BaseOCamlElement implements OCamlExpressionStatement {
    public OCamlExpressionStatementImpl(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @Override
    public boolean endsCorrectly() {
        final OCamlExpression expression = getExpression();
        return expression != null && expression.endsCorrectly();
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        final OCamlExpression expression = getExpression();
        return expression != null && expression.processDeclarations(builder);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitExpressionStatement(this);
    }

    @Nullable
    public OCamlExpression getExpression() {
        return OCamlPsiUtil.getFirstChildOfType(this, OCamlExpression.class);
    }
}
