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

package manuylov.maxim.ocaml.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 11.04.2010
 */
public class TreeNode<T> {
    @NotNull private final T myData;
    @Nullable private TreeNode<T> myParent = null;
    @NotNull final private List<TreeNode<T>> myChildren = new ArrayList<TreeNode<T>>();

    public TreeNode(@NotNull final T data) {
        myData = data;
    }

    @NotNull
    public T getData() {
        return myData;
    }

    @NotNull
    public List<TreeNode<T>> getChildren() {
        return myChildren;
    }

    @Nullable
    public TreeNode<T> getParent() {
        return myParent;
    }

    public void addChild(@NotNull final TreeNode<T> child) {
        child.myParent = this;
        myChildren.add(child);
    }
}
