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
import manuylov.maxim.ocaml.lang.feature.resolving.ResolvingBuilder;
import manuylov.maxim.ocaml.lang.feature.resolving.impl.BaseOCamlResolvedReference;
import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.ast.util.OCamlASTTreeUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlTypeBindingImpl extends BaseOCamlResolvedReference implements OCamlTypeBinding {
    public OCamlTypeBindingImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public boolean endsCorrectly() {
        return OCamlPsiUtil.endsCorrectlyWith(this, OCamlRecordTypeDefinition.class)
            || OCamlPsiUtil.endsCorrectlyWith(this, OCamlVariantTypeDefinition.class)
            || OCamlPsiUtil.endsCorrectlyWith(this, OCamlTypeExpression.class)
            || OCamlPsiUtil.endsCorrectlyWith(this, OCamlTypeDefinitionConstraint.class);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitTypeBinding(this);
    }

    @Nullable
    public ASTNode getNameElement() {
        return OCamlASTTreeUtil.findChildOfType(getNode(), OCamlElementTypes.TYPE_CONSTRUCTOR_NAME);
    }

    @NotNull
    public NameType getNameType() {
        return NameType.LowerCase;
    }

    @NotNull
    public String getDescription() {
        return "type";
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return super.processDeclarations(builder) || OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlTypeRepresentation.class);
    }

    @Nullable
    public OCamlTypeExpression getExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlTypeExpression.class);
    }

    @Nullable
    public OCamlTypeExpression getTypeExpression() {
        return null;
    }
}
