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

package manuylov.maxim.ocaml.lang.feature.completion;

import com.intellij.codeInsight.completion.CompletionType;
import manuylov.maxim.ocaml.lang.feature.completion.testCase.CompletionTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static manuylov.maxim.ocaml.lang.Keywords.*;

/**
 * @author Maxim.Manuylov
 *         Date: 20.05.2010
 */
@Test
public class KeywordsCompletionTest extends CompletionTestCase {
    public void testAndKeyword() throws Exception {
        setVariants(AND_KEYWORD);

        doTest(1, "let a = 0 }{", true);
        doTest(2, "let a = }{", false);
        doTest(3, "let a = b }{ c", true);
        doTest(4, "let a = }{ c", false);
        doTest(5, "let a = b and f = g }{", true);
        doTest(6, "let a = b and f = }{", false);
        doTest(7, "let a = b and f = g }{ c", true);
        doTest(8, "let a = b and f = }{ c", false);
        doTest(9, "let a = 1 }{ in a", true);
        doTest(10, "let a = }{ in a", false);
        doTest(11, "class s = object end }{", true);
        doTest(12, "class s = object }{", false);
        doTest(13, "class type s = object end }{", true);
        doTest(14, "class type s = object }{", false);
        doTest(15, "type s = One }{", true);
        doTest(16, "type s = }{", false);
        doTest(17, "module type S = sig end with module M = M }{", true);
        doTest(18, "module type S = sig end with module M = }{", false);
        doTest(19, "let a = Constr }{", true);
        doTest(20, "module type M = }{", false);
        doTest(21, "module type M = }{ ", false);
        doTest(22, "module M = }{", false);
        doTest(23, "module M = }{ ", false);
    }

    public void testAsKeyword() throws Exception {
        setVariants(AS_KEYWORD);

        doTest(1, "type s = _ }{", true);
        doTest(2, "type s = }{", false);
        doTest(3, "match 1 with _ }{", true);
        doTest(4, "match 1 with Constr }{", true);
        doTest(5, "match 1 with }{", false);
        doTest(6, "class s = object inherit ss }{", true);
        doTest(7, "class s = object inherit }{", false);
        doTest(8, "class s = object inherit ss as v }{", false);
        doTest(9, "class s = object inherit ss as }{", false);
        doTest(10, "module type M = }{", false);
        doTest(11, "module type M = }{ ", false);
        doTest(12, "module M = }{", false);
        doTest(13, "module M = }{ ", false);
    }
    
    public void testExpressionStart() throws Exception {
        setVariants(BEGIN_KEYWORD, IF_KEYWORD, WHILE_KEYWORD, FOR_KEYWORD, MATCH_KEYWORD, FUNCTION_KEYWORD,
            FUN_KEYWORD, TRY_KEYWORD, NEW_KEYWORD, ASSERT_KEYWORD, LAZY_KEYWORD, TRUE_KEYWORD, FALSE_KEYWORD);

        doTest(1, "let a = }{", true);
        doTest(2, "}{", true);
        doTest(3, "module }{", false);
        doTest(4, "type t = }{", false);
        doTest(5, "module type M = }{", false);
        doTest(6, "module type M = }{ ", false);
        doTest(7, "module M = }{", false);
        doTest(8, "module M = }{ ", false);
    }

    public void testStatementStart() throws Exception {
        setVariants(EXTERNAL_KEYWORD, EXCEPTION_KEYWORD, CLASS_KEYWORD, MODULE_KEYWORD, OPEN_KEYWORD, INCLUDE_KEYWORD);

        doTest(1, "let }{", false);
        doTest(2, "}{", true);
        doTest(3, ";; }{", true);
        doTest(4, "let a = 0;; }{", true);
        doTest(5, "module M = struct }{", true);
        doTest(6, "module M = struct ;; }{", true);
        doTest(7, "module M = struct let a = 0;; }{", true);
        doTest(8, "module type M = sig }{", true);
        doTest(9, "module type M = sig ;; }{", true);
        doTest(10, "module type M = sig val a : int;; }{", true);
        doTest(11, "module type M = }{", false);
        doTest(12, "module type M = }{ ", false);
        doTest(13, "module M = }{", false);
        doTest(14, "module M = }{ ", false);
    }

