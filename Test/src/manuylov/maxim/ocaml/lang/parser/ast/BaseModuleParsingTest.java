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

package manuylov.maxim.ocaml.lang.parser.ast;

import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.parser.ast.testCase.ParsingTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public abstract class BaseModuleParsingTest extends ParsingTestCase {
    public void testModuleTypePathModuleType() throws Exception {
        myTree.addNode(3, getMainElement());
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, getTypeBindingElement());
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "M1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "M2");
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");

        doTest("module type m = M1.M2.m", myTree.getStringRepresentation());
    }

    public void testModuleTypeInParentheses() throws Exception {
        myTree.addNode(3, getMainElement());
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, getTypeBindingElement());
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, PARENTHESES_MODULE_TYPE);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "M");
        myTree.addNode(6, RPAR);

        doTest("module type m = (M)", myTree.getStringRepresentation());
    }

    public void testModuleTypeWithConstraints() throws Exception {
        myTree.addNode(3, getMainElement());
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, getTypeBindingElement());
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_WITH_CONSTRAINTS);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "M");
        myTree.addNode(6, WITH_KEYWORD);
        myTree.addNode(6, MODULE_TYPE_MODULE_CONSTRAINT);
        myTree.addNode(7, MODULE_KEYWORD);
        myTree.addNode(7, MODULE_PATH);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "M1");
        myTree.addNode(8, DOT);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "M2");
        myTree.addNode(8, DOT);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "Mod");
        myTree.addNode(7, EQ);
        myTree.addNode(7, EXTENDED_MODULE_NAME);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "M4");
        myTree.addNode(8, PARENTHESES);
        myTree.addNode(9, LPAR);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "M5");
        myTree.addNode(9, RPAR);
        myTree.addNode(6, AND_KEYWORD);
        myTree.addNode(6, MODULE_TYPE_TYPE_CONSTRAINT);
        myTree.addNode(7, TYPE_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "t");
        myTree.addNode(7, EQ);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, AND_KEYWORD);
        myTree.addNode(6, MODULE_TYPE_TYPE_CONSTRAINT);
        myTree.addNode(7, TYPE_KEYWORD);
        myTree.addNode(7, TYPE_PARAMETER);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, TYPE_PARAMETER);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "b");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, TYPE_PARAMETER);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "c");
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tt");
        myTree.addNode(7, EQ);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");

        doTest("module type m = M with module M1.M2.Mod = M4(M5) and type t = int and type 'a, 'b, 'c tt = int", myTree.getStringRepresentation());
    }

    public void testFunctorModuleType() throws Exception {
        myTree.addNode(3, getMainElement());
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, getTypeBindingElement());
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, FUNCTOR_MODULE_TYPE);
        myTree.addNode(6, FUNCTOR_KEYWORD);
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, MODULE_PARAMETER);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(8, COLON);
        myTree.addNode(8, MODULE_TYPE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName");

        doTest("module type m = functor (ModuleName : ModuleTypeName) -> ModuleTypeName", myTree.getStringRepresentation());
    }

    public void testSigEndModuleType() throws Exception {
        myTree.addNode(3, getMainElement());
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, getTypeBindingElement());
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, SIG_END_MODULE_TYPE);
        myTree.addNode(6, SIG_KEYWORD);
        myTree.addNode(6, VALUE_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, END_KEYWORD);

        doTest("module type m = sig val a : int end", myTree.getStringRepresentation());
    }

    @NotNull
    protected abstract IElementType getMainElement();

    @NotNull
    protected abstract IElementType getTypeBindingElement();
}
