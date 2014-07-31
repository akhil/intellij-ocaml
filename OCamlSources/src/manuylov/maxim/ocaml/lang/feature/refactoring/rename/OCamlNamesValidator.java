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

package manuylov.maxim.ocaml.lang.feature.refactoring.rename;

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.feature.resolving.NameType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxim.Manuylov
 *         Date: 26.04.2010
 */
public class OCamlNamesValidator implements NamesValidator {
    @NotNull private static final Set<String> ourKeywords = new HashSet<String>();

    public boolean isKeyword(final String name, final Project project) {
        return isKeyword(name);
    }

    public static boolean isKeyword(@NotNull final String name) {
        initializeKeywordsSetIfNeeded();
        return ourKeywords.contains(name);
    }

    public boolean isIdentifier(final String name, final Project project) {
        for (final NameType nameType : NameType.values()) {
            if (nameType.isNameIsCorrect(name)) return true;
        }
        return false;
    }

    private static void initializeKeywordsSetIfNeeded() {
        if (ourKeywords.isEmpty()) {
            for (final Field field : Keywords.class.getFields()) {
                try {
                    ourKeywords.add(String.valueOf(field.get(null)));
                } catch (final IllegalAccessException ignore) {}
            }
        }
    }
}
