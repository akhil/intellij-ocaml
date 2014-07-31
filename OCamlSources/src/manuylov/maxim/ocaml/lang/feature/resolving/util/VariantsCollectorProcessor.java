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

package manuylov.maxim.ocaml.lang.feature.resolving.util;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maxim.Manuylov
 *         Date: 27.03.2009
 */
class VariantsCollectorProcessor extends BaseOCamlResolvedReferencesProcessor {
    @NotNull private final ArrayList<LookupElement> myVariants = new ArrayList<LookupElement>();
    @NotNull private final Set<String> myPaths = new HashSet<String>();

    public VariantsCollectorProcessor(@NotNull final List<Class<? extends OCamlResolvedReference>> types) {
        super(types);
    }

    public boolean doProcess(@NotNull final OCamlResolvedReference psiElement) {
        final String path = psiElement.getCanonicalPath();
        if (!myPaths.contains(path)) {
            myPaths.add(path);
            myVariants.add(LookupElementBuilder.create(psiElement));
        }
        return false;
    }

    @NotNull
    public LookupElement[] getCollectedVariants() {
        return myVariants.toArray(new LookupElement[myVariants.size()]);
    }
}
