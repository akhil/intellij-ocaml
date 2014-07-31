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

package manuylov.maxim.ocaml.lang.lexer;

import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.lexer.testCase.LexerTestCase;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public abstract class BaseLexerTest extends LexerTestCase {
    public void testLineTerminator() {
        doTest("\r\n", token(OCamlTokenTypes.WHITE_SPACE, "\r\n"));
        doTest("\n", token(OCamlTokenTypes.WHITE_SPACE, "\n"));
        doTest("\r", token(OCamlTokenTypes.WHITE_SPACE, "\r"));
    }

    public void testWhiteSpaces() {
        doTest(" ", token(OCamlTokenTypes.WHITE_SPACE, " "));
        doTest("\t", token(OCamlTokenTypes.WHITE_SPACE, "\t"));
        doTest("\f", token(OCamlTokenTypes.WHITE_SPACE, "\f"));
    }

    public void testComments() {
        doTest("(*)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, ")"));
        doTest("(**)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(*\n*)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, "\n"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(***)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, "*"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(* *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(* aa *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " aa "), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("( * )", token(OCamlTokenTypes.LPAR, "("), token(OCamlTokenTypes.WHITE_SPACE, " "), token(OCamlTokenTypes.MULT, "*"), token(OCamlTokenTypes.WHITE_SPACE, " "), token(OCamlTokenTypes.RPAR, ")"));
        doTest("(* *) *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"), token(OCamlTokenTypes.WHITE_SPACE, " "), token(OCamlTokenTypes.MULT, "*"), token(OCamlTokenTypes.RPAR, ")"));
        doTest("(* (* *) *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(*(**)*)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT_END, "*)"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(* (* *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"));
    }

    public void testIntegerLiteral() {
        doTest("2", token(OCamlTokenTypes.INTEGER_LITERAL, "2"));
        doTest("-2", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "2"));
        doTest("-2_300", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "2_300"));
        doTest("002_300", token(OCamlTokenTypes.INTEGER_LITERAL, "002_300"));
        doTest("-0x02_48_AB_CD_EF", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0x02_48_AB_CD_EF"));
        doTest("-0X02_48_AB_CD_EF", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0X02_48_AB_CD_EF"));
        doTest("-0o02_47", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0o02_47"));
        doTest("-0O02_47", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0O02_47"));
        doTest("-0b00_11", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0b00_11"));
        doTest("-0B11_00", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0B11_00"));
        doTest("12A", token(OCamlTokenTypes.INTEGER_LITERAL, "12"), token(OCamlTokenTypes.UCFC_IDENTIFIER, "A"));
        doTest("0x12T", token(OCamlTokenTypes.INTEGER_LITERAL, "0x12"), token(OCamlTokenTypes.UCFC_IDENTIFIER, "T"));
        doTest("0o128", token(OCamlTokenTypes.INTEGER_LITERAL, "0o12"), token(OCamlTokenTypes.INTEGER_LITERAL, "8"));
        doTest("0b12", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1"), token(OCamlTokenTypes.INTEGER_LITERAL, "2"));
        doTest("2l", token(OCamlTokenTypes.INTEGER_LITERAL, "2l"));
        doTest("2L", token(OCamlTokenTypes.INTEGER_LITERAL, "2L"));
        doTest("2n", token(OCamlTokenTypes.INTEGER_LITERAL, "2n"));
        doTest("0b1l", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1l"));
        doTest("0b1L", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1L"));
        doTest("0b1n", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1n"));
        doTest("0o1l", token(OCamlTokenTypes.INTEGER_LITERAL, "0o1l"));
        doTest("0o1L", token(OCamlTokenTypes.INTEGER_LITERAL, "0o1L"));
        doTest("0o1n", token(OCamlTokenTypes.INTEGER_LITERAL, "0o1n"));
        doTest("0x1l", token(OCamlTokenTypes.INTEGER_LITERAL, "0x1l"));
        doTest("0x1L", token(OCamlTokenTypes.INTEGER_LITERAL, "0x1L"));
        doTest("0x1n", token(OCamlTokenTypes.INTEGER_LITERAL, "0x1n"));
    }

    public void testFloatLiteral() {
        doTest("-6_4.", token(OCamlTokenTypes.MINUS, Keywords.MINUS), token(OCamlTokenTypes.FLOAT_LITERAL, "6_4."));
        doTest("6._", token(OCamlTokenTypes.FLOAT_LITERAL, "6._"));
        doTest("6.2", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2"));
        doTest("6.2e2", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e2"));
        doTest("6.2e3_", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e3_"));
        doTest("6.2e+3", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e+3"));
        doTest("6.2e-3", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e-3"));
        doTest("6.E-3", token(OCamlTokenTypes.FLOAT_LITERAL, "6.E-3"));
        doTest("6E3", token(OCamlTokenTypes.FLOAT_LITERAL, "6E3"));
    }

    public void testIdentifier() {
        doTest("pKH_B_wb'y2b8_87'3n_", token(OCamlTokenTypes.LCFC_IDENTIFIER, "pKH_B_wb'y2b8_87'3n_"));
        doTest("K'H_B_wby2b8_873n_", token(OCamlTokenTypes.UCFC_IDENTIFIER, "K'H_B_wby2b8_873n_"));
        doTest("_pKH_B_wby2b8_873n_'", token(OCamlTokenTypes.LCFC_IDENTIFIER, "_pKH_B_wby2b8_873n_'"));
        doTest("_PKH_B_wby2b8_873n_", token(OCamlTokenTypes.LCFC_IDENTIFIER, "_PKH_B_wby2b8_873n_"));
    }

    public void testInfixSymbol() {
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.INFIX_OPERATOR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.EQ));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.GT));
        assertFalse(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.VBAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.AMP));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.PLUS));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MINUS));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MULT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.DOLLAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.AMP_AMP));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MINUS_DOT));
        assertFalse(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MINUS_GT));
        assertFalse(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT_MINUS));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.VBAR_VBAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT_LT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT_COLON));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.GT_GT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.DOLLAR_DOLLAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.DOLLAR_COLON));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.OR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.COLON_EQ));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MOD_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LAND_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LOR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LXOR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LSL_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LSR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.ASR_KEYWORD));

        doTest("=", token(OCamlTokenTypes.EQ, Keywords.EQ));
        doTest("<", token(OCamlTokenTypes.LT, Keywords.LT));
        doTest(">", token(OCamlTokenTypes.GT, Keywords.GT));
        doTest("@", token(OCamlTokenTypes.INFIX_OPERATOR, "@"));
        doTest("^", token(OCamlTokenTypes.INFIX_OPERATOR, "^"));
        doTest("&", token(OCamlTokenTypes.AMP, Keywords.AMP));
        doTest("+", token(OCamlTokenTypes.PLUS, Keywords.PLUS));
        doTest("-", token(OCamlTokenTypes.MINUS, Keywords.MINUS));
        doTest("*", token(OCamlTokenTypes.MULT, Keywords.MULT));
        doTest("/", token(OCamlTokenTypes.INFIX_OPERATOR, "/"));
        doTest("$", token(OCamlTokenTypes.DOLLAR, Keywords.DOLLAR));
        doTest("%", token(OCamlTokenTypes.INFIX_OPERATOR, "%"));

        doTest("=!", token(OCamlTokenTypes.INFIX_OPERATOR, "=!"));
        doTest("<!", token(OCamlTokenTypes.INFIX_OPERATOR, "<!"));
        doTest(">!", token(OCamlTokenTypes.INFIX_OPERATOR, ">!"));
        doTest("@!", token(OCamlTokenTypes.INFIX_OPERATOR, "@!"));
        doTest("^!", token(OCamlTokenTypes.INFIX_OPERATOR, "^!"));
        doTest("|!", token(OCamlTokenTypes.INFIX_OPERATOR, "|!"));
        doTest("&!", token(OCamlTokenTypes.INFIX_OPERATOR, "&!"));
        doTest("+!", token(OCamlTokenTypes.INFIX_OPERATOR, "+!"));
        doTest("-!", token(OCamlTokenTypes.INFIX_OPERATOR, "-!"));
        doTest("*!", token(OCamlTokenTypes.INFIX_OPERATOR, "*!"));
        doTest("/!", token(OCamlTokenTypes.INFIX_OPERATOR, "/!"));
        doTest("$!", token(OCamlTokenTypes.INFIX_OPERATOR, "$!"));
        doTest("%!", token(OCamlTokenTypes.INFIX_OPERATOR, "%!"));

        doTest("%!", token(OCamlTokenTypes.INFIX_OPERATOR, "%!"));
        doTest("%$", token(OCamlTokenTypes.INFIX_OPERATOR, "%$"));
        doTest("%%", token(OCamlTokenTypes.INFIX_OPERATOR, "%%"));
        doTest("%&", token(OCamlTokenTypes.INFIX_OPERATOR, "%&"));
        doTest("%*", token(OCamlTokenTypes.INFIX_OPERATOR, "%*"));
        doTest("%+", token(OCamlTokenTypes.INFIX_OPERATOR, "%+"));
        doTest("%-", token(OCamlTokenTypes.INFIX_OPERATOR, "%-"));
        doTest("%.", token(OCamlTokenTypes.INFIX_OPERATOR, "%."));
        doTest("%/", token(OCamlTokenTypes.INFIX_OPERATOR, "%/"));
        doTest("%:", token(OCamlTokenTypes.INFIX_OPERATOR, "%:"));
        doTest("%<", token(OCamlTokenTypes.INFIX_OPERATOR, "%<"));
        doTest("%=", token(OCamlTokenTypes.INFIX_OPERATOR, "%="));
        doTest("%>", token(OCamlTokenTypes.INFIX_OPERATOR, "%>"));
        doTest("%?", token(OCamlTokenTypes.INFIX_OPERATOR, "%?"));
        doTest("%@", token(OCamlTokenTypes.INFIX_OPERATOR, "%@"));
        doTest("%^", token(OCamlTokenTypes.INFIX_OPERATOR, "%^"));
        doTest("%|", token(OCamlTokenTypes.INFIX_OPERATOR, "%|"));
        doTest("%~", token(OCamlTokenTypes.INFIX_OPERATOR, "%~"));

        doTest("&&", token(OCamlTokenTypes.AMP_AMP, Keywords.AMP_AMP));
        doTest("-.", token(OCamlTokenTypes.MINUS_DOT, Keywords.MINUS_DOT));
        doTest("<-", token(OCamlTokenTypes.LT_MINUS, Keywords.LT_MINUS));
        doTest("||", token(OCamlTokenTypes.VBAR_VBAR, Keywords.VBAR_VBAR));
        doTest("<<", token(OCamlTokenTypes.LT_LT, Keywords.LT_LT));
        doTest("<:", token(OCamlTokenTypes.LT_COLON, Keywords.LT_COLON));
        doTest(">>", token(OCamlTokenTypes.GT_GT, Keywords.GT_GT));
        doTest("$$", token(OCamlTokenTypes.DOLLAR_DOLLAR, Keywords.DOLLAR_DOLLAR));
        doTest("$:", token(OCamlTokenTypes.DOLLAR_COLON, Keywords.DOLLAR_COLON));
        doTest("or", token(OCamlTokenTypes.OR_KEYWORD, Keywords.OR_KEYWORD));
        doTest(":=", token(OCamlTokenTypes.COLON_EQ, Keywords.COLON_EQ));
        doTest("mod", token(OCamlTokenTypes.MOD_KEYWORD, Keywords.MOD_KEYWORD));
        doTest("land", token(OCamlTokenTypes.LAND_KEYWORD, Keywords.LAND_KEYWORD));
        doTest("lor", token(OCamlTokenTypes.LOR_KEYWORD, Keywords.LOR_KEYWORD));
        doTest("lxor", token(OCamlTokenTypes.LXOR_KEYWORD, Keywords.LXOR_KEYWORD));
        doTest("lsl", token(OCamlTokenTypes.LSL_KEYWORD, Keywords.LSL_KEYWORD));
        doTest("lsr", token(OCamlTokenTypes.LSR_KEYWORD, Keywords.LSR_KEYWORD));
        doTest("asr", token(OCamlTokenTypes.ASR_KEYWORD, Keywords.ASR_KEYWORD));
    }

    public void testPrefixSymbol() {
        assertTrue(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.PREFIX_OPERATOR));
        assertFalse(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.QUEST));
        assertFalse(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.TILDE));
        assertTrue(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.NOT_EQ));
        assertTrue(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.QUEST_QUEST));

        doTest("!", token(OCamlTokenTypes.PREFIX_OPERATOR, "!"));
        doTest("?", token(OCamlTokenTypes.QUEST, Keywords.QUEST));
        doTest("~", token(OCamlTokenTypes.TILDE, Keywords.TILDE));

        doTest("!!", token(OCamlTokenTypes.PREFIX_OPERATOR, "!!"));
        doTest("?!", token(OCamlTokenTypes.PREFIX_OPERATOR, "?!"));
        doTest("~!", token(OCamlTokenTypes.PREFIX_OPERATOR, "~!"));

        doTest("~!", token(OCamlTokenTypes.PREFIX_OPERATOR, "~!"));
        doTest("~$", token(OCamlTokenTypes.PREFIX_OPERATOR, "~$"));
        doTest("~%", token(OCamlTokenTypes.PREFIX_OPERATOR, "~%"));
        doTest("~&", token(OCamlTokenTypes.PREFIX_OPERATOR, "~&"));
        doTest("~*", token(OCamlTokenTypes.PREFIX_OPERATOR, "~*"));
        doTest("~+", token(OCamlTokenTypes.PREFIX_OPERATOR, "~+"));
        doTest("~-", token(OCamlTokenTypes.PREFIX_OPERATOR, "~-"));
        doTest("~.", token(OCamlTokenTypes.PREFIX_OPERATOR, "~."));
        doTest("~/", token(OCamlTokenTypes.PREFIX_OPERATOR, "~/"));
        doTest("~:", token(OCamlTokenTypes.PREFIX_OPERATOR, "~:"));
        doTest("~<", token(OCamlTokenTypes.PREFIX_OPERATOR, "~<"));
        doTest("~=", token(OCamlTokenTypes.PREFIX_OPERATOR, "~="));
        doTest("~>", token(OCamlTokenTypes.PREFIX_OPERATOR, "~>"));
        doTest("~?", token(OCamlTokenTypes.PREFIX_OPERATOR, "~?"));
        doTest("~@", token(OCamlTokenTypes.PREFIX_OPERATOR, "~@"));
        doTest("~^", token(OCamlTokenTypes.PREFIX_OPERATOR, "~^"));
        doTest("~|", token(OCamlTokenTypes.PREFIX_OPERATOR, "~|"));
        doTest("~~", token(OCamlTokenTypes.PREFIX_OPERATOR, "~~"));

        doTest("!=", token(OCamlTokenTypes.NOT_EQ, Keywords.NOT_EQ));
        doTest("??", token(OCamlTokenTypes.QUEST_QUEST, Keywords.QUEST_QUEST));
    }

    public void testLineNumberDirective() {
        doTest("#2", token(OCamlTokenTypes.WHITE_SPACE, "#2"));
        doTest("# 2 ", token(OCamlTokenTypes.WHITE_SPACE, "# 2 "));
        doTest("# 2 \"file name\"", token(OCamlTokenTypes.WHITE_SPACE, "# 2 \"file name\""));
    }

    public void testKeyword() {
        doTest("and", token(OCamlTokenTypes.AND_KEYWORD, Keywords.AND_KEYWORD));
        doTest("as", token(OCamlTokenTypes.AS_KEYWORD, Keywords.AS_KEYWORD));
        doTest("assert", token(OCamlTokenTypes.ASSERT_KEYWORD, Keywords.ASSERT_KEYWORD));
        doTest("asr", token(OCamlTokenTypes.ASR_KEYWORD, Keywords.ASR_KEYWORD));
        doTest("begin", token(OCamlTokenTypes.BEGIN_KEYWORD, Keywords.BEGIN_KEYWORD));
        doTest("class", token(OCamlTokenTypes.CLASS_KEYWORD, Keywords.CLASS_KEYWORD));
        doTest("constraint", token(OCamlTokenTypes.CONSTRAINT_KEYWORD, Keywords.CONSTRAINT_KEYWORD));
        doTest("do", token(OCamlTokenTypes.DO_KEYWORD, Keywords.DO_KEYWORD));
        doTest("done", token(OCamlTokenTypes.DONE_KEYWORD, Keywords.DONE_KEYWORD));
        doTest("downto", token(OCamlTokenTypes.DOWNTO_KEYWORD, Keywords.DOWNTO_KEYWORD));
        doTest("else", token(OCamlTokenTypes.ELSE_KEYWORD, Keywords.ELSE_KEYWORD));
        doTest("end", token(OCamlTokenTypes.END_KEYWORD, Keywords.END_KEYWORD));
        doTest("exception", token(OCamlTokenTypes.EXCEPTION_KEYWORD, Keywords.EXCEPTION_KEYWORD));
        doTest("external", token(OCamlTokenTypes.EXTERNAL_KEYWORD, Keywords.EXTERNAL_KEYWORD));
        doTest("false", token(OCamlTokenTypes.FALSE_KEYWORD, Keywords.FALSE_KEYWORD));
        doTest("for", token(OCamlTokenTypes.FOR_KEYWORD, Keywords.FOR_KEYWORD));
        doTest("fun", token(OCamlTokenTypes.FUN_KEYWORD, Keywords.FUN_KEYWORD));
        doTest("function", token(OCamlTokenTypes.FUNCTION_KEYWORD, Keywords.FUNCTION_KEYWORD));
        doTest("functor", token(OCamlTokenTypes.FUNCTOR_KEYWORD, Keywords.FUNCTOR_KEYWORD));
        doTest("if", token(OCamlTokenTypes.IF_KEYWORD, Keywords.IF_KEYWORD));
        doTest("in", token(OCamlTokenTypes.IN_KEYWORD, Keywords.IN_KEYWORD));
        doTest("include", token(OCamlTokenTypes.INCLUDE_KEYWORD, Keywords.INCLUDE_KEYWORD));
        doTest("inherit", token(OCamlTokenTypes.INHERIT_KEYWORD, Keywords.INHERIT_KEYWORD));
        doTest("initializer", token(OCamlTokenTypes.INITIALIZER_KEYWORD, Keywords.INITIALIZER_KEYWORD));
        doTest("land", token(OCamlTokenTypes.LAND_KEYWORD, Keywords.LAND_KEYWORD));
        doTest("lazy", token(OCamlTokenTypes.LAZY_KEYWORD, Keywords.LAZY_KEYWORD));
        doTest("let", token(OCamlTokenTypes.LET_KEYWORD, Keywords.LET_KEYWORD));
        doTest("lor", token(OCamlTokenTypes.LOR_KEYWORD, Keywords.LOR_KEYWORD));
        doTest("lsl", token(OCamlTokenTypes.LSL_KEYWORD, Keywords.LSL_KEYWORD));
        doTest("lsr", token(OCamlTokenTypes.LSR_KEYWORD, Keywords.LSR_KEYWORD));
        doTest("lxor", token(OCamlTokenTypes.LXOR_KEYWORD, Keywords.LXOR_KEYWORD));
        doTest("match", token(OCamlTokenTypes.MATCH_KEYWORD, Keywords.MATCH_KEYWORD));
        doTest("method", token(OCamlTokenTypes.METHOD_KEYWORD, Keywords.METHOD_KEYWORD));
        doTest("mod", token(OCamlTokenTypes.MOD_KEYWORD, Keywords.MOD_KEYWORD));
        doTest("module", token(OCamlTokenTypes.MODULE_KEYWORD, Keywords.MODULE_KEYWORD));
        doTest("mutable", token(OCamlTokenTypes.MUTABLE_KEYWORD, Keywords.MUTABLE_KEYWORD));
        doTest("new", token(OCamlTokenTypes.NEW_KEYWORD, Keywords.NEW_KEYWORD));
        doTest("object", token(OCamlTokenTypes.OBJECT_KEYWORD, Keywords.OBJECT_KEYWORD));
        doTest("of", token(OCamlTokenTypes.OF_KEYWORD, Keywords.OF_KEYWORD));
        doTest("open", token(OCamlTokenTypes.OPEN_KEYWORD, Keywords.OPEN_KEYWORD));
        doTest("or", token(OCamlTokenTypes.OR_KEYWORD, Keywords.OR_KEYWORD));
        doTest("private", token(OCamlTokenTypes.PRIVATE_KEYWORD, Keywords.PRIVATE_KEYWORD));
        doTest("rec", token(OCamlTokenTypes.REC_KEYWORD, Keywords.REC_KEYWORD));
        doTest("sig", token(OCamlTokenTypes.SIG_KEYWORD, Keywords.SIG_KEYWORD));
        doTest("struct", token(OCamlTokenTypes.STRUCT_KEYWORD, Keywords.STRUCT_KEYWORD));
        doTest("then", token(OCamlTokenTypes.THEN_KEYWORD, Keywords.THEN_KEYWORD));
        doTest("to", token(OCamlTokenTypes.TO_KEYWORD, Keywords.TO_KEYWORD));
        doTest("true", token(OCamlTokenTypes.TRUE_KEYWORD, Keywords.TRUE_KEYWORD));
        doTest("try", token(OCamlTokenTypes.TRY_KEYWORD, Keywords.TRY_KEYWORD));
        doTest("type", token(OCamlTokenTypes.TYPE_KEYWORD, Keywords.TYPE_KEYWORD));
        doTest("val", token(OCamlTokenTypes.VAL_KEYWORD, Keywords.VAL_KEYWORD));
        doTest("virtual", token(OCamlTokenTypes.VIRTUAL_KEYWORD, Keywords.VIRTUAL_KEYWORD));
        doTest("when", token(OCamlTokenTypes.WHEN_KEYWORD, Keywords.WHEN_KEYWORD));
        doTest("while", token(OCamlTokenTypes.WHILE_KEYWORD, Keywords.WHILE_KEYWORD));
        doTest("with", token(OCamlTokenTypes.WITH_KEYWORD, Keywords.WITH_KEYWORD));

        doTest("!=", token(OCamlTokenTypes.NOT_EQ, Keywords.NOT_EQ));
        doTest("#", token(OCamlTokenTypes.HASH, Keywords.HASH));
        doTest("&", token(OCamlTokenTypes.AMP, Keywords.AMP));
        doTest("&&", token(OCamlTokenTypes.AMP_AMP, Keywords.AMP_AMP));
//      "'" is tested in testQuote methods
        doTest("(", token(OCamlTokenTypes.LPAR, Keywords.LPAR));
        doTest(")", token(OCamlTokenTypes.RPAR, Keywords.RPAR));
        doTest("*", token(OCamlTokenTypes.MULT, Keywords.MULT));
        doTest("+", token(OCamlTokenTypes.PLUS, Keywords.PLUS));
        doTest(",", token(OCamlTokenTypes.COMMA, Keywords.COMMA));
        doTest("-", token(OCamlTokenTypes.MINUS, Keywords.MINUS));
        doTest("-.", token(OCamlTokenTypes.MINUS_DOT, Keywords.MINUS_DOT));
        doTest("->", token(OCamlTokenTypes.MINUS_GT, Keywords.MINUS_GT));
        doTest(".", token(OCamlTokenTypes.DOT, Keywords.DOT));
        doTest("..", token(OCamlTokenTypes.DOT_DOT, Keywords.DOT_DOT));
        doTest(":", token(OCamlTokenTypes.COLON, Keywords.COLON));
        doTest("::", token(OCamlTokenTypes.COLON_COLON, Keywords.COLON_COLON));
        doTest(":=", token(OCamlTokenTypes.COLON_EQ, Keywords.COLON_EQ));
        doTest(":>", token(OCamlTokenTypes.COLON_GT, Keywords.COLON_GT));
        doTest(";", token(OCamlTokenTypes.SEMICOLON, Keywords.SEMICOLON));
        doTest(";;", token(OCamlTokenTypes.SEMICOLON_SEMICOLON, Keywords.SEMICOLON_SEMICOLON));
        doTest("<", token(OCamlTokenTypes.LT, Keywords.LT));
        doTest("<-", token(OCamlTokenTypes.LT_MINUS, Keywords.LT_MINUS));
        doTest("=", token(OCamlTokenTypes.EQ, Keywords.EQ));
        doTest(">", token(OCamlTokenTypes.GT, Keywords.GT));
        doTest(">]", token(OCamlTokenTypes.GT_RBRACKET, Keywords.GT_RBRACKET));
        doTest(">}", token(OCamlTokenTypes.GT_RBRACE, Keywords.GT_RBRACE));
        doTest("?", token(OCamlTokenTypes.QUEST, Keywords.QUEST));
        doTest("??", token(OCamlTokenTypes.QUEST_QUEST, Keywords.QUEST_QUEST));
        doTest("[", token(OCamlTokenTypes.LBRACKET, Keywords.LBRACKET));
        doTest("[<", token(OCamlTokenTypes.LBRACKET_LT, Keywords.LBRACKET_LT));
        doTest("[>", token(OCamlTokenTypes.LBRACKET_GT, Keywords.LBRACKET_GT));
        doTest("[|", token(OCamlTokenTypes.LBRACKET_VBAR, Keywords.LBRACKET_VBAR));
        doTest("]", token(OCamlTokenTypes.RBRACKET, Keywords.RBRACKET));
        doTest("_", token(OCamlTokenTypes.UNDERSCORE, Keywords.UNDERSCORE));
        doTest("`", token(OCamlTokenTypes.ACCENT, Keywords.ACCENT));
        doTest("{", token(OCamlTokenTypes.LBRACE, Keywords.LBRACE));
        doTest("{<", token(OCamlTokenTypes.LBRACE_LT, Keywords.LBRACE_LT));
        doTest("|", token(OCamlTokenTypes.VBAR, Keywords.VBAR));
        doTest("||", token(OCamlTokenTypes.VBAR_VBAR, Keywords.VBAR_VBAR));
        doTest("|]", token(OCamlTokenTypes.VBAR_RBRACKET, Keywords.VBAR_RBRACKET));
        doTest("}", token(OCamlTokenTypes.RBRACE, Keywords.RBRACE));
        doTest("~", token(OCamlTokenTypes.TILDE, Keywords.TILDE));

        /* Camlp4 extensions compatibility { */

        doTest("parser", token(OCamlTokenTypes.PARSER_KEYWORD, Keywords.PARSER_KEYWORD));
        doTest("<<", token(OCamlTokenTypes.LT_LT, Keywords.LT_LT));
        doTest("<:", token(OCamlTokenTypes.LT_COLON, Keywords.LT_COLON));
        doTest(">>", token(OCamlTokenTypes.GT_GT, Keywords.GT_GT));
        doTest("$", token(OCamlTokenTypes.DOLLAR, Keywords.DOLLAR));
        doTest("$$", token(OCamlTokenTypes.DOLLAR_DOLLAR, Keywords.DOLLAR_DOLLAR));
        doTest("$:", token(OCamlTokenTypes.DOLLAR_COLON, Keywords.DOLLAR_COLON));

        /* } */
    }
}
