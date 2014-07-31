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

package manuylov.maxim.ocaml.lang.feature.resolving;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlExtendedModuleName;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public interface OCamlReference extends OCamlNamedElement, PsiReference {
    @Nullable
    OCamlResolvedReference resolve();

    @NotNull
    LookupElement[] getVariants();

    @NotNull
    List<Class<? extends OCamlResolvedReference>> getPossibleResolvedTypes();

    @NotNull
    List<? extends OCamlExtendedModuleName> getModulePath();

    boolean isBundled();

    boolean isReferenceToWithFakeModules(@NotNull final PsiElement element, @NotNull final OCamlFile... fakeModules);
}
