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

package manuylov.maxim.ocaml.lang.parser.ast.testCase;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.BaseOCamlTestCase;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import manuylov.maxim.ocaml.lang.parser.ast.util.TreeStringBuilder;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementFactory;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.util.ParserTestUtil;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public abstract class ParsingTestCase extends BaseOCamlTestCase {
    protected TreeStringBuilder myTree;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        recreateTree();
    }

    protected void recreateTree() {
        myTree = new TreeStringBuilder(false);
        myTree.addNode(0, getParserDefinition().getFileNodeType());
        myTree.addNode(1, getModuleBindingNodeType());
        myTree.addNode(2, getModuleExpressionNodeType());
    }

    protected void doTest(@NotNull final String text, @NotNull final String expectedTree) throws Exception {
        assertEquals(getTreeAsString(text, false, false, true), expectedTree);
    }

    @NotNull
    private String getTreeAsString(@NotNull final String text,
                                   final boolean ignoreParentheses,
                                   final boolean throwExceptionIfErrorElementOccurred,
                                   final boolean checkAllNodesEndCorrectly) throws Exception {
        final ASTNode root = ParserTestUtil.buildTree(text, getParserDefinition());
        if (checkAllNodesEndCorrectly) {
            checkAllNodesEndCorrectly(OCamlElementFactory.INSTANCE.createElement(root));
        }
        return treeToString(root, ignoreParentheses, throwExceptionIfErrorElementOccurred);
    }

    private void checkAllNodesEndCorrectly(@NotNull final OCamlElement root) {
        final ASTNode node = root.getNode();
        assert node != null : root.toString();
        assertEquals(root.endsCorrectly(), node.getElementType() != OCamlElementTypes.UNCLOSED_COMMENT, "type: " + node.getElementType());
        final List<OCamlElement> children = OCamlPsiUtil.getChildren(root);
        for (final OCamlElement child : children) {
            checkAllNodesEndCorrectly(child);
        }
    }

    @NotNull
    protected String getTreeIgnoringParentheses(@NotNull final String text) throws Exception {
        return getTreeAsString(text, true, true, false);
    }

    protected void assertIsAllowed(@NotNull final String text) throws Exception {
        getTreeAsString(text, false, true, false);
    }

    protected void assertIsNotAllowed(@NotNull final String text) throws Exception {
        try {
            getTreeAsString(text, false, true, false);
        }
        catch (Throwable ignored) {
            return;
        }
        fail();
    }

    @NotNull
    private String treeToString(@NotNull final ASTNode root, final boolean ignoreParentheses, final boolean throwExceptionIfErrorElementOccurred) {
        return TreeStringBuilder.buildTreeString(root, true, ignoreParentheses, throwExceptionIfErrorElementOccurred).getStringRepresentation();
    }

    @NotNull
    protected abstract ParserDefinition getParserDefinition();

    @NotNull
    protected abstract IElementType getModuleExpressionNodeType();

    @NotNull
    protected abstract IElementType getModuleBindingNodeType();
}
