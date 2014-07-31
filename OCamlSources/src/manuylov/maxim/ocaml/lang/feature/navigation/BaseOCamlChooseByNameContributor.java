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

package manuylov.maxim.ocaml.lang.feature.navigation;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.util.ArrayUtil;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlNamedElement;
import manuylov.maxim.ocaml.lang.parser.stub.index.StubIndexHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Maxim.Manuylov
 *         Date: 27.04.2010
 */
abstract class BaseOCamlChooseByNameContributor implements ChooseByNameContributor {
    @NotNull private final StubIndexKey<String, ? extends OCamlNamedElement>[] myIndexKeys;

    protected BaseOCamlChooseByNameContributor(@NotNull final StubIndexKey<String, ? extends OCamlNamedElement>... indexKeys) {
        myIndexKeys = indexKeys;
    }

    public String[] getNames(@NotNull final Project project, final boolean includeNonProjectItems) {
        final Collection<String> names = new HashSet<String>();
        for (final StubIndexKey<String, ?> indexKey : myIndexKeys) {
            names.addAll(StubIndexHelper.getInstance(indexKey).getAllKeys(project, includeNonProjectItems));
        }
        return ArrayUtil.toStringArray(names);
    }

    public NavigationItem[] getItemsByName(@NotNull final String name, @NotNull final String pattern, @NotNull final Project project, final boolean includeNonProjectItems) {
        final GlobalSearchScope scope = StubIndexHelper.createScope(project, includeNonProjectItems);
        final Collection<NavigationItem> items = new ArrayList<NavigationItem>();
        for (final StubIndexKey<String, ? extends OCamlNamedElement> indexKey : myIndexKeys) {
            items.addAll(StubIndex.getInstance().get(indexKey, name, project, scope));
        }
        return items.toArray(new NavigationItem[items.size()]);
    }
}