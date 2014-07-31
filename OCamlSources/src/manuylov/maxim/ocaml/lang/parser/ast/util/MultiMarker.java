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

package manuylov.maxim.ocaml.lang.parser.ast.util;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 12.02.2009
 */
public class MultiMarker {
    @NotNull private final PsiBuilder myBuilder;
    @NotNull private final List<PsiBuilder.Marker> myMarkers = new ArrayList<PsiBuilder.Marker>();

    public MultiMarker(@NotNull final PsiBuilder builder) {
        myBuilder = builder;
    }
    
    public void mark() {
        myMarkers.add(myBuilder.mark());
    }

    public void done(@NotNull final IElementType type) {
        for (int i = myMarkers.size() - 1; i >= 0; i--) {
            myMarkers.get(i).done(type);
        }
        myMarkers.clear();
    }

    public void rollbackToTheFirstMark() {
        if (!myMarkers.isEmpty()) {
            myMarkers.get(0).rollbackTo();
        }
        myMarkers.clear();
    }

    public void drop() {
        for (int i = myMarkers.size() - 1; i >= 0; i--) {
            myMarkers.get(i).drop();
        }
        myMarkers.clear();
    }

    public void dropLast() {
        if (!myMarkers.isEmpty()) {
            myMarkers.remove(myMarkers.size() - 1).drop();
        }
    }
}
