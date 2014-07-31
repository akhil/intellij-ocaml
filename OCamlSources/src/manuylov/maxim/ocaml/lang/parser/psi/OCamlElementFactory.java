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

package manuylov.maxim.ocaml.lang.parser.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.parser.psi.element.impl.*;
import org.jetbrains.annotations.NotNull;

import static manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlElementFactory {
    private OCamlElementFactory() {}
    @NotNull public final static OCamlElementFactory INSTANCE = new OCamlElementFactory();

    @NotNull
    public OCamlElement createElement(@NotNull final ASTNode node) {
        final IElementType elementType = node.getElementType();

        if (elementType == LET_BINDING) {
            return new OCamlLetBindingImpl(node);
        }
        else if (elementType == LET_BINDING_PATTERN) {
            return new OCamlLetBindingPatternImpl(node);
        }
        else if (elementType == LET_STATEMENT) {
            return new OCamlLetStatementImpl(node);
        }
        else if (elementType == LET_EXPRESSION) {
            return new OCamlLetExpressionImpl(node);
        }
        else if (elementType == LET_CLASS_EXPRESSION) {
            return new OCamlLetClassExpressionImpl(node);
        }
        else if (elementType == EXTERNAL_DEFINITION) {
            return new OCamlExternalDefinitionImpl(node);
        }
        else if (elementType == EXCEPTION_DEFINITION) {
            return new OCamlExceptionDefinitionImpl(node);
        }
        else if (elementType == OPEN_DIRECTIVE) {
            return new OCamlOpenDirectiveImpl(node);
        }
        else if (elementType == INCLUDE_DIRECTIVE_DEFINITION) {
            return new OCamlIncludeDirectiveDefinitionImpl(node);
        }
        else if (elementType == EXCEPTION_SPECIFICATION) {
            return new OCamlExceptionSpecificationImpl(node);
        }
        else if (elementType == INCLUDE_DIRECTIVE_SPECIFICATION) {
            return new OCamlIncludeDirectiveSpecificationImpl(node);
        }
        else if (elementType == VALUE_SPECIFICATION) {
            return new OCamlValueSpecificationImpl(node);
        }
        else if (elementType == EXTERNAL_DECLARATION) {
            return new OCamlExternalDeclarationImpl(node);
        }
        else if (elementType == TYPE_DEFINITION) {
            return new OCamlTypeDefinitionImpl(node);
        }
        else if (elementType == TYPE_BINDING) {
            return new OCamlTypeBindingImpl(node);
        }
        else if (elementType == TYPE_PARAMETERIZED_BINDING) {
            return new OCamlTypeParameterizedBindingImpl(node);
        }
        else if (elementType == TYPE_PARAMETER) {
            return new OCamlTypeParameterImpl(node);
        }
        else if (elementType == TYPE_PARAMETER_DEFINITION) {
            return new OCamlTypeParameterDefinitionImpl(node);
        }
        else if (elementType == TYPE_PARAMETER_NAME) {
            return new OCamlTypeParameterNameImpl(node);
        }
        else if (elementType == PLUS_MINUS_TYPE_PARAMETER) {
            return new OCamlPlusMinusTypeParameterImpl(node);
        }
        else if (elementType == RECORD_TYPE_DEFINITION) {
            return new OCamlRecordTypeDefinitionImpl(node);
        }
        else if (elementType == RECORD_FIELD_DEFINITION) {
            return new OCamlRecordFieldDefinitionImpl(node);
        }
        else if (elementType == VARIANT_TYPE_DEFINITION) {
            return new OCamlVariantTypeDefinitionImpl(node);
        }
        else if (elementType == CONSTRUCTOR_DEFINITION) {
            return new OCamlConstructorDefinitionImpl(node);
        }
        else if (elementType == TYPE_DEFINITION_CONSTRAINT) {
            return new OCamlTypeDefinitionConstraintImpl(node);
        }
        else if (elementType == AS_TYPE_EXPRESSION) {
            return new OCamlAsTypeExpressionImpl(node);
        }
        else if (elementType == FUNCTION_TYPE_EXPRESSION) {
            return new OCamlFunctionTypeExpressionImpl(node);
        }
        else if (elementType == TUPLE_TYPE_EXPRESSION) {
            return new OCamlTupleTypeExpressionImpl(node);
        }
        else if (elementType == SUPER_CLASS_TYPE_EXPRESSION) {
            return new OCamlSuperClassTypeExpressionImpl(node);
        }
        else if (elementType == TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION) {
            return new OCamlTypeConstructorApplicationTypeExpressionImpl(node);
        }
        else if (elementType == OBJECT_INTERFACE_TYPE_EXPRESSION) {
            return new OCamlObjectInterfaceTypeExpressionImpl(node);
        }
        else if (elementType == METHOD_TYPE) {
            return new OCamlMethodTypeImpl(node);
        }
        else if (elementType == VARIANT_TYPE_TYPE_EXPRESSION) {
            return new OCamlVariantTypeTypeExpressionImpl(node);
        }
        else if (elementType == TAG_SPEC) {
            return new OCamlTagSpecImpl(node);
        }
        else if (elementType == TAG_SPEC_FULL) {
            return new OCamlTagSpecFullImpl(node);
        }
        else if (elementType == POLY_TYPE_EXPRESSION) {
            return new OCamlPolyTypeExpressionImpl(node);
        }
        else if (elementType == CLASS_DEFINITION) {
            return new OCamlClassDefinitionImpl(node);
        }
        else if (elementType == CLASS_BINDING) {
            return new OCamlClassBindingImpl(node);
        }
        else if (elementType == CLASS_TYPE_DEFINITION) {
            return new OCamlClassTypeDefinitionImpl(node);
        }
        else if (elementType == CLASS_TYPE_BINDING) {
            return new OCamlClassTypeBindingImpl(node);
        }
        else if (elementType == FUNCTION_CLASS_TYPE) {
            return new OCamlFunctionClassTypeImpl(node);
        }
        else if (elementType == CLASS_PATH_APPLICATION) {
            return new OCamlClassPathApplicationImpl(node);
        }
        else if (elementType == OBJECT_END_CLASS_BODY_TYPE) {
            return new OCamlObjectEndClassBodyTypeImpl(node);
        }
        else if (elementType == INHERIT_CLASS_FILED_SPECIFICATION) {
            return new OCamlInheritClassFiledSpecificationImpl(node);
        }
        else if (elementType == VALUE_CLASS_FIELD_SPECIFICATION) {
            return new OCamlValueClassFieldSpecificationImpl(node);
        }
        else if (elementType == METHOD_CLASS_FIELD_SPECIFICATION) {
            return new OCamlMethodClassFieldSpecificationImpl(node);
        }
        else if (elementType == CONSTRAINT_CLASS_FIELD_SPECIFICATION) {
            return new OCamlConstraintClassFieldSpecificationImpl(node);
        }
        else if (elementType == FUNCTION_APPLICATION_CLASS_EXPRESSION) {
            return new OCamlFunctionApplicationClassExpressionImpl(node);
        }
        else if (elementType == OBJECT_END_CLASS_EXPRESSION) {
            return new OCamlObjectEndClassExpressionImpl(node);
        }
        else if (elementType == OBJECT_SELF_DEFINITION) {
            return new OCamlObjectSelfDefinitionImpl(node);
        }
        else if (elementType == OBJECT_SELF_SPECIFICATION) {
            return new OCamlObjectSelfSpecificationImpl(node);
        }
        else if (elementType == INHERIT_CLASS_FIELD_DEFINITION) {
            return new OCamlInheritClassFieldDefinitionImpl(node);
        }
        else if (elementType == INITIALIZER_CLASS_FIELD_DEFINITION) {
            return new OCamlInitializerClassFieldDefinitionImpl(node);
        }
        else if (elementType == CONSTRAINT_CLASS_FIELD_DEFINITION) {
            return new OCamlConstraintClassFieldDefinitionImpl(node);
        }
        else if (elementType == VALUE_CLASS_FIELD_DEFINITION) {
            return new OCamlValueClassFieldDefinitionImpl(node);
        }
        else if (elementType == METHOD_CLASS_FIELD_DEFINITION) {
            return new OCamlMethodClassFieldDefinitionImpl(node);
        }
        else if (elementType == FUNCTION_CLASS_EXPRESSION) {
            return new OCamlFunctionClassExpressionImpl(node);
        }
        else if (elementType == CLASS_SPECIFICATION) {
            return new OCamlClassSpecificationImpl(node);
        }
        else if (elementType == CLASS_SPECIFICATION_BINDING) {
            return new OCamlClassSpecificationBindingImpl(node);
        }
        else if (elementType == CLASS_TYPE_CONSTRAINT) {
            return new OCamlClassTypeConstraintImpl(node);
        }
        else if (elementType == MODULE_DEFINITION) {
            return new OCamlModuleDefinitionImpl(node);
        }
        else if (elementType == MODULE_TYPE_DEFINITION_BINDING) {
            return new OCamlModuleTypeDefinitionBindingImpl(node);
        }
        else if (elementType == MODULE_TYPE_SPECIFICATION_BINDING) {
            return new OCamlModuleTypeSpecificationBindingImpl(node);
        }
        else if (elementType == MODULE_DEFINITION_BINDING) {
            return new OCamlModuleDefinitionBindingImpl(node);
        }
        else if (elementType == MODULE_SPECIFICATION_BINDING) {
            return new OCamlModuleSpecificationBindingImpl(node);
        }
        else if (elementType == MODULE_PARAMETER) {
            return new OCamlModuleParameterImpl(node);
        }
        else if (elementType == FILE_MODULE_DEFINITION_BINDING) {
            return new OCamlFileModuleDefinitionBindingImpl(node);
        }
        else if (elementType == FILE_MODULE_EXPRESSION) {
            return new OCamlFileModuleExpressionImpl(node);
        }
        else if (elementType == FILE_MODULE_SPECIFICATION_BINDING) {
            return new OCamlFileModuleSpecificationBindingImpl(node);
        }
        else if (elementType == FILE_MODULE_TYPE) {
            return new OCamlFileModuleTypeImpl(node);
        }
        else if (elementType == MODULE_TYPE_DEFINITION) {
            return new OCamlModuleTypeDefinitionImpl(node);
        }
        else if (elementType == FUNCTOR_MODULE_TYPE) {
            return new OCamlFunctorModuleTypeImpl(node);
        }
        else if (elementType == SIG_END_MODULE_TYPE) {
            return new OCamlSigEndModuleTypeImpl(node);
        }
        else if (elementType == MODULE_TYPE_TYPE_CONSTRAINT) {
            return new OCamlModuleTypeTypeConstraintImpl(node);
        }
        else if (elementType == MODULE_TYPE_MODULE_CONSTRAINT) {
            return new OCamlModuleTypeModuleConstraintImpl(node);
        }
        else if (elementType == FUNCTOR_APPLICATION_MODULE_EXPRESSION) {
            return new OCamlFunctorApplicationModuleExpressionImpl(node);
        }
        else if (elementType == STRUCT_END_MODULE_EXPRESSION) {
            return new OCamlStructEndModuleExpressionImpl(node);
        }
        else if (elementType == FUNCTOR_MODULE_EXPRESSION) {
            return new OCamlFunctorModuleExpressionImpl(node);
        }
        else if (elementType == MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION) {
            return new OCamlModuleTypeConstraintModuleExpressionImpl(node);
        }
        else if (elementType == MODULE_TYPE_WITH_CONSTRAINTS) {
            return new OCamlModuleTypeWithConstraintsImpl(node);
        }
        else if (elementType == MODULE_TYPE_SPECIFICATION) {
            return new OCamlModuleTypeSpecificationImpl(node);
        }
        else if (elementType == MODULE_SPECIFICATION) {
            return new OCamlModuleSpecificationImpl(node);
        }
        else if (elementType == VALUE_NAME) {
            return new OCamlValueNameImpl(node);
        }
        else if (elementType == VALUE_NAME_PATTERN) {
            return new OCamlValueNamePatternImpl(node);
        }
        else if (elementType == INST_VAR_NAME_DEFINITION) {
            return new OCamlInstVarNameDefinitionImpl(node);
        }
        else if (elementType == CONSTRUCTOR_NAME_DEFINITION) {
            return new OCamlConstructorNameDefinitionImpl(node);
        }
        else if (elementType == CONSTRUCTOR_NAME) {
            return new OCamlConstructorNameImpl(node);
        }
        else if (elementType == CONSTRUCTOR_PATH) {
            return new OCamlConstructorPathImpl(node);
        }
        else if (elementType == OPERATOR_NAME) {
            return new OCamlOperatorNameImpl(node);
        }
        else if (elementType == MODULE_PATH) {
            return new OCamlModulePathImpl(node);
        }
        else if (elementType == MODULE_NAME) {
            return new OCamlModuleNameImpl(node);
        }
        else if (elementType == CONSTRUCTOR_PATH_EXPRESSION) {
            return new OCamlConstructorPathExpressionImpl(node);
        }
        else if (elementType == CONSTRUCTOR_NAME_EXPRESSION) {
            return new OCamlConstructorNameExpressionImpl(node);
        }
        else if (elementType == CONSTRUCTOR_PATH_PATTERN) {
            return new OCamlConstructorPathPatternImpl(node);
        }
        else if (elementType == CONSTRUCTOR_NAME_PATTERN) {
            return new OCamlConstructorNamePatternImpl(node);
        }
        else if (elementType == TYPE_CONSTRUCTOR_NAME) {
            return new OCamlTypeConstructorNameImpl(node);
        }
        else if (elementType == FIELD_NAME) {
            return new OCamlFieldNameImpl(node);
        }
        else if (elementType == CLASS_PATH) {
            return new OCamlClassPathImpl(node);
        }
        else if (elementType == CLASS_NAME) {
            return new OCamlClassNameImpl(node);
        }
        else if (elementType == METHOD_NAME) {
            return new OCamlMethodNameImpl(node);
        }
        else if (elementType == INST_VAR_NAME) {
            return new OCamlInstVarNameImpl(node);
        }
        else if (elementType == LABEL_NAME) {
            return new OCamlLabelNameImpl(node);
        }
        else if (elementType == MODULE_TYPE_NAME) {
            return new OCamlModuleTypeNameImpl(node);
        }
        else if (elementType == EXTENDED_MODULE_PATH) {
            return new OCamlExtendedModulePathImpl(node);
        }
        else if (elementType == EXTENDED_MODULE_NAME) {
            return new OCamlExtendedModuleNameImpl(node);
        }
        else if (elementType == TYPE_CONSTRUCTOR_PATH) {
            return new OCamlTypeConstructorPathImpl(node);
        }
        else if (elementType == MODULE_TYPE_PATH) {
            return new OCamlModuleTypePathImpl(node);
        }
        else if (elementType == TAG_NAME) {
            return new OCamlTagNameImpl(node);
        }
        else if (elementType == VALUE_PATH) {
            return new OCamlValuePathImpl(node);
        }
        else if (elementType == FIELD_PATH) {
            return new OCamlFieldPathImpl(node);
        }
        else if (elementType == CONSTANT_EXPRESSION) {
            return new OCamlConstantExpressionImpl(node);
        }
        else if (elementType == CONSTANT_PATTERN) {
            return new OCamlConstantPatternImpl(node);
        }
        else if (elementType == MATCH_EXPRESSION) {
            return new OCamlMatchExpressionImpl(node);
        }
        else if (elementType == FUN_EXPRESSION) {
            return new OCamlFunExpressionImpl(node);
        }
        else if (elementType == FUNCTION_EXPRESSION) {
            return new OCamlFunctionExpressionImpl(node);
        }
        else if (elementType == TRY_EXPRESSION) {
            return new OCamlTryExpressionImpl(node);
        }
        else if (elementType == PATTERN_MATCHING) {
            return new OCamlPatternMatchingImpl(node);
        }
        else if (elementType == SEMICOLON_EXPRESSION) {
            return new OCamlSemicolonExpressionImpl(node);
        }
        else if (elementType == IF_EXPRESSION) {
            return new OCamlIfExpressionImpl(node);
        }
        else if (elementType == FOR_EXPRESSION) {
            return new OCamlForExpressionImpl(node);
        }
        else if (elementType == FOR_EXPRESSION_BINDING) {
            return new OCamlForExpressionBindingImpl(node);
        }
        else if (elementType == WHILE_EXPRESSION) {
            return new OCamlWhileExpressionImpl(node);
        }
        else if (elementType == ASSIGNMENT_EXPRESSION) {
            return new OCamlAssignmentExpressionImpl(node);
        }
        else if (elementType == COMMA_EXPRESSION) {
            return new OCamlCommaExpressionImpl(node);
        }
        else if (elementType == BINARY_EXPRESSION) {
            return new OCamlBinaryExpressionImpl(node);
        }
        else if (elementType == HEAD_TAIL_EXPRESSION) {
            return new OCamlHeadTailExpressionImpl(node);
        }
        else if (elementType == ASSERT_EXPRESSION) {
            return new OCamlAssertExpressionImpl(node);
        }
        else if (elementType == LAZY_EXPRESSION) {
            return new OCamlLazyExpressionImpl(node);
        }
        else if (elementType == CONSTRUCTOR_APPLICATION_EXPRESSION) {
            return new OCamlConstructorApplicationExpressionImpl(node);
        }
        else if (elementType == FUNCTION_APPLICATION_EXPRESSION) {
            return new OCamlFunctionApplicationExpressionImpl(node);
        }
        else if (elementType == ARRAY_ELEMENT_ACCESSING_EXPRESSION) {
            return new OCamlArrayElementAccessingExpressionImpl(node);
        }
        else if (elementType == STRING_CHAR_ACCESSING_EXPRESSION) {
            return new OCamlStringCharAccessingExpressionImpl(node);
        }
        else if (elementType == RECORD_FIELD_ACCESSING_EXPRESSION) {
            return new OCamlRecordFieldAccessingExpressionImpl(node);
        }
        else if (elementType == UNARY_EXPRESSION) {
            return new OCamlUnaryExpressionImpl(node);
        }
        else if (elementType == CLASS_METHOD_ACCESSING_EXPRESSION) {
            return new OCamlClassMethodAccessingExpressionImpl(node);
        }
        else if (elementType == TAGGED_EXPRESSION) {
            return new OCamlTaggedExpressionImpl(node);
        }
        else if (elementType == TYPE_CONSTRAINT_EXPRESSION) {
            return new OCamlTypeConstraintExpressionImpl(node);
        }
        else if (elementType == CASTING_EXPRESSION) {
            return new OCamlCastingExpressionImpl(node);
        }
        else if (elementType == LIST_EXPRESSION) {
            return new OCamlListExpressionImpl(node);
        }
        else if (elementType == ARRAY_EXPRESSION) {
            return new OCamlArrayExpressionImpl(node);
        }
        else if (elementType == RECORD_FIELD_INITIALIZATION_IN_EXPRESSION) {
            return new OCamlRecordFieldInitializationInExpressionImpl(node);
        }
        else if (elementType == RECORD_FIELD_INITIALIZATION_IN_PATTERN) {
            return new OCamlRecordFieldInitializationInPatternImpl(node);
        }
        else if (elementType == RECORD_EXPRESSION) {
            return new OCamlRecordExpressionImpl(node);
        }
        else if (elementType == INHERITED_RECORD_EXPRESSION) {
            return new OCamlInheritedRecordExpressionImpl(node);
        }
        else if (elementType == INSTANCE_DUPLICATING_EXPRESSION) {
            return new OCamlInstanceDuplicatingExpressionImpl(node);
        }
        else if (elementType == OBJECT_CLASS_BODY_END_EXPRESSION) {
            return new OCamlObjectClassBodyEndExpressionImpl(node);
        }
        else if (elementType == NEW_INSTANCE_EXPRESSION) {
            return new OCamlNewInstanceExpressionImpl(node);
        }
        else if (elementType == ARGUMENT) {
            return new OCamlArgumentImpl(node);
        }
        else if (elementType == PARAMETER) {
            return new OCamlParameterImpl(node);
        }
        else if (elementType == LABEL_DEFINITION) {
            return new OCamlLabelDefinitionImpl(node);
        }
        else if (elementType == AS_PATTERN) {
            return new OCamlAsPatternImpl(node);
        }
        else if (elementType == OR_PATTERN) {
            return new OCamlOrPatternImpl(node);
        }
        else if (elementType == COMMA_PATTERN) {
            return new OCamlCommaPatternImpl(node);
        }
        else if (elementType == HEAD_TAIL_PATTERN) {
            return new OCamlHeadTailPatternImpl(node);
        }
        else if (elementType == CONSTRUCTOR_APPLICATION_PATTERN) {
            return new OCamlConstructorApplicationPatternImpl(node);
        }
        else if (elementType == TAGGED_PATTERN) {
            return new OCamlTaggedPatternImpl(node);
        }
        else if (elementType == COMMENT_BLOCK) {
            return new OCamlCommentBlockImpl(node);
        }
        else if (elementType == UNCLOSED_COMMENT) {
            return new OCamlUnclosedCommentImpl(node);
        }
        else if (elementType == TYPE_CONSTRUCTOR_PATTERN) {
            return new OCamlTypeConstructorPatternImpl(node);
        }
        else if (elementType == LAZY_PATTERN) {
            return new OCamlLazyPatternImpl(node);
        }
        else if (elementType == LIST_PATTERN) {
            return new OCamlListPatternImpl(node);
        }
        else if (elementType == ARRAY_PATTERN) {
            return new OCamlArrayPatternImpl(node);
        }
        else if (elementType == RECORD_PATTERN) {
            return new OCamlRecordPatternImpl(node);
        }
        else if (elementType == TYPE_CONSTRAINT_PATTERN) {
            return new OCamlTypeConstraintPatternImpl(node);
        }
        else if (elementType == CHAR_RANGE_PATTERN) {
            return new OCamlCharRangePatternImpl(node);
        }
        else if (elementType == FOR_EXPRESSION_INDEX_VARIABLE_NAME) {
            return new OCamlForExpressionIndexVariableNameImpl(node);
        }
        else if (elementType == PARENTHESES_MODULE_TYPE) {
            return new OCamlParenthesesModuleTypeImpl(node);
        }
        else if (elementType == PARENTHESES_TYPE_EXPRESSION) {
            return new OCamlParenthesesTypeExpressionImpl(node);
        }
        else if (elementType == PARENTHESES_TYPE_PARAMETERS) {
            return new OCamlParenthesesTypeParametersImpl(node);
        }
        else if (elementType == PARENTHESES_CLASS_EXPRESSION) {
            return new OCamlParenthesesClassExpressionImpl(node);
        }
        else if (elementType == PARENTHESES_EXPRESSION) {
            return new OCamlParenthesesExpressionImpl(node);
        }
        else if (elementType == PARENTHESES_MODULE_EXPRESSION) {
            return new OCamlParenthesesModuleExpressionImpl(node);
        }
        else if (elementType == PARENTHESES_PATTERN) {
            return new OCamlParenthesesPatternImpl(node);
        }
        else if (elementType == PARENTHESES) {
            return new OCamlParenthesesImpl(node);
        }
        else if (elementType == BRACKETS) {
            return new OCamlBracketsImpl(node);
        }
        else if (elementType == UNDERSCORE_TYPE_EXPRESSION) {
            return new OCamlUnderscoreTypeExpressionImpl(node);
        }
        else if (elementType == UNDERSCORE_PATTERN) {
            return new OCamlUnderscorePatternImpl(node);
        }
        else if (elementType == EXPRESSION_STATEMENT) {
            return new OCamlExpressionStatementImpl(node);
        }
        else {
            return new OCamlUnknownElementImpl(node);
        }
    }
}
