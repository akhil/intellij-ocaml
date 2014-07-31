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

package manuylov.maxim.ocaml.lang.parser.ast.element;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.mli.MLIFileTypeLanguage;
import manuylov.maxim.ocaml.lang.OCamlElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 05.02.2009
 */
public interface OCamlElementTypes {
    @NotNull IFileElementType ML_FILE = new IFileElementType("OCaml:ML_FILE", MLFileTypeLanguage.INSTANCE);
    @NotNull IFileElementType MLI_FILE = new IFileElementType("OCaml:MLI_FILE", MLIFileTypeLanguage.INSTANCE);

    /* Let parsing { */

    @NotNull IElementType LET_BINDING = new OCamlElementType("LET_BINDING");
    @NotNull IElementType LET_BINDING_PATTERN = new OCamlElementType("LET_BINDING_PATTERN");
    @NotNull IElementType LET_STATEMENT = new OCamlElementType("LET_STATEMENT");
    @NotNull IElementType LET_EXPRESSION = new OCamlElementType("LET_EXPRESSION");
    @NotNull IElementType LET_CLASS_EXPRESSION = new OCamlElementType("LET_CLASS_EXPRESSION");

    /* } */

    /* Statement parsing { */

    @NotNull IElementType EXTERNAL_DEFINITION = new OCamlElementType("EXTERNAL_DEFINITION");
    @NotNull IElementType EXCEPTION_DEFINITION = new OCamlElementType("EXCEPTION_DEFINITION");
    @NotNull IElementType OPEN_DIRECTIVE = new OCamlElementType("OPEN_DIRECTIVE");
    @NotNull IElementType INCLUDE_DIRECTIVE_DEFINITION = new OCamlElementType("INCLUDE_DIRECTIVE_DEFINITION");
    @NotNull IElementType EXCEPTION_SPECIFICATION = new OCamlElementType("EXCEPTION_SPECIFICATION");
    @NotNull IElementType INCLUDE_DIRECTIVE_SPECIFICATION = new OCamlElementType("INCLUDE_DIRECTIVE_SPECIFICATION");
    @NotNull IElementType VALUE_SPECIFICATION = new OCamlElementType("VALUE_SPECIFICATION");
    @NotNull IElementType EXTERNAL_DECLARATION = new OCamlElementType("EXTERNAL_DECLARATION");

    /* } */

    /* Type parsing { */

    @NotNull IElementType TYPE_DEFINITION = new OCamlElementType("TYPE_DEFINITION");
    @NotNull IElementType TYPE_PARAMETERIZED_BINDING = new OCamlElementType("TYPE_PARAMETERIZED_BINDING");
    @NotNull IElementType TYPE_BINDING = new OCamlElementType("TYPE_BINDING");
    @NotNull IElementType TYPE_PARAMETER = new OCamlElementType("TYPE_PARAMETER");
    @NotNull IElementType TYPE_PARAMETER_DEFINITION = new OCamlElementType("TYPE_PARAMETER_DEFINITION");
    @NotNull IElementType PLUS_MINUS_TYPE_PARAMETER = new OCamlElementType("PLUS_MINUS_TYPE_PARAMETER");
    @NotNull IElementType RECORD_TYPE_DEFINITION = new OCamlElementType("RECORD_TYPE_DEFINITION");
    @NotNull IElementType RECORD_FIELD_DEFINITION = new OCamlElementType("RECORD_FIELD_DEFINITION");
    @NotNull IElementType VARIANT_TYPE_DEFINITION = new OCamlElementType("VARIANT_TYPE_DEFINITION");
    @NotNull IElementType CONSTRUCTOR_DEFINITION = new OCamlElementType("CONSTRUCTOR_DEFINITION");
    @NotNull IElementType TYPE_DEFINITION_CONSTRAINT = new OCamlElementType("TYPE_DEFINITION_CONSTRAINT");
    @NotNull IElementType AS_TYPE_EXPRESSION = new OCamlElementType("AS_TYPE_EXPRESSION");
    @NotNull IElementType FUNCTION_TYPE_EXPRESSION = new OCamlElementType("FUNCTION_TYPE_EXPRESSION");
    @NotNull IElementType TUPLE_TYPE_EXPRESSION = new OCamlElementType("TUPLE_TYPE_EXPRESSION");
    @NotNull IElementType SUPER_CLASS_TYPE_EXPRESSION = new OCamlElementType("SUPER_CLASS_TYPE_EXPRESSION");
    @NotNull IElementType TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION = new OCamlElementType("TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION");
    @NotNull IElementType OBJECT_INTERFACE_TYPE_EXPRESSION = new OCamlElementType("OBJECT_INTERFACE_TYPE_EXPRESSION");
    @NotNull IElementType METHOD_TYPE = new OCamlElementType("METHOD_TYPE");
    @NotNull IElementType VARIANT_TYPE_TYPE_EXPRESSION = new OCamlElementType("VARIANT_TYPE_TYPE_EXPRESSION");
    @NotNull IElementType TAG_SPEC = new OCamlElementType("TAG_SPEC");
    @NotNull IElementType TAG_SPEC_FULL = new OCamlElementType("TAG_SPEC_FULL");
    @NotNull IElementType POLY_TYPE_EXPRESSION = new OCamlElementType("POLY_TYPE_EXPRESSION");
    @NotNull IElementType PARENTHESES_TYPE_EXPRESSION = new OCamlElementType("PARENTHESES_TYPE_EXPRESSION");
    @NotNull IElementType PARENTHESES_TYPE_PARAMETERS = new OCamlElementType("PARENTHESES_TYPE_PARAMETERS");
    @NotNull IElementType UNDERSCORE_TYPE_EXPRESSION = new OCamlElementType("UNDERSCORE_TYPE_EXPRESSION");

