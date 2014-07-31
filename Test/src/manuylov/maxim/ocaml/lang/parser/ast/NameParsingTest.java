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

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.parser.ast.testCase.MLParsingTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static com.intellij.psi.TokenType.ERROR_ELEMENT;
import static manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes.*;
import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 13.03.2009
 */
@Test
public class NameParsingTest extends MLParsingTestCase {
    public void testModulePath() throws Exception {
        myTree.addNode(3, OPEN_DIRECTIVE);
        myTree.addNode(4, OPEN_KEYWORD);
        myTree.addNode(4, MODULE_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module3");

        doTest("open Module1.Module2.Module3", myTree.getStringRepresentation());
    }

    public void testModuleName() throws Exception {
        myTree.addNode(3, OPEN_DIRECTIVE);
        myTree.addNode(4, OPEN_KEYWORD);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("open Module", myTree.getStringRepresentation());
    }

    public void testLabelName() throws Exception { 
        myTree.addNode(3, LET_STATEMENT);
        myTree.addNode(4, LET_KEYWORD);
        myTree.addNode(4, LET_BINDING);
        myTree.addNode(5, LET_BINDING_PATTERN);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "label");
        myTree.addNode(6, COLON);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, TILDE);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "_label");
        myTree.addNode(6, COLON);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");

        doTest("let a ~label:a ~_label:a = 0", myTree.getStringRepresentation());
    }

    public void testExtendedModulePath() throws Exception {
        myTree.addNode(3, MODULE_TYPE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, MODULE_TYPE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "mt");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_WITH_CONSTRAINTS);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, WITH_KEYWORD);
        myTree.addNode(6, MODULE_TYPE_MODULE_CONSTRAINT);
        myTree.addNode(7, MODULE_KEYWORD);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "M");
        myTree.addNode(7, EQ);
        myTree.addNode(7, EXTENDED_MODULE_PATH);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(8, DOT);
        myTree.addNode(8, EXTENDED_MODULE_NAME);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(9, PARENTHESES);
        myTree.addNode(10, LPAR);
        myTree.addNode(10, EXTENDED_MODULE_PATH);
        myTree.addNode(11, MODULE_NAME);
        myTree.addNode(12, UCFC_IDENTIFIER, "Module3");
        myTree.addNode(11, DOT);
        myTree.addNode(11, EXTENDED_MODULE_NAME);
        myTree.addNode(12, MODULE_NAME);
        myTree.addNode(13, UCFC_IDENTIFIER, "Module4");
        myTree.addNode(12, PARENTHESES);
        myTree.addNode(13, LPAR);
        myTree.addNode(13, MODULE_NAME);
        myTree.addNode(14, UCFC_IDENTIFIER, "Module5");
        myTree.addNode(13, RPAR);
        myTree.addNode(10, RPAR);
        myTree.addNode(9, PARENTHESES);
        myTree.addNode(10, LPAR);
        myTree.addNode(10, EXTENDED_MODULE_PATH);
        myTree.addNode(11, MODULE_NAME);
        myTree.addNode(12, UCFC_IDENTIFIER, "Module6");
        myTree.addNode(11, DOT);
        myTree.addNode(11, MODULE_NAME);
        myTree.addNode(12, UCFC_IDENTIFIER, "Module7");
        myTree.addNode(10, RPAR);
        myTree.addNode(8, DOT);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "ModuleName");

        doTest("module type mt = m with module M = Module1.Module2(Module3.Module4(Module5))(Module6.Module7).ModuleName", myTree.getStringRepresentation());
    }

    public void testModuleTypePath() throws Exception {
        myTree.addNode(3, MODULE_TYPE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, MODULE_TYPE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "mt");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, EXTENDED_MODULE_NAME);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(7, PARENTHESES);
        myTree.addNode(8, LPAR);
        myTree.addNode(8, EXTENDED_MODULE_PATH);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module3");
        myTree.addNode(9, DOT);
        myTree.addNode(9, EXTENDED_MODULE_NAME);
        myTree.addNode(10, MODULE_NAME);
        myTree.addNode(11, UCFC_IDENTIFIER, "Module4");
        myTree.addNode(10, PARENTHESES);
        myTree.addNode(11, LPAR);
        myTree.addNode(11, MODULE_NAME);
        myTree.addNode(12, UCFC_IDENTIFIER, "Module5");
        myTree.addNode(11, RPAR);
        myTree.addNode(8, RPAR);
        myTree.addNode(7, PARENTHESES);
        myTree.addNode(8, LPAR);
        myTree.addNode(8, EXTENDED_MODULE_PATH);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module6");
        myTree.addNode(9, DOT);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module7");
        myTree.addNode(8, RPAR);
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "moduleType");

        doTest("module type mt = Module1.Module2(Module3.Module4(Module5))(Module6.Module7).moduleType", myTree.getStringRepresentation());
    }

    public void testValueName() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");

        doTest("a", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "_a");

        doTest("_a", myTree.getStringRepresentation());

        doTestOperatorName(PREFIX_OPERATOR, "!!");
        doTestOperatorName(NOT_EQ, "!=");
        doTestOperatorName(QUEST_QUEST, "??");
        doTestOperatorName(INFIX_OPERATOR, "|@");
        doTestOperatorName(EQ, "=");
        doTestOperatorName(LT, "<");
        doTestOperatorName(GT, ">");
        doTestOperatorName(AMP, "&");
        doTestOperatorName(PLUS, "+");
        doTestOperatorName(MINUS, "-");
        doTestOperatorName(MULT, "*");
        doTestOperatorName(DOLLAR, "$");
        doTestOperatorName(AMP_AMP, "&&");
        doTestOperatorName(MINUS_DOT, "-.");
        doTestOperatorName(VBAR_VBAR, "||");
        doTestOperatorName(LT_LT, "<<");
        doTestOperatorName(LT_COLON, "<:");
        doTestOperatorName(GT_GT, ">>");
        doTestOperatorName(DOLLAR_DOLLAR, "$$");
        doTestOperatorName(DOLLAR_COLON, "$:");
        doTestOperatorName(OR_KEYWORD, "or");
        doTestOperatorName(COLON_EQ, ":=");
        doTestOperatorName(MOD_KEYWORD, "mod");
        doTestOperatorName(LAND_KEYWORD, "land");
        doTestOperatorName(LOR_KEYWORD, "lor");
        doTestOperatorName(LXOR_KEYWORD, "lxor");
        doTestOperatorName(LSL_KEYWORD, "lsl");
        doTestOperatorName(LSR_KEYWORD, "lsr");
        doTestOperatorName(ASR_KEYWORD, "asr");
    }

    private void doTestOperatorName(final IElementType operatorType, @NotNull final String operator) throws Exception {
        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, OPERATOR_NAME);
        myTree.addNode(6, operatorType, operator);
        myTree.addNode(5, RPAR);

        doTest("( " + operator + " )", myTree.getStringRepresentation());
    }

    public void testConstructorPath() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTRUCTOR_PATH_EXPRESSION);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, CONSTRUCTOR_NAME_EXPRESSION);
        myTree.addNode(6, UCFC_IDENTIFIER, "Constr");

        doTest("Module1.Module2.Constr", myTree.getStringRepresentation());
    }

    public void testConstructorName() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTRUCTOR_NAME_EXPRESSION);
        myTree.addNode(5, UCFC_IDENTIFIER, "Constr");

        doTest("Constr", myTree.getStringRepresentation());
    }

    public void testTypeConstructorName() throws Exception {
        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "t");
        myTree.addNode(5, EQ);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "d");

        doTest("type t = d", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "t");
        myTree.addNode(5, EQ);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_d");

        doTest("type t = _d", myTree.getStringRepresentation());
    }

    public void testFieldName() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, RECORD_EXPRESSION);
        myTree.addNode(5, LBRACE);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, RBRACE);

        doTest("{a = 0}", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, RECORD_EXPRESSION);
        myTree.addNode(5, LBRACE);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "_a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, RBRACE);

        doTest("{_a = 0}", myTree.getStringRepresentation());
    }

    public void testClassPath() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, NEW_INSTANCE_EXPRESSION);
        myTree.addNode(5, NEW_KEYWORD);
        myTree.addNode(5, CLASS_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(6, DOT);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "className");

        doTest("new Module1.Module2.className", myTree.getStringRepresentation());
    }

    public void testClassName() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, NEW_INSTANCE_EXPRESSION);
        myTree.addNode(5, NEW_KEYWORD);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "className");

        doTest("new className", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, NEW_INSTANCE_EXPRESSION);
        myTree.addNode(5, NEW_KEYWORD);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_className");

        doTest("new _className", myTree.getStringRepresentation());
    }

    public void testMethodName() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CLASS_METHOD_ACCESSING_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, HASH);
        myTree.addNode(5, METHOD_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");

        doTest("a#m", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CLASS_METHOD_ACCESSING_EXPRESSION);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, HASH);
        myTree.addNode(5, METHOD_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_m");

        doTest("a#_m", myTree.getStringRepresentation());
    }

    public void testInstVarName() throws Exception { 
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, INSTANCE_DUPLICATING_EXPRESSION);
        myTree.addNode(5, LBRACE_LT);
        myTree.addNode(5, INST_VAR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "x");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");
        myTree.addNode(5, GT_RBRACE);

        doTest("{< x = 0 >}", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, INSTANCE_DUPLICATING_EXPRESSION);
        myTree.addNode(5, LBRACE_LT);
        myTree.addNode(5, INST_VAR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_x");
        myTree.addNode(5, EQ);
        myTree.addNode(5, CONSTANT_EXPRESSION);
        myTree.addNode(6, INTEGER_LITERAL, "0");
        myTree.addNode(5, GT_RBRACE);

        doTest("{< _x = 0 >}", myTree.getStringRepresentation());
    }

    public void testModuleTypeName() throws Exception {
        myTree.addNode(3, MODULE_TYPE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, MODULE_TYPE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "M");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "M");

        doTest("module type M = M", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, MODULE_TYPE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, MODULE_TYPE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");

        doTest("module type m = m", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, MODULE_TYPE_DEFINITION);
        myTree.addNode(4, MODULE_KEYWORD);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, MODULE_TYPE_DEFINITION_BINDING);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_m");
        myTree.addNode(5, EQ);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_m");

        doTest("module type _m = _m", myTree.getStringRepresentation());
    }

    public void testTypeConstructorPath() throws Exception {
        myTree.addNode(3, TYPE_DEFINITION);
        myTree.addNode(4, TYPE_KEYWORD);
        myTree.addNode(4, TYPE_BINDING);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "t");
        myTree.addNode(5, EQ);
        myTree.addNode(5, TYPE_CONSTRUCTOR_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, EXTENDED_MODULE_NAME);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(7, PARENTHESES);
        myTree.addNode(8, LPAR);
        myTree.addNode(8, EXTENDED_MODULE_PATH);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module3");
        myTree.addNode(9, DOT);
        myTree.addNode(9, EXTENDED_MODULE_NAME);
        myTree.addNode(10, MODULE_NAME);
        myTree.addNode(11, UCFC_IDENTIFIER, "Module4");
        myTree.addNode(10, PARENTHESES);
        myTree.addNode(11, LPAR);
        myTree.addNode(11, MODULE_NAME);
        myTree.addNode(12, UCFC_IDENTIFIER, "Module5");
        myTree.addNode(11, RPAR);
        myTree.addNode(8, RPAR);
        myTree.addNode(7, PARENTHESES);
        myTree.addNode(8, LPAR);
        myTree.addNode(8, EXTENDED_MODULE_PATH);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module6");
        myTree.addNode(9, DOT);
        myTree.addNode(9, MODULE_NAME);
        myTree.addNode(10, UCFC_IDENTIFIER, "Module7");
        myTree.addNode(8, RPAR);
        myTree.addNode(6, DOT);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "typeConstr");

        doTest("type t = Module1.Module2(Module3.Module4(Module5))(Module6.Module7).typeConstr", myTree.getStringRepresentation());
    }

    public void testTagName() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, TAGGED_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "tag");
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");

        doTest("`tag a", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, TAGGED_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Tag");
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");

        doTest("`Tag a", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, TAGGED_EXPRESSION);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "_tag");
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");

        doTest("`_tag a", myTree.getStringRepresentation());
    }

    public void testValuePath() throws Exception { 
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, VALUE_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");

        doTest("Module1.Module2.a", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, VALUE_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, VALUE_NAME);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, OPERATOR_NAME);
        myTree.addNode(7, PREFIX_OPERATOR, "!!");
        myTree.addNode(6, RPAR);

        doTest("Module1.Module2.(!!)", myTree.getStringRepresentation());
    }

    public void testFieldPath() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, RECORD_EXPRESSION);
        myTree.addNode(5, LBRACE);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(6, FIELD_PATH);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(7, DOT);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(7, DOT);
        myTree.addNode(7, FIELD_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT_EXPRESSION);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, RBRACE);

        doTest("{Module1.Module2.a = 0}", myTree.getStringRepresentation());
    }

    public void testConstant() throws Exception {
        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, INTEGER_LITERAL, "1");

        doTest("1", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, FLOAT_LITERAL, "1.2");

        doTest("1.2", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, CHAR_LITERAL, "'w'");

        doTest("'w'", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, ERROR_ELEMENT, Strings.ILLEGAL_CHAR_LITERAL);
        myTree.addNode(6, EMPTY_CHAR_LITERAL, "''");

        doTest("''", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, STRING_LITERAL, "\"ww\"");

        doTest("\"ww\"", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, FALSE_KEYWORD, "false");

        doTest("false", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(3, EXPRESSION_STATEMENT);
        myTree.addNode(4, CONSTANT_EXPRESSION);
        myTree.addNode(5, TRUE_KEYWORD, "true");

        doTest("true", myTree.getStringRepresentation());
    }
}
