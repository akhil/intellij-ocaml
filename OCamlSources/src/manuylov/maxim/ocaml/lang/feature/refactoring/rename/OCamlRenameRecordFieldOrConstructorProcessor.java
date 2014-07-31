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

package manuylov.maxim.ocaml.lang.feature.refactoring.rename;

import com.intellij.psi.PsiElement;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.util.containers.MultiMap;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlConstructorNameDefinition;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlRecordFieldDefinition;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlVariantTypeDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 03.06.2010
 */
public class OCamlRenameRecordFieldOrConstructorProcessor extends RenamePsiElementProcessor {
    @Override
    public boolean canProcessElement(@NotNull final PsiElement element) {
        return element instanceof OCamlRecordFieldDefinition || element instanceof OCamlConstructorNameDefinition; // todo support others (children of structuredElement)
    }

    @Override
    public void findExistingNameConflicts(@NotNull final PsiElement element, @NotNull final String newName, @NotNull final MultiMap<PsiElement, String> conflicts) {
        super.findExistingNameConflicts(element, newName, conflicts);
        final OCamlElement parent = element instanceof OCamlRecordFieldDefinition
            ? OCamlPsiUtil.getParent(element)
            : OCamlPsiUtil.getParentOfType(element, OCamlVariantTypeDefinition.class);
        if (parent == null) return;
        final List<OCamlResolvedReference> children = OCamlPsiUtil.getChildrenOfType(parent, OCamlResolvedReference.class);
        for (final OCamlResolvedReference child : children) {
            if (OCamlPsiUtil.isParentOf(child, element)) continue;
            if (newName.equals(child.getName())) {
                if (!conflicts.containsKey(child)) {
                    conflicts.putValue(child, String.format("Warning: %s already exists.", child.toString()));
                }
            }
        }
    }
}