    /* } */

    /* Class parsing { */

    @NotNull IElementType CLASS_DEFINITION = new OCamlElementType("CLASS_DEFINITION");
    @NotNull IElementType CLASS_BINDING = new OCamlElementType("CLASS_BINDING");
    @NotNull IElementType CLASS_TYPE_DEFINITION = new OCamlElementType("CLASS_TYPE_DEFINITION");
    @NotNull IElementType CLASS_TYPE_BINDING = new OCamlElementType("CLASS_TYPE_BINDING");
    @NotNull IElementType FUNCTION_CLASS_TYPE = new OCamlElementType("FUNCTION_CLASS_TYPE");
    @NotNull IElementType CLASS_PATH_APPLICATION = new OCamlElementType("CLASS_PATH_APPLICATION");
    @NotNull IElementType OBJECT_END_CLASS_BODY_TYPE = new OCamlElementType("OBJECT_END_CLASS_BODY_TYPE");
    @NotNull IElementType INHERIT_CLASS_FILED_SPECIFICATION = new OCamlElementType("INHERIT_CLASS_FILED_SPECIFICATION");
    @NotNull IElementType VALUE_CLASS_FIELD_SPECIFICATION = new OCamlElementType("VALUE_CLASS_FIELD_SPECIFICATION");
    @NotNull IElementType METHOD_CLASS_FIELD_SPECIFICATION = new OCamlElementType("METHOD_CLASS_FIELD_SPECIFICATION");
    @NotNull IElementType CONSTRAINT_CLASS_FIELD_SPECIFICATION = new OCamlElementType("CONSTRAINT_CLASS_FIELD_SPECIFICATION");
    @NotNull IElementType FUNCTION_APPLICATION_CLASS_EXPRESSION = new OCamlElementType("FUNCTION_APPLICATION_CLASS_EXPRESSION");
    @NotNull IElementType OBJECT_END_CLASS_EXPRESSION = new OCamlElementType("OBJECT_END_CLASS_EXPRESSION");
    @NotNull IElementType OBJECT_SELF_DEFINITION = new OCamlElementType("OBJECT_SELF_DEFINITION");
    @NotNull IElementType OBJECT_SELF_SPECIFICATION = new OCamlElementType("OBJECT_SELF_SPECIFICATION");
    @NotNull IElementType INHERIT_CLASS_FIELD_DEFINITION = new OCamlElementType("INHERIT_CLASS_FIELD_DEFINITION");
    @NotNull IElementType INITIALIZER_CLASS_FIELD_DEFINITION = new OCamlElementType("INITIALIZER_CLASS_FIELD_DEFINITION");
    @NotNull IElementType CONSTRAINT_CLASS_FIELD_DEFINITION = new OCamlElementType("CONSTRAINT_CLASS_FIELD_DEFINITION");
    @NotNull IElementType VALUE_CLASS_FIELD_DEFINITION = new OCamlElementType("VALUE_CLASS_FIELD_DEFINITION");
    @NotNull IElementType METHOD_CLASS_FIELD_DEFINITION = new OCamlElementType("METHOD_CLASS_FIELD_DEFINITION");
    @NotNull IElementType FUNCTION_CLASS_EXPRESSION = new OCamlElementType("FUNCTION_CLASS_EXPRESSION");
    @NotNull IElementType CLASS_SPECIFICATION = new OCamlElementType("CLASS_SPECIFICATION");
    @NotNull IElementType CLASS_SPECIFICATION_BINDING = new OCamlElementType("CLASS_SPECIFICATION_BINDING");
    @NotNull IElementType CLASS_TYPE_CONSTRAINT = new OCamlElementType("CLASS_TYPE_CONSTRAINT");
    @NotNull IElementType PARENTHESES_CLASS_EXPRESSION = new OCamlElementType("PARENTHESES_CLASS_EXPRESSION");

