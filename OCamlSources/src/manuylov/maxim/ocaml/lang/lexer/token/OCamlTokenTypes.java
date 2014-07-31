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

package manuylov.maxim.ocaml.lang.lexer.token;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.OCamlElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 05.02.2009
 */
public interface OCamlTokenTypes {
    @NotNull IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    @NotNull IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    @NotNull IElementType COMMENT = new OCamlElementType("COMMENT");
    @NotNull IElementType COMMENT_BEGIN = new OCamlElementType("COMMENT_BEGIN");
    @NotNull IElementType COMMENT_END = new OCamlElementType("COMMENT_END");

    @NotNull IElementType AND_KEYWORD = new OCamlElementType("AND_KEYWORD");
    @NotNull IElementType AS_KEYWORD = new OCamlElementType("AS_KEYWORD");
    @NotNull IElementType ASSERT_KEYWORD = new OCamlElementType("ASSERT_KEYWORD");
    @NotNull IElementType ASR_KEYWORD = new OCamlElementType("ASR_KEYWORD");
    @NotNull IElementType BEGIN_KEYWORD = new OCamlElementType("BEGIN_KEYWORD");
    @NotNull IElementType CLASS_KEYWORD = new OCamlElementType("CLASS_KEYWORD");
    @NotNull IElementType CONSTRAINT_KEYWORD = new OCamlElementType("CONSTRAINT_KEYWORD");
    @NotNull IElementType DO_KEYWORD = new OCamlElementType("DO_KEYWORD");
    @NotNull IElementType DONE_KEYWORD = new OCamlElementType("DONE_KEYWORD");
    @NotNull IElementType DOWNTO_KEYWORD = new OCamlElementType("DOWNTO_KEYWORD");
    @NotNull IElementType ELSE_KEYWORD = new OCamlElementType("ELSE_KEYWORD");
    @NotNull IElementType END_KEYWORD = new OCamlElementType("END_KEYWORD");
    @NotNull IElementType EXCEPTION_KEYWORD = new OCamlElementType("EXCEPTION_KEYWORD");
    @NotNull IElementType EXTERNAL_KEYWORD = new OCamlElementType("EXTERNAL_KEYWORD");
    @NotNull IElementType FALSE_KEYWORD = new OCamlElementType("FALSE_KEYWORD");
    @NotNull IElementType FOR_KEYWORD = new OCamlElementType("FOR_KEYWORD");
    @NotNull IElementType FUN_KEYWORD = new OCamlElementType("FUN_KEYWORD");
    @NotNull IElementType FUNCTION_KEYWORD = new OCamlElementType("FUNCTION_KEYWORD");
    @NotNull IElementType FUNCTOR_KEYWORD = new OCamlElementType("FUNCTOR_KEYWORD");
    @NotNull IElementType IF_KEYWORD = new OCamlElementType("IF_KEYWORD");
    @NotNull IElementType IN_KEYWORD = new OCamlElementType("IN_KEYWORD");
    @NotNull IElementType INCLUDE_KEYWORD = new OCamlElementType("INCLUDE_KEYWORD");
    @NotNull IElementType INHERIT_KEYWORD = new OCamlElementType("INHERIT_KEYWORD");
    @NotNull IElementType INITIALIZER_KEYWORD = new OCamlElementType("INITIALIZER_KEYWORD");
    @NotNull IElementType LAND_KEYWORD = new OCamlElementType("LAND_KEYWORD");
    @NotNull IElementType LAZY_KEYWORD = new OCamlElementType("LAZY_KEYWORD");
    @NotNull IElementType LET_KEYWORD = new OCamlElementType("LET_KEYWORD");
    @NotNull IElementType LOR_KEYWORD = new OCamlElementType("LOR_KEYWORD");
    @NotNull IElementType LSR_KEYWORD = new OCamlElementType("LSR_KEYWORD");
    @NotNull IElementType LSL_KEYWORD = new OCamlElementType("LSL_KEYWORD");
    @NotNull IElementType LXOR_KEYWORD = new OCamlElementType("LXOR_KEYWORD");
    @NotNull IElementType MATCH_KEYWORD = new OCamlElementType("MATCH_KEYWORD");
    @NotNull IElementType METHOD_KEYWORD = new OCamlElementType("METHOD_KEYWORD");
    @NotNull IElementType MOD_KEYWORD = new OCamlElementType("MOD_KEYWORD");
    @NotNull IElementType MODULE_KEYWORD = new OCamlElementType("MODULE_KEYWORD");
    @NotNull IElementType MUTABLE_KEYWORD = new OCamlElementType("MUTABLE_KEYWORD");
    @NotNull IElementType NEW_KEYWORD = new OCamlElementType("NEW_KEYWORD");
    @NotNull IElementType OBJECT_KEYWORD = new OCamlElementType("OBJECT_KEYWORD");
    @NotNull IElementType OF_KEYWORD = new OCamlElementType("OF_KEYWORD");
    @NotNull IElementType OPEN_KEYWORD = new OCamlElementType("OPEN_KEYWORD");
    @NotNull IElementType OR_KEYWORD = new OCamlElementType("OR_KEYWORD");
    @NotNull IElementType PRIVATE_KEYWORD = new OCamlElementType("PRIVATE_KEYWORD");
    @NotNull IElementType REC_KEYWORD = new OCamlElementType("REC_KEYWORD");
    @NotNull IElementType SIG_KEYWORD = new OCamlElementType("SIG_KEYWORD");
    @NotNull IElementType STRUCT_KEYWORD = new OCamlElementType("STRUCT_KEYWORD");
    @NotNull IElementType THEN_KEYWORD = new OCamlElementType("THEN_KEYWORD");
    @NotNull IElementType TO_KEYWORD = new OCamlElementType("TO_KEYWORD");
    @NotNull IElementType TRUE_KEYWORD = new OCamlElementType("TRUE_KEYWORD");
    @NotNull IElementType TRY_KEYWORD = new OCamlElementType("TRY_KEYWORD");
    @NotNull IElementType TYPE_KEYWORD = new OCamlElementType("TYPE_KEYWORD");
    @NotNull IElementType VAL_KEYWORD = new OCamlElementType("VAL_KEYWORD");
    @NotNull IElementType VIRTUAL_KEYWORD = new OCamlElementType("VIRTUAL_KEYWORD");
    @NotNull IElementType WHEN_KEYWORD = new OCamlElementType("WHEN_KEYWORD");
    @NotNull IElementType WHILE_KEYWORD = new OCamlElementType("WHILE_KEYWORD");
    @NotNull IElementType WITH_KEYWORD = new OCamlElementType("WITH_KEYWORD");

