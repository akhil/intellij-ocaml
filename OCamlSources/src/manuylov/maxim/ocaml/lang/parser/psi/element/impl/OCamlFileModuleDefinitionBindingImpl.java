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
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import manuylov.maxim.ocaml.fileType.mli.parser.psi.element.impl.MLIFile;
import manuylov.maxim.ocaml.lang.feature.resolving.NameType;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.*;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 30.04.2010
 */
public class OCamlFileModuleDefinitionBindingImpl extends BaseOCamlFileModuleBinding<OCamlModuleExpression> implements OCamlFileModuleDefinitionBinding {
    public OCamlFileModuleDefinitionBindingImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @NotNull
    public NameType getNameType() {
        return NameType.UpperCase;
    }

    @NotNull
    public String getDescription() {
        return "module";
    }

    @Nullable
    public OCamlModuleExpression getExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlFileModuleExpression.class);
    }

    @Nullable
    public OCamlModuleType getTypeExpression() {
        final VirtualFile mliVirtualFile = OCamlFileUtil.getAnotherFile(getVirtualFile());
        if (mliVirtualFile == null) return null;
        final PsiFile mliFile = ApplicationManager.getApplication().runReadAction(new Computable<PsiFile>() {
            public PsiFile compute() {
                return PsiManager.getInstance(getProject()).findFile(mliVirtualFile);
            }
        });
        if (mliFile == null || !(mliFile instanceof MLIFile)) return null;
        final OCamlModuleSpecificationBinding moduleSpecificationBinding = ((MLIFile) mliFile).getModuleBinding(OCamlModuleSpecificationBinding.class);
        return moduleSpecificationBinding == null ? null : moduleSpecificationBinding.getExpression();
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitFileModuleDefinitionBinding(this);
    }
}
