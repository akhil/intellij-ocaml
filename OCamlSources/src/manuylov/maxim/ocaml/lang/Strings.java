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

package manuylov.maxim.ocaml.lang;

import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 28.02.2009
 */
public interface Strings {
    @NotNull String CLASS_EXPECTED = "'class' expected";
    @NotNull String CLASS_TYPE_EXPECTED = "Class type expected";
    @NotNull String TYPE_EXPECTED = "'type' expected";
    @NotNull String INITIALIZER_KEYWORD_EXPECTED = "'initializer' expected";
    @NotNull String METHOD_KEYWORD_EXPECTED = "'method' expected";
    @NotNull String OBJECT_KEYWORD_EXPECTED = "'object' expected";
    @NotNull String INHERIT_KEYWORD_EXPECTED = "'inherit' expected";
    @NotNull String CLASS_KEYWORD_EXPECTED = "'class' expected";
    @NotNull String LBRACE_LT_EXPECTED = "'{<' expected";
    @NotNull String GT_RBRACE_EXPECTED = "'>}' expected";
    @NotNull String LBRACKET_VBAR_EXPECTED = "'[|' expected";
    @NotNull String LBRACKET_EXPECTED = "'[' expected";
    @NotNull String WHILE_KEYWORD_EXPECTED = "'while' expected";
    @NotNull String FOR_KEYWORD_EXPECTED = "'for' expected";
    @NotNull String DO_KEYWORD_EXPECTED = "'do' expected";
    @NotNull String DONE_KEYWORD_EXPECTED = "'done' expected";
    @NotNull String IF_KEYWORD_EXPECTED = "'if' expected";
    @NotNull String THEN_KEYWORD_EXPECTED = "'then' expected";
    @NotNull String TRY_KEYWORD_EXPECTED = "'try' expected";
    @NotNull String FUNCTION_KEYWORD_EXPECTED = "'function' expected";
    @NotNull String FUN_KEYWORD_EXPECTED = "'fun' expected";
    @NotNull String MATCH_KEYWORD_EXPECTED = "'match' expected";
    @NotNull String WITH_KEYWORD_EXPECTED = "'with' expected";
    @NotNull String ASSERT_KEYWORD_EXPECTED = "'assert' expected";
    @NotNull String LAZY_KEYWORD_EXPECTED = "'lazy' expected";
    @NotNull String END_KEYWORD_EXPECTED = "'end' expected";
    @NotNull String LET_KEYWORD_EXPECTED = "'let' expected";
    @NotNull String IN_KEYWORD_EXPECTED = "'in' expected";
    @NotNull String FUNCTOR_KEYWORD_EXPECTED = "'functor' expected";
    @NotNull String SIG_KEYWORD_EXPECTED = "'sig' expected";
    @NotNull String MINUS_GT_EXPECTED = "'->' expected";
    @NotNull String LPAR_EXPECTED = "'(' expected";
    @NotNull String MODULE_KEYWORD_EXPECTED = "'module' expected";
    @NotNull String VBAR_RBRACKET_EXPECTED = "'|]' expected";
    @NotNull String VAL_KEYWORD_EXPECTED = "'val' expected";
    @NotNull String INCLUDE_KEYWORD_EXPECTED = "'include' expected";
    @NotNull String OPEN_KEYWORD_EXPECTED = "'open' expected";
    @NotNull String EXTERNAL_KEYWORD_EXPECTED = "'external' expected";
    @NotNull String EXCEPTION_KEYWORD_EXPECTED = "'exception' expected";
    @NotNull String TYPE_KEYWORD_EXPECTED = "'type' expected";
    @NotNull String CONSTRAINT_KEYWORD_EXPECTED = "'constraint' expected";
    @NotNull String QUOTE_EXPECTED = "''' expected";
    @NotNull String EQ_EXPECTED = "'=' expected";
    @NotNull String LBRACE_EXPECTED = "'{' expected";
    @NotNull String RBRACE_EXPECTED = "'}' expected";
    @NotNull String COLON_EXPECTED = "':' expected";
    @NotNull String RPAR_EXPECTED = "')' expected";
    @NotNull String ACCENT_EXPECTED = "'`' expected";
    @NotNull String RBRACKET_EXPECTED = "']' expected";
    @NotNull String GT_EXPECTED = "'>' expected";
    @NotNull String CHAR_LITERAL_EXPECTED = "Character literal expected";
    @NotNull String ILLEGAL_CHAR_LITERAL = "Illegal character literal";
    @NotNull String DOT_EXPECTED = "'.' expected";
    @NotNull String SEMICOLON_SEMICOLON_EXPECTED = "';;' expected";
    @NotNull String INHERIT_OR_VAL_OR_METHOD_OR_CONSTRAINT_OR_INITIALIZER_EXPECTED = "'inherit', 'val', 'method', 'constraint' or 'initializer' expected";
    @NotNull String EXPRESSION_EXPECTED = "Expression expected";
    @NotNull String RPAR_OR_COLON_GT_EXPECTED = "')' or ':>' expected";
    @NotNull String RPAR_OR_COLON_OR_COLON_GT_EXPECTED = "')', ':' or ':>' expected";
    @NotNull String BEGIN_OR_LPAR_EXPECTED = "'begin' or '(' expected";
    @NotNull String TO_OR_DOWNTO_EXPECTED = "'to' or 'downto' expected";
    @NotNull String TYPE_OR_MODULE_EXPECTED = "'type' or 'module' expected";
    @NotNull String MODULE_NAME_EXPECTED = "Module name expected";
    @NotNull String MODULE_TYPE_EXPECTED = "Module type expected";
    @NotNull String CONSTRUCTOR_PATH_EXPECTED = "Constructor path expected";
    @NotNull String IDENTIFIER_EXPECTED = "Identifier expected";
    @NotNull String CLASS_PATH_EXPECTED = "Class path expected";
    @NotNull String METHOD_NAME_EXPECTED = "Method name expected";
    @NotNull String LABEL_NAME_EXPECTED = "Label name expected";
    @NotNull String MODULE_TYPE_NAME_EXPECTED = "Module type name expected";
    @NotNull String MODULE_PATH_EXPECTED = "Module path expected";
    @NotNull String MODULE_EXPRESSION_EXPECTED = "Module expression expected";
    @NotNull String EXTENDED_MODULE_PATH_EXPECTED = "Extended module path expected";
    @NotNull String EXTENDED_MODULE_NAME_EXPECTED = "Extended module name expected";
    @NotNull String TYPE_CONSTRUCTOR_PATH_EXPECTED = "Type constructor path expected";
    @NotNull String MODULE_TYPE_PATH_EXPECTED = "Module type path expected";
    @NotNull String TYPE_CONSTRUCTOR_NAME_EXPECTED = "Type constructor name expected";
    @NotNull String TAG_NAME_EXPECTED = "Tag name expected";
    @NotNull String CLASS_NAME_EXPECTED = "Class name expected";
    @NotNull String CONSTRUCTOR_NAME_EXPECTED = "Constructor name expected";
    @NotNull String INSTANCE_VARIABLE_NAME_EXPECTED = "Instance variable name expected";
    @NotNull String VALUE_NAME_EXPECTED = "Value name expected";
    @NotNull String FIELD_NAME_EXPECTED = "Field name expected";
    @NotNull String FIELD_PATH_EXPECTED = "Field path expected";
    @NotNull String PATTERN_EXPECTED = "Pattern expected";
    @NotNull String RPAR_OR_COLON_EXPECTED = "')' or ':' expected";
    @NotNull String SPECIFICATION_EXPECTED = "Specification expected";
    @NotNull String TYPE_EXPRESSION_EXPECTED = "Type expression expected";
    @NotNull String POLYMORPHIC_TYPE_EXPRESSION_EXPECTED = "Polymorphic type expression expected";
    @NotNull String TYPE_CONSTRUCTOR_OR_HASH_EXPECTED = "Type constructor or '#' expected";
    @NotNull String STRUCT_KEYWORD_EXPECTED = "'struct' expected";
    @NotNull String UNEXPECTED_TOKEN = "Unexpected token";
    @NotNull String DEFINITION_OR_EXPRESSION_EXPECTED = "Definition or expression expected";
    @NotNull String UNCLOSED_COMMENT = "Unclosed comment";
    @NotNull String UNEXPECTED_END_OF_FILE = "Unexpected end of file";
    @NotNull String CLASS_EXPRESSION_EXPECTED = "Class expression expected";
    @NotNull String INDEX_VARIABLE_NAME_EXPECTED = "Index variable name expected";
}
