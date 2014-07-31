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
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * @author Maxim.Manuylov
 *         Date: 25.02.2009
 */
public class TreeStringBuilder {
    @NotNull private static final TokenSet ourParenthesesTypes = TokenSet.create(
        OCamlElementTypes.PARENTHESES_CLASS_EXPRESSION,
        OCamlElementTypes.PARENTHESES_EXPRESSION,
        OCamlElementTypes.PARENTHESES_MODULE_EXPRESSION,
        OCamlElementTypes.PARENTHESES_MODULE_TYPE,
        OCamlElementTypes.PARENTHESES_PATTERN,
        OCamlElementTypes.PARENTHESES_TYPE_EXPRESSION,
        OCamlElementTypes.PARENTHESES_TYPE_PARAMETERS
    );

    private final StringBuilder myStringBuilder = new StringBuilder("");
    private final boolean myThrowExceptionIfErrorElementOccurred;
    private int ignoredLeftParCount = 0;
    private int ignoredRightParCount = 0;

    public TreeStringBuilder(final boolean throwExceptionIfErrorElementOccurred) {
        myThrowExceptionIfErrorElementOccurred = throwExceptionIfErrorElementOccurred;
    }

    public static TreeStringBuilder buildTreeString(@NotNull final ASTNode root, final boolean ignoreWhiteSpaces, final boolean ignoreParentheses,
                                              final boolean throwExceptionIfErrorElementOccurred) {
        final TreeStringBuilder builder = new TreeStringBuilder(throwExceptionIfErrorElementOccurred);
        processNode(builder, 0, root, ignoreWhiteSpaces, ignoreParentheses);
        if (builder.ignoredLeftParCount != builder.ignoredRightParCount) {
            throw new RuntimeException("Unbalanced parentheses.");
        }
        return builder;
    }

    private static void processNode(@NotNull final TreeStringBuilder builder, final int level, @NotNull final ASTNode node,
                                    final boolean ignoreWhiteSpaces, final boolean ignoreParentheses) {
        doProcessNode(builder, level, node, ignoreWhiteSpaces, ignoreParentheses);

        final ASTNode[] children = node.getChildren(null);
        for (final ASTNode child : children) {
            processNode(builder, ignoreParentheses && ourParenthesesTypes.contains(node.getElementType()) ? level : level + 1, child, ignoreWhiteSpaces, ignoreParentheses);
        }
    }

    private static void doProcessNode(@NotNull final TreeStringBuilder builder, final int level, @NotNull final ASTNode node,
                                      final boolean ignoreWhiteSpaces, final boolean ignoreParentheses) {
        final IElementType elementType = node.getElementType();

        if (ignoreWhiteSpaces && elementType == TokenType.WHITE_SPACE) return;

        if (ignoreParentheses) {
            if (ourParenthesesTypes.contains(elementType)) return;

            if (elementType == OCamlTokenTypes.LPAR) {
                final ASTNode next = node.getTreeNext();
                if (next != null && next.getElementType() != OCamlTokenTypes.RPAR) {
                    builder.ignoredLeftParCount++;
                    return;
                }
            }
            else if (elementType == OCamlTokenTypes.RPAR) {
                final ASTNode prev = node.getTreePrev();
                if (prev != null && prev.getElementType() != OCamlTokenTypes.LPAR) {
                    builder.ignoredRightParCount++;
                    return;
                }
            }
        }

        if (node.getChildren(null).length == 0 || elementType == TokenType.ERROR_ELEMENT) {
            final String text;

            if (elementType == TokenType.ERROR_ELEMENT) {
                assert node instanceof PsiErrorElement;
                text = ((PsiErrorElement)node).getErrorDescription();
                if (builder.myThrowExceptionIfErrorElementOccurred) {
                    throw new RuntimeException("Error element: " + text + ".\n\nTree:\n\n" + buildTreeString(getRoot(node), ignoreWhiteSpaces, ignoreParentheses, false).getStringRepresentation());
                }
            }
            else {
                text = node.getText();
            }
            
            builder.addNode(level, elementType, text);
        }
        else {
            builder.addNode(level, elementType);
        }
    }

    public void addNode(final int level, @NotNull final IElementType type) {
        final String text = getElementText(type);

        if (text != null) {
            addNode(level, type, text);
            return;
        }

        addIndent(level);
        myStringBuilder.append(type.toString());
        myStringBuilder.append("\n");
    }

    public void addNode(final int level, @NotNull final IElementType type, @NotNull final String text) {
        addIndent(level);
        myStringBuilder.append(type.toString());
        myStringBuilder.append("('");
        myStringBuilder.append(text);
        myStringBuilder.append("')");
        myStringBuilder.append("\n");
    }

    @NotNull
    public String getStringRepresentation() {
        return myStringBuilder.toString();
    }

    @NotNull
    private static ASTNode getRoot(@NotNull final ASTNode node) {
        ASTNode root = node;

        while (root.getTreeParent() != null) {
            root = root.getTreeParent();
        }

        return root;
    }

    private void addIndent(final int level) {
        for (int i = 0; i < level; i++) {
            myStringBuilder.append("  ");
        }
    }

    @Nullable
    private String getElementText(@NotNull final IElementType type) {
        final Field[] fields = OCamlTokenTypes.class.getFields();

        for (final Field field : fields) {
            try {
                if (field.get(null) == type) {
                    final String fieldName = field.getName();
                    String text = findTextInClass(Strings.class, fieldName);
                    if (text != null) return text;
                    text = findTextInClass(Keywords.class, fieldName);
                    if (text != null) return text;
                }
            }
            catch (final IllegalAccessException ignored) { }
        }

        return null;
    }

    @Nullable
    private String findTextInClass(@NotNull final Class<?> clazz, @NotNull final String fieldName) throws IllegalAccessException {
        try {
            return String.valueOf(clazz.getField(fieldName).get(null));
        } catch (final NoSuchFieldException e) {
            return null;
        }
    }
}