    public void testLetKeyword() throws Exception {
        setVariants(LET_KEYWORD);

        doTest(1, "}{", true);
        doTest(2, ";; }{", true);
        doTest(3, "let f = 0 }{", true);
        doTest(4, "module D = struct }{", true);
        doTest(5, "module D = struct ;; }{", true);
        doTest(6, "module D = struct let d = 0 }{", true);
        doTest(7, "2 + }{", true);
        doTest(8, "class s = }{", true);
        doTest(9, "let }{", false);
        doTest(10, "module type M = }{", false);
        doTest(11, "module type M = }{ ", false);
        doTest(12, "module M = }{", false);
        doTest(13, "module M = }{ ", false);
    }

    public void testRecKeyword() throws Exception {
        setVariants(REC_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let }{", true);
        doTest(3, "let rec }{", false);
    }

    public void testStructKeyword() throws Exception {
        setVariants(STRUCT_KEYWORD);

        doTest(1, "module M = }{", true);
        doTest(2, "module M = struct }{", false);
        doTest(3, "module M = functor (M1 : MT) -> }{", true);
        doTest(4, "module type M = }{", false);
        doTest(5, "}{", false);
    }

    public void testSigKeyword() throws Exception {
        setVariants(SIG_KEYWORD);

        doTest(1, "module type M = }{", true);
        doTest(2, "module type M = sig }{", false);
        doTest(3, "module type M = functor (M1 : MT) -> }{", true);
        doTest(4, "module M = }{", false);
        doTest(5, "}{", false);
    }

    public void testFunctorKeyword() throws Exception {
        setVariants(FUNCTOR_KEYWORD);

        doTest(1, "module M = }{", true);
        doTest(2, "module M = struct }{", false);
        doTest(3, "module M = functor (M1 : MT) -> }{", true);
        doTest(4, "}{", false);
        doTest(5, "module type M = }{", true);
        doTest(6, "module type M = sig }{", false);
        doTest(7, "module type M = functor (M1 : MT) -> }{", true);
        doTest(8, "}{", false);
    }

    public void testLazyKeyword() throws Exception {
        setVariants(LAZY_KEYWORD);

        doTest(1, "}{", true);
        doTest(2, "let g = }{", true);
        doTest(3, "match f with }{", true);
        doTest(4, "for }{", false);
    }

    public void testObjectKeyword() throws Exception {
        setVariants(OBJECT_KEYWORD);

        doTest(1, "}{", true);
        doTest(2, "let g = }{", true);
        doTest(3, "class c = }{", true);
        doTest(4, "class type c = }{", true);
        doTest(5, "class type c }{", false);
        doTest(6, "class c }{", false);
    }

    public void testInheritKeyword() throws Exception {
        setVariants(INHERIT_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class c = object }{", true);
        doTest(4, "class c = object (self) }{", true);
        doTest(5, "class c = object val s = 3 }{", true);
        doTest(6, "class type c = object }{", true);
        doTest(7, "class type c = object (ss) }{", true);
        doTest(8, "class type c = object val s : int }{", true);
        doTest(9, "class type c }{", false);
        doTest(10, "class c }{", false);
    }

    public void testInitializerKeyword() throws Exception {
        setVariants(INITIALIZER_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class c = object }{", true);
        doTest(4, "class c = object (self) }{", true);
        doTest(5, "class c = object val s = 3 }{", true);
        doTest(6, "class type c = object }{", false);
        doTest(7, "class type c = object (ss) }{", false);
        doTest(8, "class type c = object val s : int }{", false);
        doTest(9, "class type c }{", false);
        doTest(10, "class c }{", false);
    }

    public void testMethodKeyword() throws Exception {
        setVariants(METHOD_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class c = object }{", true);
        doTest(4, "class c = object (self) }{", true);
        doTest(5, "class c = object val s = 3 }{", true);
        doTest(6, "class type c = object }{", true);
        doTest(7, "class type c = object (ss) }{", true);
        doTest(8, "class type c = object val s : int }{", true);
        doTest(9, "class type c }{", false);
        doTest(10, "class c }{", false);
    }

    public void testValKeyword() throws Exception {
        setVariants(VAL_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class c = object }{", true);
        doTest(4, "class c = object (self) }{", true);
        doTest(5, "class c = object val s = 3 }{", true);
        doTest(6, "class type c = object }{", true);
        doTest(7, "class type c = object (ss) }{", true);
        doTest(8, "class type c = object val s : int }{", true);
        doTest(9, "class type c }{", false);
        doTest(10, "class c }{", false);
    }

    public void testConstraintKeyword() throws Exception {
        setVariants(CONSTRAINT_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class c = object }{", true);
        doTest(4, "class c = object (self) }{", true);
        doTest(5, "class c = object val s = 3 }{", true);
        doTest(6, "class type c = object }{", true);
        doTest(7, "class type c = object (ss) }{", true);
        doTest(8, "class type c = object val s : int }{", true);
        doTest(9, "class type c }{", false);
        doTest(10, "class c }{", false);
        doTest(11, "type t = {a:int} }{", true);
    }

    public void testTypeKeyword() throws Exception {
        setVariants(TYPE_KEYWORD);

        doTest(1, "let }{", false);
        doTest(2, "}{", true);
        doTest(3, ";; }{", true);
        doTest(4, "let a = 0;; }{", true);
        doTest(5, "module M = struct }{", true);
        doTest(6, "module M = struct ;; }{", true);
        doTest(7, "module M = struct let a = 0;; }{", true);
        doTest(8, "module type M = sig }{", true);
        doTest(9, "module type M = sig ;; }{", true);
        doTest(10, "module type M = sig val a : int;; }{", true);
        doTest(11, "module }{", true);
        doTest(12, "class }{", true);
    }

    public void testPrivateKeyword() throws Exception {
        setVariants(PRIVATE_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class c = object }{", false);
        doTest(4, "class c = object method }{", true);
        doTest(5, "class type c = object }{", false);
        doTest(6, "class type c = object method }{", true);
        doTest(7, "class type c }{", false);
        doTest(8, "class c }{", false);
    }

    public void testDoKeyword() throws Exception {
        setVariants(DO_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "while true }{", true);
        doTest(4, "while a > 0 }{", true);
        doTest(5, "for i = 1 to 12 }{", true);
        doTest(6, "for i = 1 downto -12 }{", true);
    }

    public void testDoneKeyword() throws Exception {
        setVariants(DONE_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "while true do 1 }{", true);
        doTest(4, "while a > 0 do 1 }{", true);
        doTest(5, "for i = 1 to 12 do 1 }{", true);
        doTest(6, "for i = 1 downto -12 do 1 }{", true);
    }

    public void testToDowntoKeywords() throws Exception {
        setVariants(TO_KEYWORD, DOWNTO_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "while true do 1 }{", false);
        doTest(4, "while a > 0 do 1 }{", false);
        doTest(5, "for i = 1 }{", true);
        doTest(6, "for i = 1 + 2 }{", true);
        doTest(7, "for i = 1 + }{", false);
    }

    public void testElseKeyword() throws Exception {
        setVariants(ELSE_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "if false then 12 else }{", false);
        doTest(4, "if false then 12 }{", true);
        doTest(5, "if false then }{", false);
        doTest(6, "if false }{", false);
        doTest(7, "if }{", false);
    }

    public void testThenKeyword() throws Exception {
        setVariants(THEN_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "if false then 12 else }{", false);
        doTest(4, "if false then 12 }{", false);
        doTest(5, "if false then }{", false);
        doTest(6, "if false }{", true);
        doTest(7, "if }{", false);
    }

    public void testEndKeyword() throws Exception {
        setVariants(END_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, ";; }{", false);
        doTest(3, "let g = }{", false);
        doTest(4, "let g = 1 }{", false);
        doTest(5, "let g = 1 ;; }{", false);
        doTest(6, "begin }{", false);
        doTest(7, "begin 1 }{", true);
        doTest(8, "object }{", true);
        doTest(9, "object (self) }{", true);
        doTest(10, "class type t = object (ss) }{", true);
        doTest(11, "object val s = 2 }{", true);
        doTest(12, "class type t = object val s : int }{", true);
        doTest(13, "module M = struct }{", true);
        doTest(14, "module M = struct ;; }{", true);
        doTest(15, "module M = struct let g = 0 }{", true);
        doTest(16, "module M = struct let g = 0 ;; }{", true);
        doTest(17, "module M = struct 12 }{", true);
        doTest(18, "module type M = sig }{", true);
        doTest(19, "module type M = sig ;; }{", true);
        doTest(20, "module type M = sig type t }{", true);
        doTest(21, "module type M = sig type t ;; }{", true);
    }

    public void testInKeyword() throws Exception {
        setVariants(IN_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "let g = 4 }{", true);
        doTest(4, "let g = 4 and r = }{", false);
        doTest(5, "let g = 4 and r = 2 }{", true);
    }

    public void testMutableKeyword() throws Exception {
        setVariants(MUTABLE_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = {}{", true);
        doTest(4, "type t = { }{", true);
        doTest(5, "type t = {a:int; }{", true);
        doTest(6, "type t = {a:int }{", false);
        doTest(7, "class c = object val }{", true);
        doTest(8, "class c = object method }{", false);
        doTest(9, "class type c = object val }{", true);
        doTest(10, "class type c = object method }{", false);
        doTest(11, "module type M = sig val }{", false);
    }

    public void testOfKeyword() throws Exception {
        setVariants(OF_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "Constr }{", false);
        doTest(4, "type t = One }{", true);
        doTest(5, "type t = One | Two }{", true);
        doTest(6, "exception Constr }{", true);
        doTest(7, "exception Constr = Constr1 }{", false);
        doTest(8, "let a = Constr }{", false);
        doTest(9, "match a with Constr }{", false);
        doTest(10, "type t = [ `tag }{", true);
        doTest(11, "type t = [> `tag }{", true);
        doTest(12, "type t = [< `tag }{", true);
    }

    public void testVirtualKeyword() throws Exception {
        setVariants(VIRTUAL_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "class }{", true);
        doTest(4, "class type }{", true);
        doTest(5, "module type M = sig class }{", true);
        doTest(6, "module type M = sig class type }{", true);
        doTest(7, "class c = object method }{", true);
        doTest(8, "class c = object method private }{", true);
        doTest(9, "class type c = object method }{", true);
        doTest(10, "class type c = object method private }{", true);
        doTest(11, "class c = object val }{", true);
        doTest(12, "class c = object val mutable }{", true);
        doTest(13, "class type c = object val }{", true);
        doTest(14, "class type c = object val mutable }{", true);
    }

    public void testWhenKeyword() throws Exception {
        setVariants(WHEN_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "match a with 1 }{", true);
        doTest(4, "match a with 1 -> }{", false);
        doTest(5, "try a with 1 }{", true);
        doTest(6, "try a with 1 -> }{", false);
        doTest(7, "fun a b c }{", true);
        doTest(8, "fun a b c -> }{", false);
    }

    public void testWithKeyword() throws Exception {
        setVariants(WITH_KEYWORD);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "module type T = sig end }{", true);
        doTest(4, "match 1 }{", true);
        doTest(5, "try 1 }{", true);
        doTest(6, "{a }{", true);
    }

    public void testMinusGtKeyword() throws Exception {
        setVariants(MINUS_GT);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = int }{", true);
        doTest(4, "module M = functor (M1 : MT) }{", true);
        doTest(5, "module type M = functor (M1 : MT) }{", true);
        doTest(6, "if false then }{", false);
        doTest(7, "if false }{", false);
        doTest(8, "if }{", false);
        doTest(9, "match a with 1 }{", true);
        doTest(10, "match a with 1 when true }{", true);
        doTest(11, "try a with 1 }{", true);
        doTest(12, "try a with 1 when true }{", true);
        doTest(13, "fun a b c }{", true);
        doTest(14, "fun a b c when true }{", true);
        doTest(15, "class c = fun a }{", true);
    }

    public void testSemicolonSemicolonKeyword() throws Exception {
        setVariants(SEMICOLON_SEMICOLON);

        doTest(1, "}{", true);
        doTest(2, ";; }{", false);
        doTest(3, "let g = }{", false);
        doTest(4, "let g = 0 }{", true);
        doTest(5, "0 }{", true);
        doTest(6, "module M = struct }{", true);
        doTest(7, "module M = struct ;; }{", false);
        doTest(8, "module M = struct let g = }{", false);
        doTest(9, "module M = struct let g = 0 }{", true);
        doTest(10, "module M = struct 0 }{", true);
        doTest(11, "module type M = sig }{", true);
        doTest(12, "module type M = sig ;; }{", false);
        doTest(13, "module type M = sig val g : }{", false);
        doTest(14, "module type M = sig val g : int }{", true);
    }

    public void testRBraceKeyword() throws Exception {
        setVariants(RBRACE);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = {a:int }{", true);
        doTest(4, "type t = {a:int; }{", false);
        doTest(5, "{a = 1 }{", true);
        doTest(6, "{a = 1; }{", false);
        doTest(7, "match 1 with {a = 1 }{", true);
        doTest(8, "match 1 with {a = 1; }{", false);
    }

    public void testVBarRBracketKeyword() throws Exception {
        setVariants(VBAR_RBRACKET);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "[| }{", true);
        doTest(4, "[| 1 }{", true);
        doTest(5, "[| 1; 2 }{", true);
        doTest(6, "match 1 with [| }{", true);
        doTest(7, "match 1 with [| 1 }{", true);
        doTest(8, "match 1 with [| 1; 2 }{", true);
    }

    public void testGtKeyword() throws Exception {
        setVariants(GT);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = < }{", true);
        doTest(4, "type t = < .. }{", true);
        doTest(5, "type t = < m : int }{", true);
        doTest(6, "type t = < m : int; }{", false);
        doTest(7, "type t = < m : int; .. }{", true);
    }

    public void testGtRBraceKeyword() throws Exception {
        setVariants(GT_RBRACE);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "{< }{", true);
        doTest(4, "{< a = 1 }{", true);
        doTest(5, "{< a = 1; }{", false);
    }

    public void testColonColonKeyword() throws Exception {
        setVariants(COLON_COLON);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "1 }{", true);
        doTest(4, "match 1 with 1 }{", true);
    }

    public void testColonGtKeyword() throws Exception {
        setVariants(COLON_GT);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "(1 }{", true);
        doTest(4, "(1 :> int }{", false);
        doTest(5, "(1 : int }{", true);
        doTest(6, "(1 : int :> int }{", false);
    }

    public void testLtMinusKeyword() throws Exception {
        setVariants(LT_MINUS);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "a.b }{", true);
        doTest(4, "a.(b) }{", true);
        doTest(5, "a.[b] }{", true);
        doTest(6, "class a = object val x = 0 method m = x }{", true);
    }

    public void testQuestKeyword() throws Exception {
        setVariants(QUEST);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = }{", true);
        doTest(4, "fun }{", true);
        doTest(5, "fun a }{", true);
        doTest(6, "class c = fun }{", true);
        doTest(7, "class c = fun a }{", true);
        doTest(8, "f }{", true);
        doTest(9, "f a }{", true);
        doTest(10, "class c = f }{", true);
        doTest(11, "class c = f a }{", true);
        doTest(12, "class c : }{", true);
    }

    public void testRParKeyword() throws Exception {
        setVariants(RPAR);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = (int }{", true);
        doTest(4, "type t = (int, int }{", true);
        doTest(5, "( }{", true);
        doTest(6, "match 1 with (1 }{", true);
        doTest(7, "match 1 with (1 : int }{", true);
        doTest(8, "(1 }{", true);
        doTest(9, "(1 : int }{", true);
        doTest(10, "a.(1 }{", true);
        doTest(11, "(1 :> int }{", true);
        doTest(12, "(1 : int :> int }{", true);
        doTest(13, "fun ~(label }{", true);
        doTest(14, "fun ~(label: int }{", true);
        doTest(15, "fun ?(label }{", true);
        doTest(16, "fun ?(label: int }{", true);
        doTest(17, "fun ?(label = 1 }{", true);
        doTest(18, "fun ?(label: int = 1 }{", true);
        doTest(19, "fun ?label: (a }{", true);
        doTest(20, "fun ?label: (a : int }{", true);
        doTest(21, "fun ?label: (a = 1 }{", true);
        doTest(22, "fun ?label: (a : int = 1 }{", true);
        doTest(23, "type ('a }{", true);
        doTest(24, "type ('a, 'b }{", true);
        doTest(25, "class type c = object (int }{", true);
        doTest(26, "class c = (cc }{", true);
        doTest(27, "class c = (cc : object end }{", true);
        doTest(28, "class c = object (cc }{", true);
        doTest(29, "class c = object (cc : int }{", true);
        doTest(30, "module M = functor (M1 : MT }{", true);
        doTest(31, "module type M = functor (M1 : MT }{", true);
        doTest(32, "module M = (M1 }{", true);
        doTest(33, "module type M = (M1 }{", true);
        doTest(34, "module type M = sig module MM (M1 : MT }{", true);
        doTest(35, "module type M = sig module MM (M1 : MT) (M2 : MT }{", true);
        doTest(36, "module M = M2(M1 }{", true);
        doTest(37, "module M = (M1 : MT }{", true);
        doTest(38, "module MM (M1 : MT }{", true);
        doTest(39, "module MM (M1 : MT) (M2 : MT }{", true);
    }

    public void testRBracketKeyword() throws Exception {
        setVariants(RBRACKET);

        doTest(1, "}{", false);
        doTest(2, "let g = }{", false);
        doTest(3, "type t = [ `tag }{", true);
        doTest(4, "type t = [> `tag }{", true);
        doTest(5, "type t = [< `tag }{", true);
        doTest(6, "type t = [< `tag > `tag }{", true);
        doTest(7, "[ }{", true);
        doTest(8, "match 1 with [1 }{", true);
        doTest(9, "match 1 with [1; 2 }{", true);
        doTest(10, "[1 }{", true);
        doTest(11, "[1; 2 }{", true);
        doTest(12, "a.[1 }{", true);
        doTest(13, "class type c = [int }{", true);
        doTest(14, "class type c = [int, int }{", true);
        doTest(15, "class c = [int }{", true);
        doTest(16, "class c = [int, int }{", true);
        doTest(17, "class ['a }{", true);
        doTest(18, "class ['a, 'b }{", true);
        doTest(19, "class type ['a }{", true);
        doTest(20, "class type ['a, 'b }{", true);
        doTest(21, "module type M = sig class ['a }{", true);
        doTest(22, "module type M = sig class ['a, 'b }{", true);
        doTest(23, "module type M = sig class type ['a }{", true);
        doTest(24, "module type M = sig class type ['a, 'b }{", true);
    }

    @NotNull
    @Override
    protected CompletionType getCompletionType() {
        return CompletionType.BASIC;
    }

    @Override
    protected int getInvocationCount() {
        return 1;
    }
}
