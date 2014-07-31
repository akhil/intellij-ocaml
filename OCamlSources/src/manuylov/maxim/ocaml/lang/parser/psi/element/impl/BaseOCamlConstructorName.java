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
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import manuylov.maxim.ocaml.lang.feature.resolving.impl.BaseOCamlReference;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
abstract class BaseOCamlConstructorName extends BaseOCamlReference implements OCamlConstructorName {
    @NotNull private static final Set<String> ourBundledConstructors = new HashSet<String>() {{
        add("Division_by_zero");
        add("End_of_file");
        add("Failure");
        add("Invalid_argument");
        add("None");
        add("Some");
        add("Sys_error");
    }};

    @Override
    public boolean isBundled() {
        return ourBundledConstructors.contains(getName());
    }

    public BaseOCamlConstructorName(@NotNull final ASTNode node) {
        super(node);
    }

    @Nullable
    public ASTNode getNameElement() {
        return getNode();
    }

    @NotNull
    public NameType getNameType() {
        return NameType.UpperCase;
    }

    @NotNull
    public String getDescription() {
        return "constructor";
    }

    @NotNull
    public List<Class<? extends OCamlResolvedReference>> getPossibleResolvedTypes() {
        return Arrays.asList(OCamlConstructorDefinition.class, OCamlConstructorNameDefinition.class);
    }

    @NotNull
    public List<OCamlModuleName> getModulePath() {
        return OCamlPsiUtil.getModulePath(this, OCamlModuleName.class);
    }

    @NotNull
    public List<OCamlResolvedMethod> getAvailableMethods() {
        return Collections.emptyList();
    }
}
