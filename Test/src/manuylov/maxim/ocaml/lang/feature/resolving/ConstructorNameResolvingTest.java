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

package manuylov.maxim.ocaml.lang.feature.resolving;

import manuylov.maxim.ocaml.lang.feature.resolving.testCase.ResolvingTestCase;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 19.06.2009
 */
@Test
public class ConstructorNameResolvingTest extends ResolvingTestCase {
    public void testConstructorNameResolving() throws Exception {
        doTest(1, "" +
            "type t = {{One}};;" +
            "let f = }{One;;");

        doTest(2, "" +
            "type t = {{One of int}};;" +
            "let f = }{One 1;;");

        doTest(3, "" +
            "type t = {{One}} | Two;;" +
            "let f = }{One;;");

        doTest(4, "" +
            "type t = {{One of int}} | Two;;" +
            "let f = }{One 2;;");

        doTest(5, "" +
            "type t = One | {{Two}};;" +
            "let f = }{Two;;");

        doTest(6, "" +
            "type t = One | {{Two of int}};;" +
            "let f = }{Two 3;;");

        doTest(7, "" +
            "module M = " +
            "struct " +
            "  type t = {{One}} | Two;;" +
            "end;; " +
            "let f = M.}{One;;");

        doTest(8, "" +
            "module M = " +
            "struct " +
            "  type t = One | Two;;" +
            "end;; " +
            "let f = }{One;;");

        doTest(9, "" +
            "module M : sig " +
            "             type t = {{One}} | Two;; " +
            "           end = struct end;; " +
            "let f = M.}{One;;");

        doTest(10, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    type t = {{One}} | Two;;" +
            "  end;; " +
            "end;; " +
            "let f = M.M1.}{One;;");

        doTest(11, "" +
            "type t = {{}{One}};;");

        doTest(12, "" +
            "exception {{}{Ex}};;");

        doTest(13, "" +
            "exception {{}{Ex}} = Ex2;;");

        doTest(14, "" +
            "exception {{}{Ex}} of int;;");

        doTest(15, "" +
            "exception {{Ex}};; " +
            "exception Ex2 = }{Ex;;");

        doTest(16, "" +
            "module M : sig " +
            "             exception {{Ex}};; " +
            "           end = struct end;; " +
            "exception Ex2 = M.}{Ex;;");
   }
}