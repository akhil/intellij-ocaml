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

package manuylov.maxim.ocaml.lang.feature.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.mli.MLIFileTypeLanguage;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlReference;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlStatement;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlUnknownElement;
import manuylov.maxim.ocaml.lang.parser.psi.element.impl.OCamlUnknownElementImpl;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * @author Maxim.Manuylov
 *         Date: 09.05.2010
 */
public class OCamlCompletionContributor extends CompletionContributor {
    @NotNull public static final String LOWER_CASE_DUMMY_IDENTIFIER = "lowerCase"; // todo sometimes this string appeared in list (e.g "class }{") 
    @NotNull public static final String UPPER_CASE_DUMMY_IDENTIFIER = "UpperCase";

    @NotNull private static final Key<CompletionParameters> PARAMETERS = Key.create("OCamlCompletionContributorCompletionParameters");

    private static final char SPACE = ' ';

    @NotNull private static final PsiElementPattern.Capture<PsiElement> OCAML_ELEMENT =
        psiElement().andOr(
            psiElement().withLanguage(MLFileTypeLanguage.INSTANCE),
            psiElement().withLanguage(MLIFileTypeLanguage.INSTANCE)
        );

    @Override
    public void beforeCompletion(@NotNull final CompletionInitializationContext context) {
        context.setFileCopyPatcher(new DummyIdentifierPatcher(LOWER_CASE_DUMMY_IDENTIFIER));
    }

    @Override
    public void fillCompletionVariants(@NotNull final CompletionParameters parameters, @NotNull final CompletionResultSet result) {
        final PsiElement element = parameters.getPosition();
        try {
            element.putUserData(PARAMETERS, parameters);
            super.fillCompletionVariants(parameters, result);
        }
        finally {
            element.putUserData(PARAMETERS, null);
        }
    }

