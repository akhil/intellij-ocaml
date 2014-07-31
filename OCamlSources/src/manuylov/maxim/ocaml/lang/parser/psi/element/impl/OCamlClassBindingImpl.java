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
import manuylov.maxim.ocaml.lang.feature.resolving.NameType;
import manuylov.maxim.ocaml.lang.feature.resolving.impl.BaseOCamlResolvedReference;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.ast.util.OCamlASTTreeUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlClassBinding;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlClassExpression;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlClassType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlClassBindingImpl extends BaseOCamlResolvedReference implements OCamlClassBinding {
    public OCamlClassBindingImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public boolean endsCorrectly() {
        return OCamlPsiUtil.endsCorrectlyWith(this, OCamlClassExpression.class);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitClassBinding(this);
    }

    @Nullable
    public ASTNode getNameElement() {
        return OCamlASTTreeUtil.findChildOfType(getNode(), OCamlElementTypes.CLASS_NAME);
    }

    @NotNull
    public NameType getNameType() {
        return NameType.LowerCase;
    }

    @NotNull
    public String getDescription() {
        return "class";
    }

    @Nullable
    public OCamlClassExpression getExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlClassExpression.class);
    }

    @Nullable
    public OCamlClassType getTypeExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlClassType.class);
    }
}
