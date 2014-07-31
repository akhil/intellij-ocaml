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
 *         Date: 16.03.2009
 */         
@Test
public abstract class BaseTypeParsingTest extends ParsingTestCase {
    public void testSeveralBindings() throws Exception {
        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "t1");
        myTree.addNode(4, AND_KEYWORD);
        myTree.addNode(4, TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "t2");

        doTest("type t1 and t2", myTree.getStringRepresentation());
    }

    public void testTypeParameters() throws Exception {
        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type 'a t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "_a");
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type '_a t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "A");
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type 'A t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(6, PLUS);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type +'a t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(6, MINUS);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type -'a t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PARENTHESES_TYPE_PARAMETERS);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type ('a) t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PARENTHESES_TYPE_PARAMETERS);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, PLUS);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type (+'a) t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PARENTHESES_TYPE_PARAMETERS);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, MINUS);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type (-'a) t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PARENTHESES_TYPE_PARAMETERS);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type ('a, 'b) t", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_PARAMETERIZED_BINDING);
        myTree.addNode(5, PARENTHESES_TYPE_PARAMETERS);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, PLUS);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, PLUS_MINUS_TYPE_PARAMETER);
        myTree.addNode(7, MINUS);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, TYPE_BINDING);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");

        doTest("type (+'a, -'b) t", myTree.getStringRepresentation());
    }

    public void testRecordTypeDefinition() throws Exception {
        addCommonNodes();
        myTree.addNode(5, RECORD_TYPE_DEFINITION);
        myTree.addNode(6, LBRACE);
        myTree.addNode(6, RECORD_FIELD_DEFINITION);
        myTree.addNode(7, FIELD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, SEMICOLON);
        myTree.addNode(6, RECORD_FIELD_DEFINITION);
        myTree.addNode(7, MUTABLE_KEYWORD);
        myTree.addNode(7, FIELD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RBRACE);

        doTest("type t = {a : int; mutable b : int}", myTree.getStringRepresentation());
    }

    public void testVariantTypeDefinition() throws Exception {
        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_DEFINITION);
        myTree.addNode(6, CONSTRUCTOR_DEFINITION);
        myTree.addNode(7, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(8, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, CONSTRUCTOR_DEFINITION);
        myTree.addNode(7, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(8, UCFC_IDENTIFIER, "AnotherConstr");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");

        doTest("type t = Constr | AnotherConstr of int", myTree.getStringRepresentation());
    }

    public void testTypeConstraint() throws Exception {
        addCommonNodes();
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, CONSTRAINT_KEYWORD);
        myTree.addNode(5, TYPE_DEFINITION_CONSTRAINT);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = int constraint 'a = int", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_DEFINITION);
        myTree.addNode(6, CONSTRUCTOR_DEFINITION);
        myTree.addNode(7, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(8, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(5, CONSTRAINT_KEYWORD);
        myTree.addNode(5, TYPE_DEFINITION_CONSTRAINT);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = Constr constraint 'a = int", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, RECORD_TYPE_DEFINITION);
        myTree.addNode(6, LBRACE);
        myTree.addNode(6, RECORD_FIELD_DEFINITION);
        myTree.addNode(7, FIELD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RBRACE);
        myTree.addNode(5, CONSTRAINT_KEYWORD);
        myTree.addNode(5, TYPE_DEFINITION_CONSTRAINT);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = {a : int} constraint 'a = int", myTree.getStringRepresentation());
    }

    public void testTypeParameterExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, TYPE_PARAMETER);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");

        doTest("type t = 'a", myTree.getStringRepresentation());
    }

    public void testUnderscoreExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, UNDERSCORE_TYPE_EXPRESSION);
        myTree.addNode(6, UNDERSCORE);

        doTest("type t = _", myTree.getStringRepresentation());
    }

    public void testTypeExpressionInParentheses() throws Exception {
        addCommonNodes();
        myTree.addNode(5, PARENTHESES_TYPE_EXPRESSION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);

        doTest("type t = (int)", myTree.getStringRepresentation());
    }

    public void testFunctionExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = int -> int", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "label");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = label: int -> int", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "label");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = ?label: int -> int", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(6, QUEST);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "label");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, MINUS_GT);
        myTree.addNode(6, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(7, QUEST);
        myTree.addNode(7, LABEL_DEFINITION);
        myTree.addNode(8, LABEL_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "label");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, MINUS_GT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");

        doTest("type t = ?label: int -> ?label: int -> int", myTree.getStringRepresentation());
    }

    public void testTupleExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, TUPLE_TYPE_EXPRESSION);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, MULT);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, MULT);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("type t = int * int * int", myTree.getStringRepresentation());
    }

    public void testTypeConstructorExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");

        doTest("type t = int", myTree.getStringRepresentation());
    }

    public void testTypeConstructorApplicationExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tt");

        doTest("type t = int tt", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION);
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tt");

        doTest("type t = (int) tt", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION);
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tt");

        doTest("type t = (int, int) tt", myTree.getStringRepresentation());
    }

    public void testAsExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, AS_TYPE_EXPRESSION);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, AS_KEYWORD);
        myTree.addNode(6, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");

        doTest("type t = int as 'a", myTree.getStringRepresentation());
    }

    public void testVariantTypeExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [ `tag ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [ | `tag ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [ `tag | `tag of int | int ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_GT);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [> `tag ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_GT);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [> `tag | `tag of int | int ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_LT);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [< `tag ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_LT);
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [< | `tag ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_LT);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, GT);
        myTree.addNode(6, ACCENT);
        myTree.addNode(6, TAG_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tag1");
        myTree.addNode(6, ACCENT);
        myTree.addNode(6, TAG_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tag2");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [< `tag > `tag1 `tag2 ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_LT);
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, GT);
        myTree.addNode(6, ACCENT);
        myTree.addNode(6, TAG_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tag1");
        myTree.addNode(6, ACCENT);
        myTree.addNode(6, TAG_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tag2");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [< | `tag > `tag1 `tag2 ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_LT);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, AMP);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [< `tag | `tag of int | `tag of int & int | int ]", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, VARIANT_TYPE_TYPE_EXPRESSION);
        myTree.addNode(6, LBRACKET_LT);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TAG_SPEC_FULL);
        myTree.addNode(7, ACCENT);
        myTree.addNode(7, TAG_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "tag");
        myTree.addNode(7, OF_KEYWORD);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, AMP);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, VBAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, GT);
        myTree.addNode(6, ACCENT);
        myTree.addNode(6, TAG_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tag1");
        myTree.addNode(6, ACCENT);
        myTree.addNode(6, TAG_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tag2");
        myTree.addNode(6, RBRACKET);

        doTest("type t = [< `tag | `tag of int | `tag of int & int | int > `tag1 `tag2]", myTree.getStringRepresentation());
    }

    public void testObjectInterfaceExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, OBJECT_INTERFACE_TYPE_EXPRESSION);
        myTree.addNode(6, LT);
        myTree.addNode(6, DOT_DOT);
        myTree.addNode(6, GT);

        doTest("type t = < .. >", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_INTERFACE_TYPE_EXPRESSION);
        myTree.addNode(6, LT);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m1");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, GT);

        doTest("type t = <m1 : int>", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_INTERFACE_TYPE_EXPRESSION);
        myTree.addNode(6, LT);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m1");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, SEMICOLON);
        myTree.addNode(6, DOT_DOT);
        myTree.addNode(6, GT);

        doTest("type t = <m1 : int; ..>", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_INTERFACE_TYPE_EXPRESSION);
        myTree.addNode(6, LT);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m1");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, SEMICOLON);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m2");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, GT);

        doTest("type t = <m1 : int; m2: int>", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, OBJECT_INTERFACE_TYPE_EXPRESSION);
        myTree.addNode(6, LT);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m1");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, SEMICOLON);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m2");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, SEMICOLON);
        myTree.addNode(6, DOT_DOT);
        myTree.addNode(6, GT);

        doTest("type t = <m1 : int; m2: int; ..>", myTree.getStringRepresentation());
    }

    public void testSuperClassExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, SUPER_CLASS_TYPE_EXPRESSION);
        myTree.addNode(6, HASH);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "myClass");

        doTest("type t = #myClass", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, SUPER_CLASS_TYPE_EXPRESSION);
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, HASH);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "myClass");

        doTest("type t = (int) #myClass", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(5, SUPER_CLASS_TYPE_EXPRESSION);
        myTree.addNode(6, PARENTHESES);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, COMMA);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, RPAR);
        myTree.addNode(6, HASH);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "myClass");

        doTest("type t = (int, int) #myClass", myTree.getStringRepresentation());
    }

    public void testAssociativity() throws Exception {
        final String text1 = getTreeIgnoringParentheses("type t = ?label: int -> ?label: int -> int");
        final String text2 = getTreeIgnoringParentheses("type t = (?label: int -> (?label: int -> int))");

        assertEquals(text1, text2);
    }

    public void testPriorities() throws Exception {
        String text1 = getTreeIgnoringParentheses("type t = int -> int as 'a");
        String text2 = getTreeIgnoringParentheses("type t = ((int -> int) as 'a)");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("type t = int -> int * int");
        text2 = getTreeIgnoringParentheses("type t = (int -> (int * int))");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("type t = int * int -> int");
        text2 = getTreeIgnoringParentheses("type t = ((int * int) -> int)");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("type t = int * int list");
        text2 = getTreeIgnoringParentheses("type t = (int * (int list))");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("type t = int list * int");
        text2 = getTreeIgnoringParentheses("type t = ((int list) * int)");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("type t = int * int #myClass");
        text2 = getTreeIgnoringParentheses("type t = (int * (int #myClass))");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParentheses("type t = int #myClass * int");
        text2 = getTreeIgnoringParentheses("type t = ((int #myClass) * int)");

        assertEquals(text1, text2);
    }

    public void testPolymorphicExpression() throws Exception {
        addCommonNodes();
        myTree.addNode(5, OBJECT_INTERFACE_TYPE_EXPRESSION);
        myTree.addNode(6, LT);
        myTree.addNode(6, METHOD_TYPE);
        myTree.addNode(7, METHOD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "m");
        myTree.addNode(7, COLON);
        myTree.addNode(7, POLY_TYPE_EXPRESSION);
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "a");
        myTree.addNode(8, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(9, QUOTE);
        myTree.addNode(9, TYPE_PARAMETER_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "b");
        myTree.addNode(8, DOT);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, GT);

        doTest("type t = <m : 'a 'b . int>", myTree.getStringRepresentation());
    }

    private void addCommonNodes() {
        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "t");
        myTree.addNode(5, EQ);
    }
}
