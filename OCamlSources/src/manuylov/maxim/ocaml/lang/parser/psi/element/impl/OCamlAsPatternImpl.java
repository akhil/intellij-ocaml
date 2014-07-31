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
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlAsPattern;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlPattern;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlValueNamePattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlAsPatternImpl extends BaseOCamlResolvedReference implements OCamlAsPattern {
    public OCamlAsPatternImpl(@NotNull final ASTNode node) {
        super(node);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitAsPattern(this);
    }

    @Override
    public boolean endsCorrectly() {
        return OCamlPsiUtil.endsCorrectlyWith(this, OCamlValueNamePattern.class);
    }

    @Nullable
    public ASTNode getNameElement() {
        return OCamlASTTreeUtil.checkNodeType(getNode().getLastChildNode(), OCamlElementTypes.VALUE_NAME_PATTERN);
    }

    @NotNull
    public NameType getNameType() {
        return NameType.ValueName;
    }

    @NotNull
    public String getDescription() {
        return "pattern";
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return builder.getProcessor().process(this) || OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlPattern.class);    
    }
}
