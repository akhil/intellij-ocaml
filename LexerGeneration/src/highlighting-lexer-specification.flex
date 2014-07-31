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

package manuylov.maxim.ocaml.lang.lexer.flex;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;

import java.util.Map;
import java.util.HashMap;
%%

%public
%class OCamlHighlightingFlexLexer
%implements FlexLexer

%function advance
%type IElementType

%unicode            

%{
  private int myNestedCommentsDepth;

  private int mySavedTokenStart;

  private void saveNextTokenStart() {
    mySavedTokenStart = zzStartRead + yylength();
  }

  private void restoreSavedTokenStart() {
    zzStartRead = mySavedTokenStart;
  }
%}

LineTerminator         = \r|\n|\r\n
Blank                  = [ \t]
WhiteSpace             = {LineTerminator} | {Blank} | [\f]

CommentBegin           = "(*"
CommentEnd             = "*)"

Digit                  = [0-9]
Digit_                 = {Digit} | [_]
HexDigit               = [0-9a-fA-F]
HexDigit_              = {HexDigit} | [_]
OctDigit               = [0-7]
OctDigit_              = {OctDigit} | [_]
BinDigit               = [01]
BinDigit_              = {BinDigit} | [_]

IntegerLiteralPostfix  = [lLn]

IntegerLiteral         = (
                           ({Digit} {Digit_}*)
                         | (("0x" | "0X") {HexDigit} {HexDigit_}*)
                         | (("0o" | "0O") {OctDigit} {OctDigit_}*)
                         | (("0b" | "0B") {BinDigit} {BinDigit_}*)
                         ) {IntegerLiteralPostfix}?

FloatLiteral           = {Digit} {Digit_}* ("." {Digit_}*)? ([eE] [+-]? {Digit} {Digit_}*)?

