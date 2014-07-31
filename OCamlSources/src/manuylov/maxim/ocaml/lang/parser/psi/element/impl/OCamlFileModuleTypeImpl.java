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
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlFileModuleType;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlSpecification;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlStructuredElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 24.05.2009
 */
public class OCamlFileModuleTypeImpl extends BaseOCamlElement implements OCamlFileModuleType {
    public OCamlFileModuleTypeImpl(@NotNull final ASTNode node) {
        super(node);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitFileModuleType(this);
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return builder.getLastParentPosition() == ElementPosition.Sibling
            && OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlSpecification.class);
    }

    @NotNull
    public List<OCamlStructuredElement> findActualDefinitions() {
        return Collections.<OCamlStructuredElement>singletonList(this);
    }
}
