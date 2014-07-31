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
import manuylov.maxim.ocaml.lang.feature.resolving.ElementPosition;
import manuylov.maxim.ocaml.lang.feature.resolving.ResolvingBuilder;
import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlLetBinding;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 23.04.2009
 */
abstract class BaseOCamlLetExpression extends BaseOCamlLetElement {
    public BaseOCamlLetExpression(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        if (builder.getLastParentPosition() == ElementPosition.Sibling) return false;

        final OCamlElement lastParent = builder.getLastParent();
        //noinspection SimplifiableIfStatement
        if (lastParent instanceof OCamlLetBinding && !isRecursive()) {
            return false;
        }

        return OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlLetBinding.class);
    }
}
