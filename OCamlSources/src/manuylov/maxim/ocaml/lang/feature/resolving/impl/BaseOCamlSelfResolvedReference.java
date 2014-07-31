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

package manuylov.maxim.ocaml.lang.feature.resolving.impl;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import manuylov.maxim.ocaml.lang.feature.resolving.ResolvingBuilder;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlExtendedModuleName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 25.04.2010
 */
public abstract class BaseOCamlSelfResolvedReference extends BaseOCamlReference implements OCamlResolvedReference {
    public BaseOCamlSelfResolvedReference(@NotNull final ASTNode node) {
        super(node);
    }

    @Nullable
    public ASTNode getNameElement() {
        return getNode();
    }

    @NotNull
    public List<Class<? extends OCamlResolvedReference>> getPossibleResolvedTypes() {
        return Arrays.<Class<? extends OCamlResolvedReference>>asList(getClass());
    }

    @NotNull
    public List<OCamlExtendedModuleName> getModulePath() {
        return Collections.emptyList();
    }

    @Override
    public OCamlResolvedReference resolve() {
        return this;
    }

    @NotNull
    @Override
    public LookupElement[] getVariants() {
        return new LookupElement[] { LookupElementBuilder.create(this) };
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return builder.getProcessor().process(this);
    }
}
