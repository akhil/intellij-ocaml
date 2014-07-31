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
import com.intellij.lang.ITokenTypeRemapper;
import com.intellij.lang.LighterASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.util.Key;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.diff.FlyweightCapableTreeStructure;
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

/**
 * @author Maxim.Manuylov
 *         Date: 13.06.2009
 */
public class CommentsParserPsiBuilder implements PsiBuilder {
    @NotNull private final PsiBuilder myBuilder;

    public CommentsParserPsiBuilder(@NotNull final PsiBuilder builder) {
        myBuilder = builder;
        myBuilder.enforceCommentTokens(TokenSet.create());
    }

    public void advanceLexer() {
        tryParseComments();
        myBuilder.advanceLexer();
    }

    public IElementType getTokenType() {
        tryParseComments();
        return myBuilder.getTokenType();
    }

    public String getTokenText() {
        tryParseComments();
        return myBuilder.getTokenText();
    }

    public int getCurrentOffset() {
        tryParseComments();
        return myBuilder.getCurrentOffset();
    }

    public Marker mark() {
        return myBuilder.mark();
    }

    public void error(final String messageText) {
        tryParseComments();
        myBuilder.error(messageText);
    }

    public boolean eof() {
        tryParseComments();
        return myBuilder.eof();
    }

    public CharSequence getOriginalText() {
        return myBuilder.getOriginalText();
    }

    public void setTokenTypeRemapper(final ITokenTypeRemapper remapper) {
        myBuilder.setTokenTypeRemapper(remapper);
    }

    public ASTNode getTreeBuilt() {
        return myBuilder.getTreeBuilt();
    }

    public FlyweightCapableTreeStructure<LighterASTNode> getLightTree() {
        return myBuilder.getLightTree();
    }

    public void setDebugMode(final boolean dbgMode) {
        myBuilder.setDebugMode(dbgMode);
    }

    public void enforceCommentTokens(final TokenSet tokens) {
        myBuilder.enforceCommentTokens(tokens);
    }

    public LighterASTNode getLatestDoneMarker() {
        try {
            return (LighterASTNode) myBuilder.getClass().getMethod("getLatestDoneMarker").invoke(myBuilder);
        } catch (final IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public <T> T getUserData(@NotNull final Key<T> key) {
        return myBuilder.getUserData(key);
    }

    public <T> void putUserData(@NotNull final Key<T> key, @Nullable final T value) {
        myBuilder.putUserData(key, value);
    }

    private void tryParseComments() {
        while (myBuilder.getTokenType() == OCamlTokenTypes.COMMENT_BEGIN) {
            parseComment();
        }
    }

    private void parseComment() {
        final Stack<Marker> markers = new Stack<PsiBuilder.Marker>();

        do {
            if (myBuilder.getTokenType() == null) {
                while (markers.size() > 0) {
                    markers.pop().done(OCamlElementTypes.UNCLOSED_COMMENT);
                }
                myBuilder.error(Strings.UNCLOSED_COMMENT);
            }
            else if (myBuilder.getTokenType() == OCamlTokenTypes.COMMENT_BEGIN) {
                markers.push(myBuilder.mark());
                myBuilder.advanceLexer();
            }
            else if (myBuilder.getTokenType() == OCamlTokenTypes.COMMENT_END) {
                myBuilder.advanceLexer();
                markers.pop().done(OCamlElementTypes.COMMENT_BLOCK);
            }
            else if (myBuilder.getTokenType() == OCamlTokenTypes.COMMENT) {
                myBuilder.advanceLexer();
            }
        } while (markers.size() > 0);
    }
}