    @NotNull IElementType NOT_EQ = new OCamlElementType("NOT_EQ"); // !=
    @NotNull IElementType HASH = new OCamlElementType("HASH"); // #
    @NotNull IElementType AMP = new OCamlElementType("AMP"); // &
    @NotNull IElementType AMP_AMP = new OCamlElementType("AMP_AMP"); // &&
    @NotNull IElementType QUOTE = new OCamlElementType("QUOTE"); // '
    @NotNull IElementType LPAR = new OCamlElementType("LPAR"); // (
    @NotNull IElementType RPAR = new OCamlElementType("RPAR"); // )
    @NotNull IElementType MULT = new OCamlElementType("MULT"); // *
    @NotNull IElementType PLUS = new OCamlElementType("PLUS"); // +
    @NotNull IElementType COMMA = new OCamlElementType("COMMA"); // ,
    @NotNull IElementType MINUS = new OCamlElementType("MINUS"); // -
    @NotNull IElementType MINUS_DOT = new OCamlElementType("MINUS_DOT"); // -.
    @NotNull IElementType MINUS_GT = new OCamlElementType("MINUS_GT"); // ->
    @NotNull IElementType DOT = new OCamlElementType("DOT"); // .
    @NotNull IElementType DOT_DOT = new OCamlElementType("DOT_DOT"); // ..
    @NotNull IElementType COLON = new OCamlElementType("COLON"); // :
    @NotNull IElementType COLON_COLON = new OCamlElementType("COLON_COLON"); // ::
    @NotNull IElementType COLON_EQ = new OCamlElementType("COLON_EQ"); // :=
    @NotNull IElementType COLON_GT = new OCamlElementType("COLON_GT"); // :>
    @NotNull IElementType SEMICOLON = new OCamlElementType("SEMICOLON"); // ;
    @NotNull IElementType SEMICOLON_SEMICOLON = new OCamlElementType("SEMICOLON_SEMICOLON"); // ;;
    @NotNull IElementType LT = new OCamlElementType("LT"); // <
    @NotNull IElementType LT_MINUS = new OCamlElementType("LT_MINUS"); // <-
    @NotNull IElementType EQ = new OCamlElementType("EQ"); // =
    @NotNull IElementType GT = new OCamlElementType("GT"); // >
    @NotNull IElementType GT_RBRACKET = new OCamlElementType("GT_RBRACKET"); // >]
    @NotNull IElementType GT_RBRACE = new OCamlElementType("GT_RBRACE"); // >}
    @NotNull IElementType QUEST = new OCamlElementType("QUEST"); // ?
    @NotNull IElementType QUEST_QUEST = new OCamlElementType("QUEST_QUEST"); // ??
    @NotNull IElementType LBRACKET = new OCamlElementType("LBRACKET"); // [
    @NotNull IElementType LBRACKET_LT = new OCamlElementType("LBRACKET_LT"); // [<
    @NotNull IElementType LBRACKET_GT = new OCamlElementType("LBRACKET_GT"); // [>
    @NotNull IElementType LBRACKET_VBAR = new OCamlElementType("LBRACKET_VBAR"); // [|
    @NotNull IElementType RBRACKET = new OCamlElementType("RBRACKET"); // ]
    @NotNull IElementType UNDERSCORE = new OCamlElementType("UNDERSCORE"); // _
    @NotNull IElementType ACCENT = new OCamlElementType("ACCENT"); // `
    @NotNull IElementType LBRACE = new OCamlElementType("LBRACE"); // {
    @NotNull IElementType LBRACE_LT = new OCamlElementType("LBRACE_LT"); // {<
    @NotNull IElementType VBAR = new OCamlElementType("VBAR"); // |
    @NotNull IElementType VBAR_VBAR = new OCamlElementType("VBAR_VBAR"); // ||
    @NotNull IElementType VBAR_RBRACKET = new OCamlElementType("VBAR_RBRACKET"); // |]
    @NotNull IElementType RBRACE = new OCamlElementType("RBRACE"); // }
    @NotNull IElementType TILDE = new OCamlElementType("TILDE"); // ~

