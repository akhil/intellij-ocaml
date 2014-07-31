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

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlFile;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlStructuredBinding;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 22.03.2009
 */
public abstract class BaseOCamlFile extends PsiFileBase implements OCamlFile {
    protected BaseOCamlFile(@NotNull final FileViewProvider fileViewProvider, @NotNull final Language language) {
        super(fileViewProvider, language);
    }

    @Nullable
    public <T extends OCamlStructuredBinding> T getModuleBinding(@NotNull final Class<T> type) {
        return OCamlPsiUtil.getFirstChildOfType(this, type);
    }

    @Nullable
    public String getModuleName() {
        final VirtualFile file = getVirtualFile();
        return file == null ? null : OCamlStringUtil.firstLetterToUpperCase(file.getNameWithoutExtension());
    }
}