    /* } */

    /* Module parsing { */

    @NotNull IElementType MODULE_DEFINITION = new OCamlElementType("MODULE_DEFINITION");
    @NotNull IElementType MODULE_TYPE_DEFINITION = new OCamlElementType("MODULE_TYPE_DEFINITION");
    @NotNull IElementType FUNCTOR_MODULE_TYPE = new OCamlElementType("FUNCTOR_MODULE_TYPE");
    @NotNull IElementType SIG_END_MODULE_TYPE = new OCamlElementType("SIG_END_MODULE_TYPE");
    @NotNull IElementType MODULE_TYPE_TYPE_CONSTRAINT = new OCamlElementType("MODULE_TYPE_TYPE_CONSTRAINT");
    @NotNull IElementType MODULE_TYPE_MODULE_CONSTRAINT = new OCamlElementType("MODULE_TYPE_MODULE_CONSTRAINT");
    @NotNull IElementType FUNCTOR_APPLICATION_MODULE_EXPRESSION = new OCamlElementType("FUNCTOR_APPLICATION_MODULE_EXPRESSION");
    @NotNull IElementType STRUCT_END_MODULE_EXPRESSION = new OCamlElementType("STRUCT_END_MODULE_EXPRESSION");
    @NotNull IElementType FUNCTOR_MODULE_EXPRESSION = new OCamlElementType("FUNCTOR_MODULE_EXPRESSION");
    @NotNull IElementType MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION = new OCamlElementType("MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION");
    @NotNull IElementType MODULE_TYPE_WITH_CONSTRAINTS = new OCamlElementType("MODULE_TYPE_WITH_CONSTRAINTS");
    @NotNull IElementType MODULE_TYPE_SPECIFICATION = new OCamlElementType("MODULE_TYPE_SPECIFICATION");
    @NotNull IElementType MODULE_SPECIFICATION = new OCamlElementType("MODULE_SPECIFICATION");
    @NotNull IElementType MODULE_TYPE_DEFINITION_BINDING = new OCamlElementType("MODULE_TYPE_DEFINITION_BINDING");
    @NotNull IElementType MODULE_TYPE_SPECIFICATION_BINDING = new OCamlElementType("MODULE_TYPE_SPECIFICATION_BINDING");
    @NotNull IElementType MODULE_DEFINITION_BINDING = new OCamlElementType("MODULE_DEFINITION_BINDING");
    @NotNull IElementType MODULE_SPECIFICATION_BINDING = new OCamlElementType("MODULE_SPECIFICATION_BINDING");
    @NotNull IElementType MODULE_PARAMETER = new OCamlElementType("MODULE_PARAMETER");
    @NotNull IElementType FILE_MODULE_DEFINITION_BINDING = new OCamlElementType("FILE_MODULE_DEFINITION_BINDING");
    @NotNull IElementType FILE_MODULE_EXPRESSION = new OCamlElementType("FILE_MODULE_EXPRESSION");
    @NotNull IElementType FILE_MODULE_SPECIFICATION_BINDING = new OCamlElementType("FILE_MODULE_SPECIFICATION_BINDING");
    @NotNull IElementType FILE_MODULE_TYPE = new OCamlElementType("FILE_MODULE_TYPE");
    @NotNull IElementType PARENTHESES_MODULE_EXPRESSION = new OCamlElementType("PARENTHESES_MODULE_EXPRESSION");
    @NotNull IElementType PARENTHESES_MODULE_TYPE = new OCamlElementType("PARENTHESES_MODULE_TYPE");

