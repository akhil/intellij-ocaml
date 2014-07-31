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
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import manuylov.maxim.ocaml.lang.feature.resolving.ResolvingBuilder;
import manuylov.maxim.ocaml.lang.feature.resolving.impl.BaseOCamlResolvedReference;
import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlFile;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlModuleType;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlStructuredBinding;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlStructuredElement;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 01.05.2010
 */
abstract class BaseOCamlFileModuleBinding<T extends OCamlStructuredElement> extends BaseOCamlResolvedReference implements OCamlStructuredBinding<T, OCamlModuleType> {
    public BaseOCamlFileModuleBinding(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    @Nullable
    public String getName() {
        final OCamlFile file = getFile();
        return file == null ? null : file.getModuleName();
    }

    @Override
    public int getTextOffset() {
        return 0;
    }

    @Override
    protected void doSetName(@NotNull final String name) throws IncorrectOperationException {
        final OCamlFile file = getFile();
        if (file == null) {
            throw new IncorrectOperationException("Incorrect " + getDescription() + " file");
        }

        final String newName = OCamlStringUtil.makeFirstLetterCaseTheSame(name, file.getName());
        file.setName(OCamlFileUtil.getFileName(newName, file.getFileType())); //todo files should be renamed automatically        
    }

    @Nullable
    public ASTNode getNameElement() {
        return null;
    }

    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return OCamlDeclarationsUtil.processDeclarationsInStructuredBinding(builder, this);
    }

    @Nullable
    protected OCamlFile getFile() {
        final PsiFile file = getContainingFile();
        return file instanceof OCamlFile ? (OCamlFile) file : null;
    }

    @Nullable
    protected VirtualFile getVirtualFile() {
        final OCamlFile file = getFile();
        return file == null ? null : file.getVirtualFile();
    }
}