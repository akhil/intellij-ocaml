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

import com.intellij.psi.PsiElementVisitor;
import manuylov.maxim.ocaml.lang.parser.psi.element.*;
import manuylov.maxim.ocaml.lang.parser.psi.element.impl.OCamlObjectSelfSpecificationImpl;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 22.03.2009
 */
public abstract class OCamlElementVisitor extends PsiElementVisitor {
    public abstract void visitLetBinding(@NotNull final OCamlLetBinding psiElement);

    public abstract void visitLetStatement(@NotNull final OCamlLetStatement psiElement);

    public abstract void visitLetExpression(@NotNull final OCamlLetExpression psiElement);

    public abstract void visitLetClassExpression(@NotNull final OCamlLetClassExpression psiElement);

    public abstract void visitExternalDefinition(@NotNull final OCamlExternalDefinition psiElement);

    public abstract void visitExceptionDefinition(@NotNull final OCamlExceptionDefinition psiElement);

    public abstract void visitOpenDirective(@NotNull final OCamlOpenDirective psiElement);

    public abstract void visitIncludeDirectiveDefinition(@NotNull final OCamlIncludeDirectiveDefinition psiElement);

    public abstract void visitExceptionSpecification(@NotNull final OCamlExceptionSpecification psiElement);

    public abstract void visitIncludeDirectiveSpecification(@NotNull final OCamlIncludeDirectiveSpecification psiElement);

    public abstract void visitValueSpecification(@NotNull final OCamlValueSpecification psiElement);

    public abstract void visitExternalDeclaration(@NotNull final OCamlExternalDeclaration psiElement);

    public abstract void visitTypeDefinition(@NotNull final OCamlTypeDefinition psiElement);

    public abstract void visitTypeBinding(@NotNull final OCamlTypeBinding psiElement);

    public abstract void visitTypeParameter(@NotNull final OCamlTypeParameter psiElement);

    public abstract void visitPlusMinusTypeParameter(@NotNull final OCamlPlusMinusTypeParameter psiElement);

    public abstract void visitRecordTypeDefinition(@NotNull final OCamlRecordTypeDefinition psiElement);

    public abstract void visitRecordFieldDefinition(@NotNull final OCamlRecordFieldDefinition psiElement);

    public abstract void visitRecordFieldInitializationInExpression(@NotNull final OCamlRecordFieldInitializationInExpression psiElement);

    public abstract void visitVariantTypeDefinition(@NotNull final OCamlVariantTypeDefinition psiElement);

    public abstract void visitConstructorDefinition(@NotNull final OCamlConstructorDefinition psiElement);

    public abstract void visitTypeDefinitionConstraint(@NotNull final OCamlTypeDefinitionConstraint psiElement);

    public abstract void visitAsTypeExpression(@NotNull final OCamlAsTypeExpression psiElement);

    public abstract void visitFunctionTypeExpression(@NotNull final OCamlFunctionTypeExpression psiElement);

    public abstract void visitTupleTypeExpression(@NotNull final OCamlTupleTypeExpression psiElement);

    public abstract void visitSuperClassTypeExpression(@NotNull final OCamlSuperClassTypeExpression psiElement);

    public abstract void visitTypeConstructorApplicationTypeExpression(@NotNull final OCamlTypeConstructorApplicationTypeExpression psiElement);

    public abstract void visitObjectInterfaceTypeExpression(@NotNull final OCamlObjectInterfaceTypeExpression psiElement);

    public abstract void visitMethodType(@NotNull final OCamlMethodType psiElement);

    public abstract void visitVariantTypeTypeExpression(@NotNull final OCamlVariantTypeTypeExpression psiElement);

    public abstract void visitTagSpec(@NotNull final OCamlTagSpec psiElement);

    public abstract void visitTagSpecFull(@NotNull final OCamlTagSpecFull psiElement);

    public abstract void visitPolyTypeExpression(@NotNull final OCamlPolyTypeExpression psiElement);

    public abstract void visitClassDefinition(@NotNull final OCamlClassDefinition psiElement);

    public abstract void visitClassBinding(@NotNull final OCamlClassBinding psiElement);

    public abstract void visitClassTypeDefinition(@NotNull final OCamlClassTypeDefinition psiElement);

    public abstract void visitClassTypeBinding(@NotNull final OCamlClassTypeBinding psiElement);

    public abstract void visitFunctionClassType(@NotNull final OCamlFunctionClassType psiElement);

    public abstract void visitClassPathApplication(@NotNull final OCamlClassPathApplication psiElement);

    public abstract void visitObjectEndClassBodyType(@NotNull final OCamlObjectEndClassBodyType psiElement);

    public abstract void visitInheritClassFiledSpecification(@NotNull final OCamlInheritClassFiledSpecification psiElement);

