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

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import manuylov.maxim.ocaml.lang.feature.refactoring.rename.OCamlNamesValidator;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlNamedElement;
import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlASTNodeUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementProcessorAdapter;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlModuleDefinitionBinding;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlModuleSpecificationBinding;
import manuylov.maxim.ocaml.lang.parser.psi.element.impl.BaseOCamlElement;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 28.03.2009
 */
public abstract class BaseOCamlNamedElement extends BaseOCamlElement implements OCamlNamedElement {
    protected BaseOCamlNamedElement(@NotNull final ASTNode node) {
        super(node);
    }

    @Nullable
    public String getCanonicalPath() {
        final StringBuilder sb = new StringBuilder(OCamlStringUtil.getNotNull(getName()));

        final OCamlElementProcessorAdapter processor = new OCamlElementProcessorAdapter() {
            public void process(@NotNull final OCamlElement psiElement) {
                if (psiElement instanceof OCamlModuleDefinitionBinding || psiElement instanceof OCamlModuleSpecificationBinding) {
                    sb.insert(0, ".");
                    sb.insert(0, OCamlStringUtil.getNotNull(((OCamlNamedElement) psiElement).getName()));
                }
            }
        };

        OCamlElement parent = OCamlPsiUtil.getParent(this);

        while (parent != null) {
            parent.accept(processor);
            parent = OCamlPsiUtil.getParent(parent);
        }

        return sb.toString();
    }

    @Override
    @Nullable
    public String getName() {
        final ASTNode nameElement = getNameElement();
        return nameElement == null ? null : nameElement.getText();
    }

    @Override
    public int getTextOffset() {
        final ASTNode nameElement = getNameElement();
        return nameElement == null ? 0 : nameElement.getStartOffset();
    }

    @NotNull
    public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
        checkNameIsNotAKeyword(name);
        getNameType().checkNameIsCorrect(this, name);

        doSetName(name);

        return this;
    }

    private void checkNameIsNotAKeyword(@NotNull final String name) throws IncorrectOperationException {
        if (OCamlNamesValidator.isKeyword(name)) {
            throw new IncorrectOperationException("It is not allowed to use a keyword as a " + getDescription() + " name.");
        }
    }

    protected void doSetName(@NotNull final String name) throws IncorrectOperationException {
        final ASTNode nameElement = getNameElement();
        if (nameElement == null) {
            throw new IncorrectOperationException("Incorrect " + getDescription() + " name element");
        }

        OCamlASTNodeUtil.replaceLeafText(nameElement.getFirstChildNode(), name);
    }

    @Override
    public String toString() {
        return getDescription() + " " + OCamlStringUtil.getNotNull(getName());
    }

    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @NotNull
            public String getPresentableText() {
                return getDescription() + ' ' + getName();
            }

            @NotNull
            public String getLocationString() {
                return '(' + getContainingFile().getName() + ')';
            }

            @Nullable
            public Icon getIcon(final boolean open) {
                return null;
            }

            @Nullable
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }
        };
    }
}
