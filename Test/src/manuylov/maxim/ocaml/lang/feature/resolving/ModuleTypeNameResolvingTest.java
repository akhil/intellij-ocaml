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
public class ModuleTypeNameResolvingTest extends ResolvingTestCase {
    public void testModuleTypeResolving() throws Exception {
        doTest(1, "" +
            "module type {{M = M1}};;" +
            "module type M2 = }{M;;");

        doTest(2, "module type M = }{M1;;");

        doTest(3, "" +
            "module M = " +
            "struct " +
            "  module type {{M1 = sig end}};; " +
            "end;; " +
            "module type M2 = M.}{M1;;");

        doTest(4, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module type {{M3 = sig end}};;" +
            "  end;; " +
            "end;; " +
            "module type M2 = M.M1.}{M3;;");

        doTest(5, "" +
            "module type Main = " +
            "sig " +
            "  module type M = " +
            "  sig " +
            "    module type M1 = sig end;; " +
            "  end;; " +
            "  module type M2 = }{M1;; " +
            "end;;");

        doTest(6, "" +
            "module type Main = " +
            "sig " +
            "  module type M = " +
            "  sig " +
            "    module type M1 = sig end;; " +
            "  end;; " +
            "  open M;; " +
            "  module type M2 = }{M1;; " +
            "end;;");

        doTest(7, "" +
            "module type Main = " +
            "sig " +
            "  module type M = " +
            "  sig " +
            "    module type M1 = sig end;; " +
            "  end;; " +
            "  open }{M;; " +
            "  module type M2 = M1;; " +
            "end;;");

        doTest(8, "" +
            "module type Main = " +
            "sig " +
            "  module type M = " +
            "  sig " +
            "    module type {{M1 = sig end}};; " +
            "  end;; " +
            "  include M;; " +
            "  module type M2 = }{M1;; " +
            "end;;");

        doTest(9, "" +
            "module type Main = " +
            "sig " +
            "  module type {{M = " +
            "  sig " +
            "    module type M1 = sig end;; " +
            "  end}};; " +
            "  include }{M;; " +
            "  module type M2 = M1;; " +
            "end;;");

        doTest(10, "" +
            "module type M = " +
            "sig " +
            "  module type M1 = sig end;; " +
            "end;; " +
            "module M2 = }{M1;;");

        doTest(11, "" +
            "module type {{}{M = sig end}};;");
    }
}