    public abstract void visitValueClassFieldSpecification(@NotNull final OCamlValueClassFieldSpecification psiElement);

    public abstract void visitMethodClassFieldSpecification(@NotNull final OCamlMethodClassFieldSpecification psiElement);

    public abstract void visitConstraintClassFieldSpecification(@NotNull final OCamlConstraintClassFieldSpecification psiElement);

    public abstract void visitFunctionApplicationClassExpression(@NotNull final OCamlFunctionApplicationClassExpression psiElement);

    public abstract void visitObjectEndClassExpression(@NotNull final OCamlObjectEndClassExpression psiElement);

    public abstract void visitInheritClassFieldDefinition(@NotNull final OCamlInheritClassFieldDefinition psiElement);

    public abstract void visitInitializerClassFieldDefinition(@NotNull final OCamlInitializerClassFieldDefinition psiElement);

    public abstract void visitConstraintClassFieldDefinition(@NotNull final OCamlConstraintClassFieldDefinition psiElement);

    public abstract void visitValueClassFieldDefinition(@NotNull final OCamlValueClassFieldDefinition psiElement);

    public abstract void visitMethodClassFieldDefinition(@NotNull final OCamlMethodClassFieldDefinition psiElement);

    public abstract void visitFunctionClassExpression(@NotNull final OCamlFunctionClassExpression psiElement);

    public abstract void visitClassSpecification(@NotNull final OCamlClassSpecification psiElement);

    public abstract void visitClassSpecificationBinding(@NotNull final OCamlClassSpecificationBinding psiElement);

    public abstract void visitClassTypeConstraint(@NotNull final OCamlClassTypeConstraint psiElement);

    public abstract void visitModuleDefinition(@NotNull final OCamlModuleDefinition psiElement);

    public abstract void visitModuleTypeDefinition(@NotNull final OCamlModuleTypeDefinition psiElement);

    public abstract void visitFunctorModuleType(@NotNull final OCamlFunctorModuleType psiElement);

    public abstract void visitSigEndModuleType(@NotNull final OCamlSigEndModuleType psiElement);

    public abstract void visitModuleTypeTypeConstraint(@NotNull final OCamlModuleTypeTypeConstraint psiElement);

    public abstract void visitModuleTypeModuleConstraint(@NotNull final OCamlModuleTypeModuleConstraint psiElement);

    public abstract void visitFunctorApplicationModuleExpression(@NotNull final OCamlFunctorApplicationModuleExpression psiElement);

    public abstract void visitStructEndModuleExpression(@NotNull final OCamlStructEndModuleExpression psiElement);

    public abstract void visitFunctorModuleExpression(@NotNull final OCamlFunctorModuleExpression psiElement);

    public abstract void visitModuleTypeConstraintModuleExpression(@NotNull final OCamlModuleTypeConstraintModuleExpression psiElement);

    public abstract void visitModuleTypeWithConstraints(@NotNull final OCamlModuleTypeWithConstraints psiElement);

    public abstract void visitModuleTypeSpecification(@NotNull final OCamlModuleTypeSpecification psiElement);

    public abstract void visitModuleSpecification(@NotNull final OCamlModuleSpecification psiElement);

    public abstract void visitValueName(@NotNull final OCamlValueName psiElement);

    public abstract void visitValueNamePattern(@NotNull final OCamlValueNamePattern psiElement);

    public abstract void visitOperatorName(@NotNull final OCamlOperatorName psiElement);

    public abstract void visitModulePath(@NotNull final OCamlModulePath psiElement);

    public abstract void visitModuleName(@NotNull final OCamlModuleName psiElement);

    public abstract void visitConstructorPathExpression(@NotNull final OCamlConstructorPathExpression psiElement);

    public abstract void visitConstructorNameExpression(@NotNull final OCamlConstructorNameExpression psiElement);

    public abstract void visitConstructorPathPattern(@NotNull final OCamlConstructorPathPattern psiElement);

    public abstract void visitConstructorNamePattern(@NotNull final OCamlConstructorNamePattern psiElement);

    public abstract void visitTypeConstructorName(@NotNull final OCamlTypeConstructorName psiElement);

    public abstract void visitFieldName(@NotNull final OCamlFieldName psiElement);

    public abstract void visitClassPath(@NotNull final OCamlClassPath psiElement);

    public abstract void visitClassName(@NotNull final OCamlClassName psiElement);

    public abstract void visitMethodName(@NotNull final OCamlMethodName psiElement);

    public abstract void visitInstVarName(@NotNull final OCamlInstVarName psiElement);

    public abstract void visitLabelName(@NotNull final OCamlLabelName psiElement);

    public abstract void visitModuleTypeName(@NotNull final OCamlModuleTypeName psiElement);

    public abstract void visitExtendedModulePath(@NotNull final OCamlExtendedModulePath psiElement);