    /* Camlp4 extensions compatibility { */

    @NotNull IElementType PARSER_KEYWORD = new OCamlElementType("PARSER_KEYWORD");
    @NotNull IElementType LT_LT = new OCamlElementType("LT_LT"); // <<
    @NotNull IElementType LT_COLON = new OCamlElementType("LT_COLON"); // <:
    @NotNull IElementType GT_GT = new OCamlElementType("GT_GT"); // >>
    @NotNull IElementType DOLLAR = new OCamlElementType("DOLLAR"); // $
    @NotNull IElementType DOLLAR_DOLLAR = new OCamlElementType("DOLLAR_DOLLAR"); // $$
    @NotNull IElementType DOLLAR_COLON = new OCamlElementType("DOLLAR_COLON"); // $:

    /* } */

    @NotNull IElementType LCFC_IDENTIFIER = new OCamlElementType("LCFC_IDENTIFIER");
    @NotNull IElementType UCFC_IDENTIFIER = new OCamlElementType("UCFC_IDENTIFIER");

    @NotNull IElementType INTEGER_LITERAL = new OCamlElementType("INTEGER_LITERAL");
    @NotNull IElementType FLOAT_LITERAL = new OCamlElementType("FLOAT_LITERAL");
    @NotNull IElementType CHAR_LITERAL = new OCamlElementType("CHAR_LITERAL");
    @NotNull IElementType EMPTY_CHAR_LITERAL = new OCamlElementType("EMPTY_CHAR_LITERAL");
    @NotNull IElementType STRING_LITERAL = new OCamlElementType("STRING_LITERAL");

    @NotNull IElementType INFIX_OPERATOR = new OCamlElementType("INFIX_OPERATOR");
    @NotNull IElementType PREFIX_OPERATOR = new OCamlElementType("PREFIX_OPERATOR");

    /* Highlighting only tokens { */

    @NotNull IElementType REGULAR_CHARS = new OCamlElementType("REGULAR_CHARS");
    @NotNull IElementType ESCAPE_SEQUENCES = new OCamlElementType("ESCAPE_SEQUENCES");
    @NotNull IElementType OPENING_QUOTE = new OCamlElementType("OPENING_QUOTE"); // '
    @NotNull IElementType CLOSING_QUOTE = new OCamlElementType("CLOSING_QUOTE"); // '
    @NotNull IElementType OPENING_DOUBLE_QUOTE = new OCamlElementType("OPENING_DOUBLE_QUOTE"); // "
    @NotNull IElementType CLOSING_DOUBLE_QUOTE = new OCamlElementType("CLOSING_DOUBLE_QUOTE"); // "

