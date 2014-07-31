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

package manuylov.maxim.ocaml.lang.feature.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.lexer.OCamlHighlightingLexer;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim.Manuylov
 *         Date: 09.02.2009
 */
class OCamlSyntaxHighlighter extends SyntaxHighlighterBase {
    @NotNull private static final Map<IElementType, TextAttributesKey> keys = new HashMap<IElementType, TextAttributesKey>();

    @NotNull public static final TextAttributesKey OCAML_KEYWORD = TextAttributesKey.createTextAttributesKey("OCAML.KEYWORD", getTextAttributes(Color.blue.darker(), Font.BOLD));
    @NotNull public static final TextAttributesKey OCAML_STRING = TextAttributesKey.createTextAttributesKey("OCAML.STRING", getTextAttributes(Color.green, Font.BOLD));
    @NotNull public static final TextAttributesKey OCAML_ESCAPE = TextAttributesKey.createTextAttributesKey("OCAML.ESCAPE", getTextAttributes(Color.blue, Font.BOLD));
    @NotNull public static final TextAttributesKey OCAML_NUMBER = TextAttributesKey.createTextAttributesKey("OCAML.NUMBER",  getTextAttributes(Color.blue, Font.PLAIN));
    @NotNull public static final TextAttributesKey OCAML_COMMENT = TextAttributesKey.createTextAttributesKey("OCAML.COMMENT", getTextAttributes(Color.gray, Font.PLAIN));
    @NotNull public static final TextAttributesKey OCAML_SYNTAX_SYMBOL = TextAttributesKey.createTextAttributesKey("OCAML.SYNTAX.SYMBOL", getTextAttributes(Color.black, Font.BOLD));

    @NotNull
    public Lexer getHighlightingLexer() {
        return new OCamlHighlightingLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(@NotNull final IElementType tokenType) {
        return pack(keys.get(tokenType));
    }

    static {
        fillMap(keys, OCamlTokenTypes.KEYWORDS, OCAML_KEYWORD);
        fillMap(keys, OCamlTokenTypes.COMMENTS, OCAML_COMMENT);

        keys.put(OCamlTokenTypes.INTEGER_LITERAL, OCAML_NUMBER);
        keys.put(OCamlTokenTypes.FLOAT_LITERAL, OCAML_NUMBER);
        keys.put(OCamlTokenTypes.REGULAR_CHARS, OCAML_STRING);
        keys.put(OCamlTokenTypes.ESCAPE_SEQUENCES, OCAML_ESCAPE);

        keys.put(OCamlTokenTypes.COMMA, OCAML_SYNTAX_SYMBOL);
        keys.put(OCamlTokenTypes.DOT, OCAML_SYNTAX_SYMBOL);              
        keys.put(OCamlTokenTypes.SEMICOLON, OCAML_SYNTAX_SYMBOL);
        keys.put(OCamlTokenTypes.SEMICOLON_SEMICOLON, OCAML_SYNTAX_SYMBOL);

        keys.put(OCamlTokenTypes.BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);
    }

    @NotNull
    private static TextAttributes getTextAttributes(@NotNull final Color color, final int fontType) {
        return new TextAttributes(color, null, null, null, fontType);
    }
}