    public abstract void visitExtendedModuleName(@NotNull final OCamlExtendedModuleName psiElement);

    public abstract void visitTypeConstructorPath(@NotNull final OCamlTypeConstructorPath psiElement);

    public abstract void visitModuleTypePath(@NotNull final OCamlModuleTypePath psiElement);

    public abstract void visitTagName(@NotNull final OCamlTagName psiElement);

    public abstract void visitValuePath(@NotNull final OCamlValuePath psiElement);

    public abstract void visitFieldPath(@NotNull final OCamlFieldPath psiElement);

    public abstract void visitConstantExpression(@NotNull final OCamlConstantExpression psiElement);

    public abstract void visitConstantPattern(@NotNull final OCamlConstantPattern psiElement);

    public abstract void visitMatchExpression(@NotNull final OCamlMatchExpression psiElement);

    public abstract void visitFunExpression(@NotNull final OCamlFunExpression psiElement);

    public abstract void visitFunctionExpression(@NotNull final OCamlFunctionExpression psiElement);

    public abstract void visitTryExpression(@NotNull final OCamlTryExpression psiElement);

    public abstract void visitPatternMatching(@NotNull final OCamlPatternMatching psiElement);

    public abstract void visitSemicolonExpression(@NotNull final OCamlSemicolonExpression psiElement);

    public abstract void visitIfExpression(@NotNull final OCamlIfExpression psiElement);

    public abstract void visitForExpression(@NotNull final OCamlForExpression psiElement);

    public abstract void visitWhileExpression(@NotNull final OCamlWhileExpression psiElement);

    public abstract void visitAssignmentExpression(@NotNull final OCamlAssignmentExpression psiElement);

    public abstract void visitCommaExpression(@NotNull final OCamlCommaExpression psiElement);

    public abstract void visitBinaryExpression(@NotNull final OCamlBinaryExpression psiElement);

    public abstract void visitHeadTailExpression(@NotNull final OCamlHeadTailExpression psiElement);

    public abstract void visitAssertExpression(@NotNull final OCamlAssertExpression psiElement);

    public abstract void visitLazyExpression(@NotNull final OCamlLazyExpression psiElement);

    public abstract void visitConstructorApplicationExpression(@NotNull final OCamlConstructorApplicationExpression psiElement);

    public abstract void visitFunctionApplicationExpression(@NotNull final OCamlFunctionApplicationExpression psiElement);

    public abstract void visitArrayElementAccessingExpression(@NotNull final OCamlArrayElementAccessingExpression psiElement);

    public abstract void visitStringCharAccessingExpression(@NotNull final OCamlStringCharAccessingExpression psiElement);

    public abstract void visitRecordFieldAccessingExpression(@NotNull final OCamlRecordFieldAccessingExpression psiElement);

    public abstract void visitUnaryExpression(@NotNull final OCamlUnaryExpression psiElement);

    public abstract void visitClassMethodAccessingExpression(@NotNull final OCamlClassMethodAccessingExpression psiElement);

    public abstract void visitTaggedExpression(@NotNull final OCamlTaggedExpression psiElement);

    public abstract void visitTypeConstraintExpression(@NotNull final OCamlTypeConstraintExpression psiElement);

    public abstract void visitCastingExpression(@NotNull final OCamlCastingExpression psiElement);

    public abstract void visitListExpression(@NotNull final OCamlListExpression psiElement);

    public abstract void visitArrayExpression(@NotNull final OCamlArrayExpression psiElement);

    public abstract void visitRecordExpression(@NotNull final OCamlRecordExpression psiElement);

    public abstract void visitInheritedRecordExpression(@NotNull final OCamlInheritedRecordExpression psiElement);

    public abstract void visitInstanceDuplicatingExpression(@NotNull final OCamlInstanceDuplicatingExpression psiElement);

    public abstract void visitObjectClassBodyEndExpression(@NotNull final OCamlObjectClassBodyEndExpression psiElement);

    public abstract void visitNewInstanceExpression(@NotNull final OCamlNewInstanceExpression psiElement);

    public abstract void visitArgument(@NotNull final OCamlArgument psiElement);

    public abstract void visitParameter(@NotNull final OCamlParameter psiElement);

    public abstract void visitAsPattern(@NotNull final OCamlAsPattern psiElement);

    public abstract void visitOrPattern(@NotNull final OCamlOrPattern psiElement);

    public abstract void visitCommaPattern(@NotNull final OCamlCommaPattern psiElement);

    public abstract void visitHeadTailPattern(@NotNull final OCamlHeadTailPattern psiElement);

    public abstract void visitConstructorApplicationPattern(@NotNull final OCamlConstructorApplicationPattern psiElement);

    public abstract void visitTaggedPattern(@NotNull final OCamlTaggedPattern psiElement);

