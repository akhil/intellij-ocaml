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

package manuylov.maxim.ocaml.lang.feature.refactoring.surround.surrounder;

import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 08.05.2010
 */
abstract class BaseOCamlSurrounderWithNavigation extends BaseOCamlSurrounder {
    public BaseOCamlSurrounderWithNavigation(@NotNull final String description) {
        super(description);
    }

    @NotNull
    @Override
    protected TextRange getTextRange(final int startPos, @NotNull final String surroundedText) {
        final int pos = startPos + getOffset(surroundedText);
        return new TextRange(pos, pos);
    }

    protected abstract int getOffset(@NotNull final String surroundedText);
}