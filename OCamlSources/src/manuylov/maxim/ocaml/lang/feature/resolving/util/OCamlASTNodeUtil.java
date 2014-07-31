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

package manuylov.maxim.ocaml.lang.feature.resolving.util;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageASTFactory;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 27.03.2009
 */
public class OCamlASTNodeUtil {
    @NotNull
    public static ASTNode createLeaf(@NotNull final IElementType elementType, @NotNull final String token) {
        final LeafElement createdNode = LanguageASTFactory.INSTANCE.forLanguage(elementType.getLanguage()).createLeaf(elementType, token);
        CodeEditUtil.setNodeGenerated(createdNode, true);
        //noinspection ConstantConditions
        return createdNode;
    }

    public static void replaceLeafText(@NotNull final ASTNode leaf, @NotNull final String text) {
        leaf.getTreeParent().replaceChild(leaf, createLeaf(leaf.getElementType(), text));
    }
}