    /* } */

    @NotNull TokenSet IDENTIFIERS = TokenSet.create( LCFC_IDENTIFIER, UCFC_IDENTIFIER );

    @NotNull TokenSet KEYWORDS = TokenSet
        .create(AND_KEYWORD, AS_KEYWORD, ASSERT_KEYWORD, BEGIN_KEYWORD, CLASS_KEYWORD, CONSTRAINT_KEYWORD, DO_KEYWORD, DONE_KEYWORD, DOWNTO_KEYWORD, ELSE_KEYWORD, END_KEYWORD,
            EXCEPTION_KEYWORD, EXTERNAL_KEYWORD, FALSE_KEYWORD, FOR_KEYWORD, FUN_KEYWORD, FUNCTION_KEYWORD, FUNCTOR_KEYWORD, IF_KEYWORD, IN_KEYWORD, INCLUDE_KEYWORD,
            INHERIT_KEYWORD, INITIALIZER_KEYWORD, LAZY_KEYWORD, LET_KEYWORD, MATCH_KEYWORD, METHOD_KEYWORD, MODULE_KEYWORD, MUTABLE_KEYWORD, NEW_KEYWORD, OBJECT_KEYWORD,
            OF_KEYWORD, OPEN_KEYWORD, OR_KEYWORD, PARSER_KEYWORD, PRIVATE_KEYWORD, REC_KEYWORD, SIG_KEYWORD, STRUCT_KEYWORD, THEN_KEYWORD, TO_KEYWORD, TRUE_KEYWORD,
            TRY_KEYWORD, TYPE_KEYWORD, VAL_KEYWORD, VIRTUAL_KEYWORD, WHEN_KEYWORD, WHILE_KEYWORD, WITH_KEYWORD);

    @NotNull TokenSet PREFIX_OPERATORS = TokenSet.create( PREFIX_OPERATOR, NOT_EQ, QUEST_QUEST );

    @NotNull TokenSet INFIX_OPERATORS = TokenSet.create(INFIX_OPERATOR, EQ, LT, GT, AMP, PLUS, MINUS, MULT, DOLLAR, AMP_AMP, MINUS_DOT, VBAR_VBAR, LT_LT, LT_COLON, GT_GT, DOLLAR_DOLLAR,
                                                        DOLLAR_COLON, OR_KEYWORD, COLON_EQ, MOD_KEYWORD, LAND_KEYWORD, LOR_KEYWORD, LXOR_KEYWORD, LSL_KEYWORD, LSR_KEYWORD, ASR_KEYWORD);

    @NotNull TokenSet WHITE_SPACES = TokenSet.create( WHITE_SPACE );

    @NotNull TokenSet STRING_LITERALS = TokenSet.create( STRING_LITERAL );
    @NotNull TokenSet CHAR_LITERALS = TokenSet.create( CHAR_LITERAL, EMPTY_CHAR_LITERAL );
    @NotNull TokenSet COMMENTS = TokenSet.create( COMMENT, COMMENT_BEGIN, COMMENT_END );

    /* TokenSets for DefaultWordsScanner { */

    @NotNull TokenSet DWS_IDENTIFIERS = IDENTIFIERS;
    @NotNull TokenSet DWS_COMMENTS = TokenSet.create( COMMENT );
    @NotNull TokenSet DWS_LITERALS = TokenSet.create( INTEGER_LITERAL, FLOAT_LITERAL, REGULAR_CHARS, ESCAPE_SEQUENCES );

    /* } */

    /* TokenSets for QuoteHandler { */

    @NotNull TokenSet QH_OPENING_QUOTES = TokenSet.create( OPENING_QUOTE, OPENING_DOUBLE_QUOTE );
    @NotNull TokenSet QH_CLOSING_QUOTES = TokenSet.create( CLOSING_QUOTE, CLOSING_DOUBLE_QUOTE );
    @NotNull TokenSet QH_STRING_LITERALS = TokenSet.create( REGULAR_CHARS, ESCAPE_SEQUENCES );

    /* } */
}

