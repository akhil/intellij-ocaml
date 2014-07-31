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

package manuylov.maxim.ocaml.lang.feature.completion.testCase;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.command.impl.DummyProject;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiFileImpl;
import manuylov.maxim.ocaml.fileType.OCamlFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.lang.BaseOCamlTestCase;
import manuylov.maxim.ocaml.lang.feature.completion.OCamlCompletionContributor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementFactory;
import manuylov.maxim.ocaml.lang.parser.util.ParserTestUtil;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.BeforeMethod;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Maxim.Manuylov
 *         Date: 20.05.2010
 */
public abstract class CompletionTestCase extends BaseOCamlTestCase {
    @NotNull private static final String COMPLETION_POSITION = "}{";

    @NotNull private static final OCamlFileTypeLanguage ourLanguage = MLFileTypeLanguage.INSTANCE;
    @NotNull private static final CompletionContributor ourContributor = new OCamlCompletionContributor();

    private static int testNumber;

    private String[] myVariants;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        testNumber = 0;
    }

    @NotNull
    protected abstract CompletionType getCompletionType();

    protected abstract int getInvocationCount();

    protected void setVariants(@NotNull final String... variants) {
        myVariants = variants;
    }

    protected void doTest(final int n, @NotNull final String text, final boolean shouldContain) throws Exception {
        if (n <= testNumber) {
            throw new IllegalArgumentException("n = " + n + ", testNumber = " + testNumber);
        }
        testNumber = n;

        final String errorText = "Test " + n;

        int completionPosition = text.indexOf(COMPLETION_POSITION);
        assertTrue(completionPosition != -1, errorText);
        final String actualText = remove(text, completionPosition, COMPLETION_POSITION.length());
        assertEquals(actualText.indexOf(COMPLETION_POSITION), -1, errorText);

        doTest(actualText, completionPosition, shouldContain, errorText);
    }

    @NotNull
    private String remove(@NotNull final String text, final int start, final int length) {
        return text.substring(0, start) + text.substring(start + length);
    }

    private void doTest(@NotNull final String text,
                        final int completionPosition,
                        final boolean shouldContain,
                        @NotNull final String errorText) throws Exception {
        final ParserDefinition parserDefinition = ourLanguage.getParserDefinition();

        final ASTNode originalRoot = ParserTestUtil.buildTree(text, parserDefinition);
        final OCamlElement originalPsiRoot = OCamlElementFactory.INSTANCE.createElement(originalRoot);

        final String dummyText = insert(
            text, completionPosition, OCamlCompletionContributor.LOWER_CASE_DUMMY_IDENTIFIER
        );
        final ASTNode dummyRoot = ParserTestUtil.buildTree(dummyText, parserDefinition);
        final OCamlElement dummyPsiRoot = OCamlElementFactory.INSTANCE.createElement(dummyRoot);

        final Set<String> result = new TreeSet<String>();
        ourContributor.fillCompletionVariants(
            createCompletionParameters(originalPsiRoot, dummyPsiRoot, completionPosition),
            createResultSet(result)
        );

        for (final String variant : myVariants) {
            assertEquals(result.contains(variant), shouldContain, errorText +
                "\nvariant: " + variant +
                "\nshould contain: " + shouldContain +
                "\nresult: " + result.toString()
            );
        }
    }

    @NotNull
    private CompletionParameters createCompletionParameters(@NotNull final OCamlElement originalPsiRoot,
                                                            @NotNull final OCamlElement dummyPsiRoot,
                                                            final int completionPosition) {
        //noinspection ConstantConditions
        return new CompletionParameters(
            dummyPsiRoot.findElementAt(completionPosition),
            createFakeFile(originalPsiRoot),
            getCompletionType(),
            completionPosition,
            getInvocationCount()) {

            @Override
            public PsiElement getOriginalPosition() {
                return originalPsiRoot.findElementAt(completionPosition);
            }
        };
    }

    @NotNull
    private CompletionResultSet createResultSet(@NotNull final Set<String> result) {
        return new CompletionResultSet(null, null, ourContributor) {
            @Override
            public void addElement(@NotNull final LookupElement element) {
                result.add(element.getLookupString());
            }

            @NotNull
            @Override
            public CompletionResultSet withPrefixMatcher(@NotNull final PrefixMatcher matcher) {
                return this;
            }

            @NotNull
            @Override
            public CompletionResultSet withPrefixMatcher(@NotNull final String prefix) {
                return this;
            }

            @NotNull
            @Override
            public CompletionResultSet caseInsensitive() {
                return this;
            }
        };
    }

    @NotNull
    public PsiFile createFakeFile(@NotNull final OCamlElement originalPsiRoot) {
        return new PsiFileImpl(new DummyHolderViewProvider(new MockPsiManager())) {
            @NotNull
            public FileType getFileType() {
                //noinspection ConstantConditions
                return null;
            }

            @Override
            public PsiReference findReferenceAt(final int offset) {
                return null;
            }

            @Override
            public ASTNode getNode() {
                return originalPsiRoot.getNode();
            }

            @NotNull
            @Override
            public Language getLanguage() {
                return ourLanguage;
            }

            @NotNull
            @Override
            public Project getProject() {
                return DummyProject.getInstance();
            }

            @Override
            @Nullable
            public PsiElement getLastChild() {
                return originalPsiRoot.getLastChild();
            }

            public void accept(@NotNull final PsiElementVisitor visitor) {}
        };
    }

    @NotNull
    public static String insert(@NotNull final String text, final int position, @NotNull final String textToInsert) {
        final StringBuilder sb = new StringBuilder(text);
        OCamlStringUtil.insert(sb, position, textToInsert);
        return sb.toString();
    }
}
