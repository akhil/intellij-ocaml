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

import manuylov.maxim.ocaml.lang.parser.ast.testCase.ParsingTestCase;
import org.testng.annotations.Test;

import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public abstract class BaseClassParsingTest extends ParsingTestCase {
    public void testSeveralClassTypeBindings() throws Exception {
        addCommonNodes();
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, CLASS_TYPE_BINDING);
        myTree.addNode(5, VIRTUAL_KEYWORD);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class2");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, CLASS_TYPE_BINDING);
        myTree.addNode(5, BRACKETS);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, RBRACKET);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class3");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, CLASS_TYPE_BINDING);
        myTree.addNode(5, VIRTUAL_KEYWORD);
        myTree.addNode(5, BRACKETS);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, RBRACKET);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class3");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class0");
        
        doTest("class type class1 = class0 and virtual class2 = class0 and ['a, 'b, 'c] class3 = class0 and virtual ['a, 'b, 'c] class3 = class0", myTree.getStringRepresentation());
    }

    public void testClassPathClassBodyType() throws Exception {
        addCommonNodes();
        myTree.addNode(5, CLASS_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(6, DOT);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "class0");

        doTest("class type class1 = Module1.Module2.class0", myTree.getStringRepresentation());
    }

    public void testClassPathApplicationClassBodyType() throws Exception {
        addCommonNodes();
        myTree.addNode(5, CLASS_PATH_APPLICATION);
        myTree.addNode(6, BRACKETS);
        myTree.addNode(7, LBRACKET);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RBRACKET);
        myTree.addNode(6, CLASS_PATH);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(7, DOT);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(7, DOT);
        myTree.addNode(7, CLASS_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "class0");

        doTest("class type class1 = [int, int, int] Module1.Module2.class0", myTree.getStringRepresentation());
    }

    public void testObjectEndClassBodyType() throws Exception {
        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, OBJECT_SELF_SPECIFICATION);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object (int) end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(7, INHERIT_KEYWORD);
        myTree.addNode(7, CLASS_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "class0");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, MUTABLE_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, MUTABLE_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, PRIVATE_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, PRIVATE_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, CONSTRAINT_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, CONSTRAINT_KEYWORD);
        myTree.addNode(7, TYPE_PARAMETER);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, EQ);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object " +
               "inherit class0 " +
               "val x : int " +
               "val mutable x : int " +
               "val virtual x : int " +
               "val mutable virtual x : int " +
               "method m : 'a . int " +
               "method private m : 'a . int " +
               "method virtual m : 'a . int " +
               "method private virtual m : 'a . int " +
               "constraint 'a = int " +
               "end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, OBJECT_SELF_SPECIFICATION);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(7, INHERIT_KEYWORD);
        myTree.addNode(7, CLASS_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "class0");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, MUTABLE_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, VAL_KEYWORD);
        myTree.addNode(7, MUTABLE_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, INST_VAR_NAME_DEFINITION);
        myTree.addNode(8, LCFC_IDENTIFIER, "x");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, PRIVATE_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, METHOD_KEYWORD);
        myTree.addNode(7, PRIVATE_KEYWORD);
        myTree.addNode(7, VIRTUAL_KEYWORD);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, CONSTRAINT_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(7, CONSTRAINT_KEYWORD);
        myTree.addNode(7, TYPE_PARAMETER);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, EQ);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object (int) " +
               "inherit class0 " +
               "val x : int " +
               "val mutable x : int " +
               "val virtual x : int " +
               "val mutable virtual x : int " +
               "method m : 'a . int " +
               "method private m : 'a . int " +
               "method virtual m : 'a . int " +
               "method private virtual m : 'a . int " +
               "constraint 'a = int " +
               "end", myTree.getStringRepresentation());
    }

    public void testClassType() throws Exception {
        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(7, INHERIT_KEYWORD);
        myTree.addNode(7, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(8, OBJECT_KEYWORD);
        myTree.addNode(8, END_KEYWORD);
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object inherit object end end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(7, INHERIT_KEYWORD);
        myTree.addNode(7, FUNCTION_CLASS_TYPE);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(8, MINUS_GT);
        myTree.addNode(8, FUNCTION_CLASS_TYPE);
        myTree.addNode(9, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "int");
        myTree.addNode(9, MINUS_GT);
        myTree.addNode(9, CLASS_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "myClass");
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object inherit int -> int -> myClass end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(7, INHERIT_KEYWORD);
        myTree.addNode(7, FUNCTION_CLASS_TYPE);
        myTree.addNode(8, LABEL_DEFINITION);
        myTree.addNode(9, LABEL_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "label");
        myTree.addNode(8, COLON);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(8, MINUS_GT);
        myTree.addNode(8, FUNCTION_CLASS_TYPE);
        myTree.addNode(9, QUEST);
        myTree.addNode(9, LABEL_DEFINITION);
        myTree.addNode(10, LABEL_NAME);
        myTree.addNode(11, LCFC_IDENTIFIER, "label");
        myTree.addNode(9, COLON);
        myTree.addNode(9, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "int");
        myTree.addNode(9, MINUS_GT);
        myTree.addNode(9, CLASS_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "myClass");
        myTree.addNode(6, END_KEYWORD);

        doTest("class type class1 = object inherit label: int -> ?label: int -> myClass end", myTree.getStringRepresentation());
    }

    private void addCommonNodes() {
        myTree.addNode(3, CLASS_TYPE_DEFINITION);
        myTree.addNode(4, CLASS_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, CLASS_TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, EQ);
    }
}
