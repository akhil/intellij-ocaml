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

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.ASTNode;
import manuylov.maxim.ocaml.lang.feature.resolving.NameType;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import manuylov.maxim.ocaml.lang.feature.resolving.impl.BaseOCamlReference;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlExtendedModuleName;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlMethodName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlMethodNameImpl extends BaseOCamlReference implements OCamlMethodName {
    public OCamlMethodNameImpl(@NotNull final ASTNode node) {
        super(node);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitMethodName(this);
    }

    public ASTNode getNameElement() {
        return getNode();
    }

    @NotNull
    public NameType getNameType() {
        return NameType.LowerCase;
    }

    @NotNull
    public String getDescription() {
        return "method";
    }

    @NotNull
    public List<Class<? extends OCamlResolvedReference>> getPossibleResolvedTypes() {
        return Collections.emptyList();
    }

    @NotNull
    public List<OCamlExtendedModuleName> getModulePath() {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public OCamlResolvedReference resolve() {
/*
        final OCamlElement parent = OCamlPsiUtil.getParent(this);
        if (parent == null) return null;

        if (parent instanceof OCamlResolvedMethod) {
            return (OCamlResolvedMethod) parent;
        }

        if (!(parent instanceof OCamlClassMethodAccessingExpression)) return null;

        final OCamlExpression object = ((OCamlClassMethodAccessingExpression) parent).getObject();

        if (object == null) return null;

        final List<OCamlResolvedMethod> methods = object.getAvailableMethods();
        final String name = getName();

        for (final OCamlResolvedMethod method : methods) {
            if (OCamlResolvingUtil.areEqual(name, method.getName())) {
                return method;
            }
        }
*/

        return null; //todo
    }



    @NotNull
    @Override
    public LookupElement[] getVariants() {
/*
        final OCamlElement parent = OCamlPsiUtil.getParent(this);
        if (parent == null) return new OCamlResolvedReference[0];

        if (!(parent instanceof OCamlClassMethodAccessingExpression)) return new OCamlResolvedReference[0];

        final OCamlExpression object = ((OCamlClassMethodAccessingExpression) parent).getObject();

        if (object == null) return new OCamlResolvedReference[0];

        return (OCamlResolvedReference[]) object.getAvailableMethods().toArray();
*/
        return new LookupElement[0]; //todo
    }

    @Override
    public boolean isSoft() {
        return true; // todo
    }
}