    /* } */

    /* Name parsing { */

    @NotNull IElementType VALUE_NAME = new OCamlElementType("VALUE_NAME");
    @NotNull IElementType OPERATOR_NAME = new OCamlElementType("OPERATOR_NAME");
    @NotNull IElementType MODULE_PATH = new OCamlElementType("MODULE_PATH");
    @NotNull IElementType MODULE_NAME = new OCamlElementType("MODULE_NAME");
    @NotNull IElementType CONSTRUCTOR_PATH_EXPRESSION = new OCamlElementType("CONSTRUCTOR_PATH_EXPRESSION");
    @NotNull IElementType CONSTRUCTOR_NAME_EXPRESSION = new OCamlElementType("CONSTRUCTOR_NAME_EXPRESSION");
    @NotNull IElementType CONSTRUCTOR_PATH_PATTERN = new OCamlElementType("CONSTRUCTOR_PATH_PATTERN");
    @NotNull IElementType CONSTRUCTOR_NAME_PATTERN = new OCamlElementType("CONSTRUCTOR_NAME_PATTERN");
    @NotNull IElementType TYPE_CONSTRUCTOR_NAME = new OCamlElementType("TYPE_CONSTRUCTOR_NAME");
    @NotNull IElementType INST_VAR_NAME_DEFINITION = new OCamlElementType("INST_VAR_NAME_DEFINITION");
    @NotNull IElementType CONSTRUCTOR_NAME_DEFINITION = new OCamlElementType("CONSTRUCTOR_NAME_DEFINITION");
    @NotNull IElementType CONSTRUCTOR_NAME = new OCamlElementType("CONSTRUCTOR_NAME");
    @NotNull IElementType CONSTRUCTOR_PATH = new OCamlElementType("CONSTRUCTOR_PATH");
    @NotNull IElementType TYPE_PARAMETER_NAME = new OCamlElementType("TYPE_PARAMETER_NAME");
    @NotNull IElementType FIELD_NAME = new OCamlElementType("FIELD_NAME");
    @NotNull IElementType CLASS_PATH = new OCamlElementType("CLASS_PATH");
    @NotNull IElementType CLASS_NAME = new OCamlElementType("CLASS_NAME");
    @NotNull IElementType METHOD_NAME = new OCamlElementType("METHOD_NAME");
    @NotNull IElementType INST_VAR_NAME = new OCamlElementType("INST_VAR_NAME");
    @NotNull IElementType LABEL_NAME = new OCamlElementType("LABEL_NAME");
    @NotNull IElementType MODULE_TYPE_NAME = new OCamlElementType("MODULE_TYPE_NAME");
    @NotNull IElementType EXTENDED_MODULE_PATH = new OCamlElementType("EXTENDED_MODULE_PATH");
    @NotNull IElementType EXTENDED_MODULE_NAME = new OCamlElementType("EXTENDED_MODULE_NAME");
    @NotNull IElementType TYPE_CONSTRUCTOR_PATH = new OCamlElementType("TYPE_CONSTRUCTOR_PATH");
    @NotNull IElementType MODULE_TYPE_PATH = new OCamlElementType("MODULE_TYPE_PATH");
    @NotNull IElementType TAG_NAME = new OCamlElementType("TAG_NAME");
    @NotNull IElementType VALUE_PATH = new OCamlElementType("VALUE_PATH");
    @NotNull IElementType FIELD_PATH = new OCamlElementType("FIELD_PATH");
    @NotNull IElementType FOR_EXPRESSION_INDEX_VARIABLE_NAME = new OCamlElementType("FOR_EXPRESSION_INDEX_VARIABLE_NAME");

