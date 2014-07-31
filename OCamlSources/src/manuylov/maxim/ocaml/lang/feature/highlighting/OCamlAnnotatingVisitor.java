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

package manuylov.maxim.ocaml.lang.feature.highlighting;

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlReference;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementProcessor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitorAdapter;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlUnknownElement;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @author Maxim.Manuylov
 *         Date: 22.03.2009
 */
public class OCamlAnnotatingVisitor extends OCamlElementVisitorAdapter implements Annotator, OCamlElementProcessor {
    @NotNull private AnnotationHolder myAnnotationHolder;

    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder holder) {
        myAnnotationHolder = holder;
        psiElement.accept(this);
    }

    @Override
    public void visitUnknownElement(@NotNull final OCamlUnknownElement psiElement) {
        final ASTNode node = psiElement.getNode();
        final Annotation annotation = myAnnotationHolder
            .createErrorAnnotation(psiElement, "OCaml element was not created properly: " + (node == null ? "null, ": node.getElementType().toString() + ", ") + psiElement.toString());
        annotation.setEnforcedTextAttributes(new TextAttributes(Color.black, Color.red.darker(), null, null, Font.PLAIN));
    }

    public void process(@NotNull final OCamlElement psiElement) {
        if (psiElement instanceof OCamlReference) {
            final OCamlReference ref = (OCamlReference) psiElement;
            if (!ref.isSoft() && !ref.isBundled() && ref.resolve() == null) {
                final Annotation annotation = myAnnotationHolder.createErrorAnnotation(psiElement, "Unknown " + ref.getDescription());
                annotation.setEnforcedTextAttributes(new TextAttributes(Color.red, null, null, null, Font.PLAIN));
            }
        }
    }
}
