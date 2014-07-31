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

import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReferencesProcessor;
import manuylov.maxim.ocaml.lang.feature.resolving.ResolvingBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 28.03.2009
 */
abstract class BaseOCamlResolvedReferencesProcessor implements OCamlResolvedReferencesProcessor {
    @NotNull private final List<Class<? extends OCamlResolvedReference>> myTypes;

    private ResolvingBuilder myBuilder = null;

    public void setResolvingBuilder(@NotNull final ResolvingBuilder resolvingBuilder) {
        myBuilder = resolvingBuilder;
    }

    public BaseOCamlResolvedReferencesProcessor(@NotNull final List<Class<? extends OCamlResolvedReference>> types) {
        myTypes = types;
    }

    public boolean process(@NotNull final OCamlResolvedReference psiElement) {
        if (myBuilder != null && myBuilder.canProcessElement()) {
            for (final Class<? extends OCamlResolvedReference> type : myTypes) {
                if (type.isInstance(psiElement)) {
                    if (doProcess(psiElement)) return true;
                }
            }
        }

        return false;
    }

    @Nullable
    protected String getSourceElementName() {
        return myBuilder == null ? null : myBuilder.getContext().getSourceElement().getName();
    }

    protected abstract boolean doProcess(@NotNull final OCamlResolvedReference psiElement);
}