    /* } */

    /* Expression parsing { */

    @NotNull IElementType MATCH_EXPRESSION = new OCamlElementType("MATCH_EXPRESSION");
    @NotNull IElementType FUN_EXPRESSION = new OCamlElementType("FUN_EXPRESSION");
    @NotNull IElementType FUNCTION_EXPRESSION = new OCamlElementType("FUNCTION_EXPRESSION");
    @NotNull IElementType TRY_EXPRESSION = new OCamlElementType("TRY_EXPRESSION");
    @NotNull IElementType PATTERN_MATCHING = new OCamlElementType("PATTERN_MATCHING");
    @NotNull IElementType SEMICOLON_EXPRESSION = new OCamlElementType("SEMICOLON_EXPRESSION");
    @NotNull IElementType IF_EXPRESSION = new OCamlElementType("IF_EXPRESSION");
    @NotNull IElementType FOR_EXPRESSION = new OCamlElementType("FOR_EXPRESSION");
    @NotNull IElementType FOR_EXPRESSION_BINDING = new OCamlElementType("FOR_EXPRESSION_BINDING");
    @NotNull IElementType WHILE_EXPRESSION = new OCamlElementType("WHILE_EXPRESSION");
    @NotNull IElementType ASSIGNMENT_EXPRESSION = new OCamlElementType("ASSIGNMENT_EXPRESSION");
    @NotNull IElementType COMMA_EXPRESSION = new OCamlElementType("COMMA_EXPRESSION");
    @NotNull IElementType BINARY_EXPRESSION = new OCamlElementType("BINARY_EXPRESSION");
    @NotNull IElementType HEAD_TAIL_EXPRESSION = new OCamlElementType("HEAD_TAIL_EXPRESSION");
    @NotNull IElementType ASSERT_EXPRESSION = new OCamlElementType("ASSERT_EXPRESSION");
    @NotNull IElementType LAZY_EXPRESSION = new OCamlElementType("LAZY_EXPRESSION");
    @NotNull IElementType CONSTRUCTOR_APPLICATION_EXPRESSION = new OCamlElementType("CONSTRUCTOR_APPLICATION_EXPRESSION");
    @NotNull IElementType FUNCTION_APPLICATION_EXPRESSION = new OCamlElementType("FUNCTION_APPLICATION_EXPRESSION");
    @NotNull IElementType ARRAY_ELEMENT_ACCESSING_EXPRESSION = new OCamlElementType("ARRAY_ELEMENT_ACCESSING_EXPRESSION");
    @NotNull IElementType STRING_CHAR_ACCESSING_EXPRESSION = new OCamlElementType("STRING_CHAR_ACCESSING_EXPRESSION");
    @NotNull IElementType RECORD_FIELD_ACCESSING_EXPRESSION = new OCamlElementType("RECORD_FIELD_ACCESSING_EXPRESSION");
    @NotNull IElementType RECORD_FIELD_INITIALIZATION_IN_EXPRESSION = new OCamlElementType("RECORD_FIELD_INITIALIZATION_IN_EXPRESSION");
    @NotNull IElementType RECORD_FIELD_INITIALIZATION_IN_PATTERN = new OCamlElementType("RECORD_FIELD_INITIALIZATION_IN_PATTERN");
    @NotNull IElementType UNARY_EXPRESSION = new OCamlElementType("UNARY_EXPRESSION");
    @NotNull IElementType CLASS_METHOD_ACCESSING_EXPRESSION = new OCamlElementType("CLASS_METHOD_ACCESSING_EXPRESSION");
    @NotNull IElementType TAGGED_EXPRESSION = new OCamlElementType("TAGGED_EXPRESSION");
    @NotNull IElementType TYPE_CONSTRAINT_EXPRESSION = new OCamlElementType("TYPE_CONSTRAINT_EXPRESSION");
    @NotNull IElementType CASTING_EXPRESSION = new OCamlElementType("CASTING_EXPRESSION");
    @NotNull IElementType LIST_EXPRESSION = new OCamlElementType("LIST_EXPRESSION");
    @NotNull IElementType ARRAY_EXPRESSION = new OCamlElementType("ARRAY_EXPRESSION");
    @NotNull IElementType RECORD_EXPRESSION = new OCamlElementType("RECORD_EXPRESSION");
    @NotNull IElementType INHERITED_RECORD_EXPRESSION = new OCamlElementType("INHERITED_RECORD_EXPRESSION");
    @NotNull IElementType INSTANCE_DUPLICATING_EXPRESSION = new OCamlElementType("INSTANCE_DUPLICATING_EXPRESSION");
    @NotNull IElementType OBJECT_CLASS_BODY_END_EXPRESSION = new OCamlElementType("OBJECT_CLASS_BODY_END_EXPRESSION");
    @NotNull IElementType NEW_INSTANCE_EXPRESSION = new OCamlElementType("NEW_INSTANCE_EXPRESSION");
    @NotNull IElementType ARGUMENT = new OCamlElementType("ARGUMENT");
    @NotNull IElementType PARAMETER = new OCamlElementType("PARAMETER");
    @NotNull IElementType LABEL_DEFINITION = new OCamlElementType("LABEL_DEFINITION");
    @NotNull IElementType PARENTHESES_EXPRESSION = new OCamlElementType("PARENTHESES_EXPRESSION");
    @NotNull IElementType EXPRESSION_STATEMENT = new OCamlElementType("EXPRESSION_STATEMENT");
    @NotNull IElementType CONSTANT_EXPRESSION = new OCamlElementType("CONSTANT_EXPRESSION");

