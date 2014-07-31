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

package manuylov.maxim.ocaml.lang.feature.resolving;

import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * @author Maxim.Manuylov
 *         Date: 27.03.2009
 */
public enum NameType {
    LowerCase("[_a-z][_0-9a-zA-Z']*"),
    UpperCase("[A-Z][_0-9a-zA-Z']*"),
    AnyCase("[_a-zA-Z][_0-9a-zA-Z']*"),
    ValueName("(" + LowerCase.myRegexp +")|(\\([=<>@\\^\\|\\&/$%!\\?~\\-+\\*][!$%\\&\\*\\-+./:<=>\\?@^\\|~]*\\))") {
        @Override
        public void checkNameIsCorrect(@NotNull final OCamlNamedElement reference, @NotNull final String name) throws IncorrectOperationException {
            super.checkNameIsCorrect(reference, name);
        }
    };

    @NotNull private final String myRegexp;

    NameType(@NotNull final String regexp) {
        myRegexp = regexp;
    }

    public void checkNameIsCorrect(@NotNull final OCamlNamedElement reference, @NotNull final String name) throws IncorrectOperationException {
        if (!isNameIsCorrect(name)) {
            throw new IncorrectOperationException("Name \"" + name + "\" is not possible for " + reference.getDescription());
        }
    }

    public boolean isNameIsCorrect(@NotNull final String name) {
        return Pattern.matches(myRegexp, name);
    }
}
