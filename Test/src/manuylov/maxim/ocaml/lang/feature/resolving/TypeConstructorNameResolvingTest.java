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
public class TypeConstructorNameResolvingTest extends ResolvingTestCase {
    public void testTypeConstructorNameResolving() throws Exception {
        doTest(1, "" +
            "type {{t = One}};; " +
            "type t2 = }{t;;");

        doTest(2, "" +
            "type {{}{t = One}};;");

        doTest(3, "" +
            "module M = " +
            "struct " +
            "  type {{t = One}};; " +
            "end;; " +
            "type t2 = M.}{t;;");

        doTest(4, "" +
            "module M = " +
            "struct " +
            "  type t = One;; " +
            "end;; " +
            "type t2 = }{t;;");

        doTest(5, "" +
            "module M : sig " +
            "             type {{t = One}};; " +
            "           end = struct end;; " +
            "type t2 = M.}{t;;");

        doTest(6, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    type {{t = One}};; " +
            "  end;; " +
            "end;; " +
            "type t2 = M.M1.}{t;;");

        doTest(7, "" +
            "class {{t = object end}};; " +
            "type t2 = }{t;;");

        doTest(8, "" +
            "class {{}{t = object end}};;");

        doTest(9, "" +
            "module M = " +
            "struct " +
            "  class {{t = object end}};; " +
            "end;; " +
            "type t2 = M.}{t;;");

        doTest(10, "" +
            "module M = " +
            "struct " +
            "  class t = object end;; " +
            "end;; " +
            "type t2 = }{t;;");

        doTest(11, "" +
            "module M : sig " +
            "             class {{t : object end}};; " +
            "           end = struct end;; " +
            "type t2 = M.}{t;;");

        doTest(12, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    class {{t = object end}};; " +
            "  end;; " +
            "end;; " +
            "type t2 = M.M1.}{t;;");

        doTest(13, "" +
            "class type {{t = object end}};; " +
            "type t2 = }{t;;");

        doTest(14, "" +
            "class type {{}{t = object end}};;");

        doTest(15, "" +
            "module M = " +
            "struct " +
            "  class type {{t = object end}};; " +
            "end;; " +
            "type t2 = M.}{t;;");

        doTest(16, "" +
            "module M = " +
            "struct " +
            "  class type t = object end;; " +
            "end;; " +
            "type t2 = }{t;;");

        doTest(17, "" +
            "module M : sig " +
            "             class type {{t = object end}};; " +
            "           end = struct end;; " +
            "type t2 = M.}{t;;");

        doTest(18, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    class type {{t = object end}};; " +
            "  end;; " +
            "end;; " +
            "type t2 = M.M1.}{t;;");

        doTest(19, "" +
            "type 'a {{}{t = One}};;");
   }
}