    public OCamlCompletionContributor() {
        final Set<String> keywords = collectAllKeywords();

        for (final String keyword : keywords) {
            extend(
                CompletionType.BASIC,
                OCAML_ELEMENT.and(isPossibleHere(keyword)),
                createCompletionProvider(TailType.SPACE, keyword)
            );
        }

        extend(
            CompletionType.BASIC,
            OCAML_ELEMENT.and(isEndAfterStructOrSig()),
            createCompletionProvider(TailType.SPACE, Keywords.END_KEYWORD)
        );

        extend(CompletionType.BASIC, psiElement(), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, @NotNull final ProcessingContext context, @NotNull final CompletionResultSet result) {
                processUpperCaseVariants(parameters, result);
            }
        });

        //todo ModuleName (ClassName?) completion + smart completion
    }

    private void processUpperCaseVariants(@NotNull final CompletionParameters parameters, @NotNull final CompletionResultSet result) {
        final PsiElement dummyElement = createDummyElement(parameters, UPPER_CASE_DUMMY_IDENTIFIER, true);
        if (dummyElement != null) {
            final OCamlReference reference = OCamlPsiUtil.getParentOfType(dummyElement, OCamlReference.class);
            if (reference != null) {
                final LookupElement[] variants = reference.getVariants();
                for (final LookupElement variant : variants) {
                    result.addElement(variant);
                }
            }
        }
    }

    @NotNull
    private static Set<String> collectAllKeywords() {
        final HashSet<String> result = new HashSet<String>();
        for (final Field field : Keywords.class.getFields()) {
            if (field.isAnnotationPresent(DoNotSuggestInCompletionVariants.class)) continue;
            try {
                result.add((String) field.get(null));
            } catch (IllegalAccessException ignore) {}
        }
        return result;
    }

    @NotNull
    private ElementPattern isPossibleHere(@NotNull final String keyword) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(final Object element, final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                if (parameters == null) return false;

                final PsiElement dummyElement = createDummyElement(parameters, keyword, false);
                return dummyElement != null && isCorrect(dummyElement);
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    private static boolean isCorrect(@NotNull final PsiElement element) {
        final PsiElement parent = OCamlPsiUtil.getStrictParent(element);
        if (parent != null && OCamlPsiUtil.isError(parent)) return false;
        final PsiElement previousLeaf = OCamlPsiUtil.getNonWhiteSpacePreviousLeaf(element, false);
        return previousLeaf == null || !OCamlPsiUtil.isError(previousLeaf);
    }

    @NotNull
    private ElementPattern isEndAfterStructOrSig() {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                if (parameters == null) return false;

                final PsiFile file = parameters.getOriginalFile();

                final OCamlElement module;
                final PsiElement originalPosition = parameters.getOriginalPosition();
                if (originalPosition == null) {
                    final PsiElement lastLeaf = OCamlPsiUtil.getNonWhiteSpaceLastLeaf(file, true);
                    final ASTNode lastLeafNode = lastLeaf.getNode();
                    if (lastLeafNode != null && lastLeafNode.getElementType() == OCamlTokenTypes.SEMICOLON_SEMICOLON) {
                        module = OCamlPsiUtil.getParent(lastLeaf);
                    }
                    else {
                        final OCamlStatement statement = OCamlPsiUtil.getStatementOf(lastLeaf);
                        module = statement == null ? null : OCamlPsiUtil.getParent(statement);
                    }
                }
                else {
                    final OCamlStatement statement = OCamlPsiUtil.getStatementOf(originalPosition);
                    module = statement == null ? null : OCamlPsiUtil.getParent(statement);
                }

                if (module == null) return false;
                final PsiElement firstChild = module.getFirstChild();
                if (firstChild == null) return false;
                final ASTNode firstChildNode = firstChild.getNode();
                if (firstChildNode == null) return false;
                final IElementType elementType = firstChildNode.getElementType();
                return elementType == OCamlTokenTypes.STRUCT_KEYWORD || elementType == OCamlTokenTypes.SIG_KEYWORD;
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    @Nullable
    private static CompletionParameters getCompletionParameters(@NotNull final Object object) {
        if (!(object instanceof UserDataHolder)) return null;
        return ((UserDataHolder) object).getUserData(PARAMETERS);
    }

    @Nullable
    private static PsiElement createDummyElement(@NotNull final CompletionParameters parameters, @NotNull final String elementText, final boolean parseWholeFile) {
        final PsiFile file = parameters.getOriginalFile();
        final StringBuilder textToParse = new StringBuilder("");
        final int offsetInStatement;

        if (parseWholeFile) {
            offsetInStatement = parameters.getOffset();
            if (appendElementText(textToParse, file) > 0) {
                OCamlStringUtil.insert(textToParse, offsetInStatement, elementText);
            }
        }
        else {
            final PsiElement originalPosition = parameters.getOriginalPosition();

            PsiElement statement;
            int prevStatementLength = 0;
            if (originalPosition == null) {
                final PsiElement lastLeaf = OCamlPsiUtil.getNonWhiteSpaceLastLeaf(file, true);
                statement = getStatementOrElement(lastLeaf);
            }
            else {
                final PsiElement previousLeaf = OCamlPsiUtil.getNonWhiteSpacePreviousLeaf(originalPosition, true);
                statement = previousLeaf == null ? null : getStatementOrElement(previousLeaf);
                if (statement != null && OCamlPsiUtil.isFirstChildOf(statement, originalPosition)) {
                    final OCamlElement prevStatement = OCamlPsiUtil.getPrevSibling(statement);
                    if (prevStatement != null) {
                        prevStatementLength = appendElementText(textToParse, prevStatement) + 1;
                        textToParse.append(SPACE);
                    }
                }
            }

            if (statement != null) {
                appendElementText(textToParse, statement);
            }

            offsetInStatement = prevStatementLength +
                (statement == null ? 0 : getStartOffset(parameters.getPosition()) - getStartOffset(statement));

            final int length = textToParse.length();
            if (offsetInStatement > length) {
                StringUtil.repeatSymbol(textToParse, SPACE, offsetInStatement - length);
            }
            else if (offsetInStatement < length) {
                textToParse.delete(offsetInStatement, length);
            }

            textToParse.append(elementText);
        }

        final ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(file.getLanguage());
        if (parserDefinition == null) return null;

        final PsiElement root = OCamlPsiUtil.parse(textToParse, parserDefinition, file.getProject(), false);
        if (root instanceof OCamlUnknownElementImpl) {
            ((OCamlUnknownElementImpl)root).setFile(file);
        }
        return root.findElementAt(offsetInStatement);
    }

    private static int getStartOffset(@NotNull final PsiElement element) {
        return element.getTextRange().getStartOffset();
    }

    @NotNull
    private static PsiElement getStatementOrElement(@NotNull final PsiElement element) {
        final PsiElement statement = OCamlPsiUtil.getStatementOf(element);
        return statement == null ? element : statement;
    }

    private static int appendElementText(@NotNull final StringBuilder builder, @NotNull final PsiElement element) {
        final ASTNode node = element.getNode();
        if (node != null) {
            final String text = node.getText();
            builder.append(text);
            return text.length();
        }
        return 0;
    }

    @NotNull
    private static CompletionProvider<CompletionParameters> createCompletionProvider(@NotNull final TailType tailType, @NotNull final String... keywords) {
        return new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, @NotNull final ProcessingContext context, @NotNull final CompletionResultSet result) {
                putKeywords(result, tailType, keywords);
            }
        };
    }

    private static void putKeywords(@NotNull final CompletionResultSet result, @NotNull final TailType tail, @NotNull final String... words) {
        for (final String word : words) {
            result.addElement(TailTypeDecorator.withTail(LookupElementBuilder.create(word).setBold(), tail));
        }
    }
}