EscapeSequence         = ("\\" [\\\"'ntbr ]) | ("\\" {Digit}{3}) | ("\\x" {HexDigit}{2})
StringLineFeed         = [\\] {LineTerminator} {Blank}*

RegularChar            = [^\\']
RegularStrChar         = [^\\\"]

RegularStrCharsSequence = {RegularStrChar}+
EscapeSequencesSequence = ({EscapeSequence} | {StringLineFeed})+

UpperCaseLetter        = [A-Z]
LowerCaseLetter        = [a-z_]
Letter                 = {UpperCaseLetter} | {LowerCaseLetter}

NotFirstIdentifierChar = {Letter} | {Digit} | [']
UCFirstCharIdentifier  = {UpperCaseLetter} {NotFirstIdentifierChar}*
LCFirstCharIdentifier  = {LowerCaseLetter} {NotFirstIdentifierChar}*

OperatorChar           = [\!\$%&\*\+-\.\/:\<=\>\?@\^\|\~]
InfixSymbol            = ([=\<\>@\^\|\&\/\$%] | [+] | [-] | [*]) {OperatorChar}*
PrefixSymbol           = [\!\?\~] {OperatorChar}*

LinenumDirective       = "#" {WhiteSpace}* {Digit}+ {WhiteSpace}* ([\"] ({RegularStrChar} | {EscapeSequence})* [\"])?

Any                    = .|\n

%state COMMENT, BEFORE_COMMENT_BEGIN, BEFORE_COMMENT_END, CHARACTER_TO_BE_READ, CHARACTER_HAS_BEEN_READ, STRING

%%

<YYINITIAL> {
  "and"                     { return OCamlTokenTypes.AND_KEYWORD; }
  "as"                      { return OCamlTokenTypes.AS_KEYWORD; }
  "assert"                  { return OCamlTokenTypes.ASSERT_KEYWORD; }
  "asr"                     { return OCamlTokenTypes.ASR_KEYWORD; }
  "begin"                   { return OCamlTokenTypes.BEGIN_KEYWORD; }
  "class"                   { return OCamlTokenTypes.CLASS_KEYWORD; }
  "constraint"              { return OCamlTokenTypes.CONSTRAINT_KEYWORD; }
  "do"                      { return OCamlTokenTypes.DO_KEYWORD; }
  "done"                    { return OCamlTokenTypes.DONE_KEYWORD; }
  "downto"                  { return OCamlTokenTypes.DOWNTO_KEYWORD; }
  "else"                    { return OCamlTokenTypes.ELSE_KEYWORD; }
  "end"                     { return OCamlTokenTypes.END_KEYWORD; }
  "exception"               { return OCamlTokenTypes.EXCEPTION_KEYWORD; }
  "external"                { return OCamlTokenTypes.EXTERNAL_KEYWORD; }
  "false"                   { return OCamlTokenTypes.FALSE_KEYWORD; }
  "for"                     { return OCamlTokenTypes.FOR_KEYWORD; }
  "fun"                     { return OCamlTokenTypes.FUN_KEYWORD; }
  "function"                { return OCamlTokenTypes.FUNCTION_KEYWORD; }
  "functor"                 { return OCamlTokenTypes.FUNCTOR_KEYWORD; }
  "if"                      { return OCamlTokenTypes.IF_KEYWORD; }
  "in"                      { return OCamlTokenTypes.IN_KEYWORD; }
  "include"                 { return OCamlTokenTypes.INCLUDE_KEYWORD; }
  "inherit"                 { return OCamlTokenTypes.INHERIT_KEYWORD; }
  "initializer"             { return OCamlTokenTypes.INITIALIZER_KEYWORD; }
  "land"                    { return OCamlTokenTypes.LAND_KEYWORD; }
  "lazy"                    { return OCamlTokenTypes.LAZY_KEYWORD; }
  "let"                     { return OCamlTokenTypes.LET_KEYWORD; }
  "lor"                     { return OCamlTokenTypes.LOR_KEYWORD; }
  "lsl"                     { return OCamlTokenTypes.LSL_KEYWORD; }
  "lsr"                     { return OCamlTokenTypes.LSR_KEYWORD; }
  "lxor"                    { return OCamlTokenTypes.LXOR_KEYWORD; }
  "match"                   { return OCamlTokenTypes.MATCH_KEYWORD; }
  "method"                  { return OCamlTokenTypes.METHOD_KEYWORD; }
  "mod"                     { return OCamlTokenTypes.MOD_KEYWORD; }
  "module"                  { return OCamlTokenTypes.MODULE_KEYWORD; }
  "mutable"                 { return OCamlTokenTypes.MUTABLE_KEYWORD; }
  "new"                     { return OCamlTokenTypes.NEW_KEYWORD; }
  "object"                  { return OCamlTokenTypes.OBJECT_KEYWORD; }
  "of"                      { return OCamlTokenTypes.OF_KEYWORD; }
  "open"                    { return OCamlTokenTypes.OPEN_KEYWORD; }
  "or"                      { return OCamlTokenTypes.OR_KEYWORD; }
  "private"                 { return OCamlTokenTypes.PRIVATE_KEYWORD; }
  "rec"                     { return OCamlTokenTypes.REC_KEYWORD; }
  "sig"                     { return OCamlTokenTypes.SIG_KEYWORD; }
  "struct"                  { return OCamlTokenTypes.STRUCT_KEYWORD; }
  "then"                    { return OCamlTokenTypes.THEN_KEYWORD; }
  "to"                      { return OCamlTokenTypes.TO_KEYWORD; }
  "true"                    { return OCamlTokenTypes.TRUE_KEYWORD; }
  "try"                     { return OCamlTokenTypes.TRY_KEYWORD; }
  "type"                    { return OCamlTokenTypes.TYPE_KEYWORD; }
  "val"                     { return OCamlTokenTypes.VAL_KEYWORD; }
  "virtual"                 { return OCamlTokenTypes.VIRTUAL_KEYWORD; }
  "when"                    { return OCamlTokenTypes.WHEN_KEYWORD; }
  "while"                   { return OCamlTokenTypes.WHILE_KEYWORD; }
  "with"                    { return OCamlTokenTypes.WITH_KEYWORD; }

  "!="                      { return OCamlTokenTypes.NOT_EQ; }
  "#"                       { return OCamlTokenTypes.HASH; }
  "&"                       { return OCamlTokenTypes.AMP; }
  "&&"                      { return OCamlTokenTypes.AMP_AMP; }
  "("                       { return OCamlTokenTypes.LPAR; }
  ")"                       { return OCamlTokenTypes.RPAR; }
  "*"                       { return OCamlTokenTypes.MULT; }
  "+"                       { return OCamlTokenTypes.PLUS; }
  ","                       { return OCamlTokenTypes.COMMA; }
  "-"                       { return OCamlTokenTypes.MINUS; }
  "-."                      { return OCamlTokenTypes.MINUS_DOT; }
  "->"                      { return OCamlTokenTypes.MINUS_GT; }
  "."                       { return OCamlTokenTypes.DOT; }
  ".."                      { return OCamlTokenTypes.DOT_DOT; }
  ":"                       { return OCamlTokenTypes.COLON; }
  "::"                      { return OCamlTokenTypes.COLON_COLON; }
  ":="                      { return OCamlTokenTypes.COLON_EQ; }
  ":>"                      { return OCamlTokenTypes.COLON_GT; }
  ";"                       { return OCamlTokenTypes.SEMICOLON; }
  ";;"                      { return OCamlTokenTypes.SEMICOLON_SEMICOLON; }
  "<"                       { return OCamlTokenTypes.LT; }
  "<-"                      { return OCamlTokenTypes.LT_MINUS; }
  "="                       { return OCamlTokenTypes.EQ; }
  ">"                       { return OCamlTokenTypes.GT; }
  ">]"                      { return OCamlTokenTypes.GT_RBRACKET; }
  ">}"                      { return OCamlTokenTypes.GT_RBRACE; }
  "?"                       { return OCamlTokenTypes.QUEST; }
  "??"                      { return OCamlTokenTypes.QUEST_QUEST; }
  "["                       { return OCamlTokenTypes.LBRACKET; }
  "[<"                      { return OCamlTokenTypes.LBRACKET_LT; }
  "[>"                      { return OCamlTokenTypes.LBRACKET_GT; }
  "[|"                      { return OCamlTokenTypes.LBRACKET_VBAR; }
  "]"                       { return OCamlTokenTypes.RBRACKET; }
  "_"                       { return OCamlTokenTypes.UNDERSCORE; }
  "`"                       { return OCamlTokenTypes.ACCENT; }
  "{"                       { return OCamlTokenTypes.LBRACE; }
  "{<"                      { return OCamlTokenTypes.LBRACE_LT; }
  "|"                       { return OCamlTokenTypes.VBAR; }
  "||"                      { return OCamlTokenTypes.VBAR_VBAR; }
  "|]"                      { return OCamlTokenTypes.VBAR_RBRACKET; }
  "}"                       { return OCamlTokenTypes.RBRACE; }
  "~"                       { return OCamlTokenTypes.TILDE; }

  /* Camlp4 extensions compatibility { */

  "parser"                  { return OCamlTokenTypes.PARSER_KEYWORD; }
  "<<"                      { return OCamlTokenTypes.LT_LT; }
  "<:"                      { return OCamlTokenTypes.LT_COLON; }
  ">>"                      { return OCamlTokenTypes.GT_GT; }
  "$"                       { return OCamlTokenTypes.DOLLAR; }
  "$$"                      { return OCamlTokenTypes.DOLLAR_DOLLAR; }
  "$:"                      { return OCamlTokenTypes.DOLLAR_COLON; }

  /* } */

  {UCFirstCharIdentifier}   { return OCamlTokenTypes.UCFC_IDENTIFIER; }
  {LCFirstCharIdentifier}   { return OCamlTokenTypes.LCFC_IDENTIFIER; }

  {IntegerLiteral}          { return OCamlTokenTypes.INTEGER_LITERAL; }
  {FloatLiteral}            { return OCamlTokenTypes.FLOAT_LITERAL; }

  [\"]                      {
                              yybegin(STRING);
                              return OCamlTokenTypes.OPENING_DOUBLE_QUOTE;
                            }

  [\']                      {
                              yybegin(CHARACTER_TO_BE_READ);
                              return OCamlTokenTypes.OPENING_QUOTE;
                            }

  {InfixSymbol}             { return OCamlTokenTypes.INFIX_OPERATOR; }
  {PrefixSymbol}            { return OCamlTokenTypes.PREFIX_OPERATOR; }

  {CommentBegin}            {
                              myNestedCommentsDepth = 1;
                              saveNextTokenStart();
                              yybegin(COMMENT);
                              return OCamlTokenTypes.COMMENT_BEGIN;
                            }

  {WhiteSpace}              { return OCamlTokenTypes.WHITE_SPACE; }
  {LinenumDirective}        { return OCamlTokenTypes.WHITE_SPACE; }
}

<COMMENT> {
  {CommentBegin}            {
                              yypushback(yylength());
                              restoreSavedTokenStart();
                              yybegin(BEFORE_COMMENT_BEGIN);
                              if (yylength() != 0) {
                                return OCamlTokenTypes.COMMENT;
                              }
                            }

  {CommentEnd}              {
                              yypushback(yylength());
                              restoreSavedTokenStart();
                              yybegin(BEFORE_COMMENT_END);
                              if (yylength() != 0) {
                                return OCamlTokenTypes.COMMENT;
                              }
                            }

  <<EOF>>                   {
                              restoreSavedTokenStart();
                              yybegin(YYINITIAL);
                              if (yylength() != 0) {
                                return OCamlTokenTypes.COMMENT;
                              }
                            }

  {Any}                     { /* ignore */ }
}

<BEFORE_COMMENT_BEGIN> {
  {CommentBegin}            {
                              myNestedCommentsDepth++;
                              saveNextTokenStart();
                              yybegin(COMMENT);
                              return OCamlTokenTypes.COMMENT_BEGIN;
                            }
}

<BEFORE_COMMENT_END> {
  {CommentEnd}              {
                              myNestedCommentsDepth--;
                              if (myNestedCommentsDepth == 0) {
                                yybegin(YYINITIAL);
                              }
                              else {
                                saveNextTokenStart();
                                yybegin(COMMENT);
                              }
                              return OCamlTokenTypes.COMMENT_END;
                            }
}

<STRING> {
  {RegularStrCharsSequence} { return OCamlTokenTypes.REGULAR_CHARS; }
  {EscapeSequencesSequence} { return OCamlTokenTypes.ESCAPE_SEQUENCES; }

  [\"]                      {
                              yybegin(YYINITIAL);
                              return OCamlTokenTypes.CLOSING_DOUBLE_QUOTE;
                            }
}

<CHARACTER_TO_BE_READ> {
  {RegularChar} [']         {
                              yypushback(1);
                              yybegin(CHARACTER_HAS_BEEN_READ);
                              return OCamlTokenTypes.REGULAR_CHARS;
                            }

  {EscapeSequence} [']      {
                              yypushback(1);
                              yybegin(CHARACTER_HAS_BEEN_READ);
                              return OCamlTokenTypes.ESCAPE_SEQUENCES;
                            }

  [']                       {
                              yybegin(YYINITIAL);
                              return OCamlTokenTypes.CLOSING_QUOTE;
                            }

  {Any}                     {
                              yypushback(1);
                              yybegin(YYINITIAL);
                            }
}       

<CHARACTER_HAS_BEEN_READ> {
  [']                       {
                              yybegin(YYINITIAL);
                              return OCamlTokenTypes.CLOSING_QUOTE;
                            }
}

<<EOF>>                     {
                              yybegin(YYINITIAL);
                              return null;
                            }

{Any}                       { return OCamlTokenTypes.BAD_CHARACTER; }
