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

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 16.04.2009
 */
public class OCamlASTTreeUtil {
    @Nullable
    public static ASTNode checkNodeType(@Nullable final ASTNode node, @NotNull final IElementType type) {
        return node != null && node.getElementType() == type ? node : null;
    }

    @Nullable
    public static ASTNode findChildOfType(@NotNull final ASTNode parent, @NotNull final IElementType type) {
        final ASTNode[] children = parent.getChildren(null);

        for (final ASTNode child : children) {
            final IElementType elementType = child.getElementType();
            if (elementType == null) {
                break;
            }
            else if (elementType == type) {
                return child;
            }
        }

        return null;
    }

}
