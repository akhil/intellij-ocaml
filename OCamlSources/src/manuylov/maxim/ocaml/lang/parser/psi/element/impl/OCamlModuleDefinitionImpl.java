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
import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlModuleDefinition;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlModuleDefinitionBinding;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlModuleDefinitionImpl extends BaseOCamlElement implements OCamlModuleDefinition {
    public OCamlModuleDefinitionImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public boolean endsCorrectly() {
        return OCamlPsiUtil.endsCorrectlyWith(this, OCamlModuleDefinitionBinding.class);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitModuleDefinition(this);
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlModuleDefinitionBinding.class);
    }
}
