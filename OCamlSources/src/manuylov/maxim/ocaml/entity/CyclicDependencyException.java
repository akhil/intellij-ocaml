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

package manuylov.maxim.ocaml.entity;

import manuylov.maxim.ocaml.util.TreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 11.04.2010
 */
public class CyclicDependencyException extends Exception {
    public CyclicDependencyException(@NotNull final TreeNode<OCamlModule> node) {
        super(generateMessage(node));
    }

    @NotNull
    private static String generateMessage(@NotNull final TreeNode<OCamlModule> node) {
        final StringBuilder sb = new StringBuilder();
        final OCamlModule module = node.getData();
        TreeNode<OCamlModule> current = processNode(sb, node);
        while (current != null && !current.getData().equals(module)) {
            current = processNode(sb, current);
        }
        assert current != null;
        sb.insert(0, module.getName());
        sb.insert(0, "There is the cycle of OCaml module dependencies: ");
        return sb.toString();
    }

    @Nullable
    private static TreeNode<OCamlModule> processNode(@NotNull final StringBuilder sb, @NotNull final TreeNode<OCamlModule> node) {
        sb.insert(0, node.getData().getName());
        sb.insert(0, " -> ");
        return node.getParent();
    }
}
