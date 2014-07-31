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

package manuylov.maxim.ocaml.lang.feature.braceMatching;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 24.05.2009
 */
public class OCamlBraceMatcher implements PairedBraceMatcher {
    @NotNull private final BracePair[] myBracePairs = new BracePair[] {
        new BracePair(OCamlTokenTypes.LPAR, OCamlTokenTypes.RPAR, false),
        new BracePair(OCamlTokenTypes.LBRACE, OCamlTokenTypes.RBRACE, false),
        new BracePair(OCamlTokenTypes.LBRACE_LT, OCamlTokenTypes.GT_RBRACE, false),
        new BracePair(OCamlTokenTypes.LBRACKET, OCamlTokenTypes.RBRACKET, false),
        new BracePair(OCamlTokenTypes.LBRACKET_GT, OCamlTokenTypes.RBRACKET, false),
        new BracePair(OCamlTokenTypes.LBRACKET_LT, OCamlTokenTypes.RBRACKET, false),
        new BracePair(OCamlTokenTypes.LBRACKET_VBAR, OCamlTokenTypes.VBAR_RBRACKET, false),
        new BracePair(OCamlTokenTypes.COMMENT_BEGIN, OCamlTokenTypes.COMMENT_END, false),
        new BracePair(OCamlTokenTypes.BEGIN_KEYWORD, OCamlTokenTypes.END_KEYWORD, true),
        new BracePair(OCamlTokenTypes.STRUCT_KEYWORD, OCamlTokenTypes.END_KEYWORD, true),
        new BracePair(OCamlTokenTypes.SIG_KEYWORD, OCamlTokenTypes.END_KEYWORD, true),
        new BracePair(OCamlTokenTypes.OBJECT_KEYWORD, OCamlTokenTypes.END_KEYWORD, true),
    };

    @NotNull
    public BracePair[] getPairs() {
        return myBracePairs;
    }

    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType lbraceType, @Nullable final IElementType contextType) {
        return true;
    }

    public int getCodeConstructStart(@NotNull final PsiFile file, final int openingBraceOffset) {
        return openingBraceOffset;
    }
}
