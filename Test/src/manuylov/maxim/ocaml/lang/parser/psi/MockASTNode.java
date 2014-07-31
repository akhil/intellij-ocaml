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

package manuylov.maxim.ocaml.lang.parser.psi;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public class MockASTNode implements ASTNode {
    private final IElementType myElementType;

    public MockASTNode(@NotNull final IElementType elementType) {
        myElementType = elementType;
    }

    @NotNull
    public IElementType getElementType() {
        return myElementType;
    }

    public String getText() {
        return null;
    }

    public CharSequence getChars() {
        return null;
    }

    public boolean textContains(final char c) {
        return false;
    }

    public int getStartOffset() {
        return 0;
    }

    public int getTextLength() {
        return 0;
    }

    public TextRange getTextRange() {
        return null;
    }

    public ASTNode getTreeParent() {
        return null;
    }

    public ASTNode getFirstChildNode() {
        return null;
    }

    public ASTNode getLastChildNode() {
        return null;
    }

    public ASTNode getTreeNext() {
        return null;
    }

    public ASTNode getTreePrev() {
        return null;
    }

    public ASTNode[] getChildren(@Nullable final TokenSet filter) {
        return new ASTNode[0];
    }

    public void addChild(@NotNull final ASTNode child) {
    }

    public void addChild(@NotNull final ASTNode child, final ASTNode anchorBefore) {
    }

    public void addLeaf(@NotNull final IElementType leafType, final CharSequence leafText, final ASTNode anchorBefore) {
    }

    public void removeChild(@NotNull final ASTNode child) {
    }

    public void removeRange(@NotNull final ASTNode firstNodeToRemove, final ASTNode firstNodeToKeep) {
    }

    public void replaceChild(@NotNull final ASTNode oldChild, @NotNull final ASTNode newChild) {
    }

    public void replaceAllChildrenToChildrenOf(final ASTNode anotherParent) {
    }

    public void addChildren(final ASTNode firstChild, final ASTNode firstChildToNotAdd, final ASTNode anchorBefore) {
    }

    public Object clone() {
        return null;
    }

    public ASTNode copyElement() {
        return null;
    }

    public ASTNode findLeafElementAt(final int offset) {
        return null;
    }

    public <T> T getCopyableUserData(final Key<T> key) {
        return null;
    }

    public <T> void putCopyableUserData(final Key<T> key, final T value) {
    }

    public ASTNode findChildByType(final IElementType type) {
        return null;
    }

    public ASTNode findChildByType(@NotNull final TokenSet typesSet) {
        return null;
    }

    public ASTNode findChildByType(@NotNull final TokenSet typesSet, @Nullable final ASTNode anchor) {
        return null;
    }

    public PsiElement getPsi() {
        return null;
    }

    public <T> T getUserData(final Key<T> key) {
        return null;
    }

    public <T> void putUserData(final Key<T> key, final T value) {
    }
}