    /* } */

    /* Pattern parsing { */

    @NotNull IElementType VALUE_NAME_PATTERN = new OCamlElementType("VALUE_NAME_PATTERN");
    @NotNull IElementType AS_PATTERN = new OCamlElementType("AS_PATTERN");
    @NotNull IElementType OR_PATTERN = new OCamlElementType("OR_PATTERN");
    @NotNull IElementType COMMA_PATTERN = new OCamlElementType("COMMA_PATTERN");
    @NotNull IElementType HEAD_TAIL_PATTERN = new OCamlElementType("HEAD_TAIL_PATTERN");
    @NotNull IElementType CONSTRUCTOR_APPLICATION_PATTERN = new OCamlElementType("CONSTRUCTOR_APPLICATION_PATTERN");
    @NotNull IElementType TAGGED_PATTERN = new OCamlElementType("TAGGED_PATTERN");
    @NotNull IElementType TYPE_CONSTRUCTOR_PATTERN = new OCamlElementType("TYPE_CONSTRUCTOR_PATTERN");
    @NotNull IElementType LAZY_PATTERN = new OCamlElementType("LAZY_PATTERN");
    @NotNull IElementType LIST_PATTERN = new OCamlElementType("LIST_PATTERN");
    @NotNull IElementType ARRAY_PATTERN = new OCamlElementType("ARRAY_PATTERN");
    @NotNull IElementType RECORD_PATTERN = new OCamlElementType("RECORD_PATTERN");
    @NotNull IElementType TYPE_CONSTRAINT_PATTERN = new OCamlElementType("TYPE_CONSTRAINT_PATTERN");
    @NotNull IElementType CHAR_RANGE_PATTERN = new OCamlElementType("CHAR_RANGE_PATTERN");
    @NotNull IElementType PARENTHESES_PATTERN = new OCamlElementType("PARENTHESES_PATTERN");
    @NotNull IElementType UNDERSCORE_PATTERN = new OCamlElementType("UNDERSCORE_PATTERN");
    @NotNull IElementType CONSTANT_PATTERN = new OCamlElementType("CONSTANT_PATTERN");

    /* } */

    /* Comments { */

    @NotNull IElementType COMMENT_BLOCK = new OCamlElementType("COMMENT_BLOCK");
    @NotNull IElementType UNCLOSED_COMMENT = new OCamlElementType("UNCLOSED_COMMENT");

    /* } */

    @NotNull IElementType PARENTHESES = new OCamlElementType("PARENTHESES");
    @NotNull IElementType BRACKETS = new OCamlElementType("BRACKETS");
}