    public abstract void visitTypeConstructorPattern(@NotNull final OCamlTypeConstructorPattern psiElement);

    public abstract void visitLazyPattern(@NotNull final OCamlLazyPattern psiElement);

    public abstract void visitListPattern(@NotNull final OCamlListPattern psiElement);

    public abstract void visitArrayPattern(@NotNull final OCamlArrayPattern psiElement);

    public abstract void visitRecordPattern(@NotNull final OCamlRecordPattern psiElement);

    public abstract void visitTypeConstraintPattern(@NotNull final OCamlTypeConstraintPattern psiElement);

    public abstract void visitBrackets(@NotNull final OCamlBrackets psiElement);

    public abstract void visitUnknownElement(@NotNull final OCamlUnknownElement psiElement);

    public abstract void visitTypeParameterName(@NotNull final OCamlTypeParameterName psiElement);

    public abstract void visitForExpressionIndexVariableName(@NotNull final OCamlForExpressionIndexVariableName psiElement);

    public abstract void visitForExpressionBinding(@NotNull final OCamlForExpressionBinding psiElement);

    public abstract void visitLabelDefinition(@NotNull final OCamlLabelDefinition psiElement);

    public abstract void visitTypeParameterDefinition(@NotNull final OCamlTypeParameterDefinition psiElement);

    public abstract void visitObjectSelfDefinition(@NotNull final OCamlObjectSelfDefinition psiElement);

    public abstract void visitModuleTypeDefinitionBinding(@NotNull final OCamlModuleTypeDefinitionBinding psiElement);

    public abstract void visitModuleTypeSpecificationBinding(@NotNull final OCamlModuleTypeSpecificationBinding psiElement);

    public abstract void visitModuleDefinitionBinding(@NotNull final OCamlModuleDefinitionBinding psiElement);

    public abstract void visitModuleSpecificationBinding(@NotNull final OCamlModuleSpecificationBinding psiElement);

    public abstract void visitModuleParameter(@NotNull final OCamlModuleParameter psiElement);

    public abstract void visitFileModuleExpression(@NotNull final OCamlFileModuleExpression psiElement);

    public abstract void visitFileModuleType(@NotNull final OCamlFileModuleType psiElement);

    public abstract void visitCommentBlock(@NotNull final OCamlCommentBlock psiElement);

    public abstract void visitUnclosedComment(@NotNull final OCamlUnclosedComment psiElement);

    public abstract void visitLetBindingPattern(@NotNull final OCamlLetBindingPattern psiElement);

    public abstract void visitRecordFieldInitializationInPattern(@NotNull final OCamlRecordFieldInitializationInPattern psiElement);

    public abstract void visitInstVarNameDefinition(@NotNull final OCamlInstVarNameDefinition psiElement);

    public abstract void visitConstructorNameDefinition(@NotNull final OCamlConstructorNameDefinition psiElement);

    public abstract void visitTypeParameterizedBinding(@NotNull final OCamlTypeParameterizedBinding psiElement);

    public abstract void visitCharRangePattern(@NotNull final OCamlCharRangePattern psiElement);

    public abstract void visitFileModuleDefinitionBinding(@NotNull final OCamlFileModuleDefinitionBinding psiElement);

    public abstract void visitFileModuleSpecificationBinding(@NotNull final OCamlFileModuleSpecificationBinding psiElement);

    public abstract void visitParenthesesExpression(@NotNull final OCamlParenthesesExpression psiElement);

    public abstract void visitParenthesesPattern(@NotNull final OCamlParenthesesPattern psiElement);

    public abstract void visitParenthesesTypeExpression(@NotNull final OCamlParenthesesTypeExpression psiElement);

    public abstract void visitParenthesesClassExpression(@NotNull final OCamlParenthesesClassExpression psiElement);

    public abstract void visitParenthesesModuleExpression(@NotNull final OCamlParenthesesModuleExpression psiElement);

    public abstract void visitParenthesesTypeParameters(@NotNull final OCamlParenthesesTypeParameters psiElement);

    public abstract void visitParenthesesModuleType(@NotNull final OCamlParenthesesModuleType psiElement);

    public abstract void visitParentheses(@NotNull final OCamlParentheses psiElement);

    public abstract void visitUnderscoreTypeExpression(@NotNull final OCamlUnderscoreTypeExpression psiElement);

    public abstract void visitUnderscorePattern(@NotNull final OCamlUnderscorePattern psiElement);

    public abstract void visitExpressionStatement(@NotNull final OCamlExpressionStatement psiElement);

    public abstract void visitConstructorName(@NotNull final OCamlConstructorName psiElement);

    public abstract void visitConstructorPath(@NotNull final OCamlConstructorPath psiElement);

    public abstract void visitObjectSelfSpecification(@NotNull final OCamlObjectSelfSpecification psiElement);
}
