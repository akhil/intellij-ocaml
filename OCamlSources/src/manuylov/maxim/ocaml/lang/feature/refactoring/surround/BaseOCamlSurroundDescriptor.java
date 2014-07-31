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

package manuylov.maxim.ocaml.lang.feature.refactoring.surround;

import com.intellij.lang.surroundWith.SurroundDescriptor;
import com.intellij.lang.surroundWith.Surrounder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 08.05.2010
 */
abstract class BaseOCamlSurroundDescriptor implements SurroundDescriptor {
    @NotNull private final Surrounder[] mySurrounders;
    private final Class<? extends OCamlElement> myElementClass;

    public BaseOCamlSurroundDescriptor(@NotNull final Class<? extends OCamlElement> elementClass,
                                       @NotNull final Surrounder... surrounders) {
        myElementClass = elementClass;
        mySurrounders = surrounders;
    }

    @NotNull
    public PsiElement[] getElementsToSurround(@NotNull final PsiFile file, final int startOffset, final int endOffset) {
        final PsiElement element = OCamlPsiUtil.findElementOfTypeInRange(file, myElementClass, startOffset, endOffset, true);
        return element == null ? PsiElement.EMPTY_ARRAY : new PsiElement[] { element };
    }

    @NotNull
    public Surrounder[] getSurrounders() {
      return mySurrounders;
    }
}
