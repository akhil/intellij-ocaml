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

package manuylov.maxim.ocaml.lang.feature.resolving.testCase;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.PsiReference;
import manuylov.maxim.ocaml.fileType.OCamlFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.lang.BaseOCamlTestCase;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlResolvedReference;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlUnknownElement;
import manuylov.maxim.ocaml.lang.parser.util.ParserTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 18.06.2009
 *
 * todo test MethodName
 * todo test FieldName
 * todo test LabelName
 * todo test TagName
 * todo test TypeParameterName
 *
 * todo see getPossibleResTypes() methods in all reference types and check if there are the tests for each case
 * todo resolving for prefix and infix operators (e.g. " a + b ") (+ renaming of operators)
 * 
 */
@Test
public abstract class ResolvingTestCase extends BaseOCamlTestCase {
    @NotNull private static final String RESOLVED_REFERENCE_START = "{{";
    @NotNull private static final String RESOLVED_REFERENCE_END = "}}";
    @NotNull private static final String REFERENCE_POSITION = "}{";

    @NotNull private static final OCamlFileTypeLanguage ourLanguage = MLFileTypeLanguage.INSTANCE;

    private static int testNumber;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        testNumber = 0;
    }

    protected void doTest(final int n, @NotNull final String text) throws Exception {
        if (n <= testNumber) {
            throw new IllegalArgumentException("n = " + n + ", testNumber = " + testNumber);
        }
        testNumber = n;

        final String errorText = "Test " + n;
        String actualText = text;

        int resolvedReferenceStart = actualText.indexOf(RESOLVED_REFERENCE_START);
        int resolvedReferenceEnd = actualText.indexOf(RESOLVED_REFERENCE_END);

        int referencePosition = actualText.indexOf(REFERENCE_POSITION);
        assertTrue(referencePosition != -1, errorText);

        TextRange textRange;

        if (resolvedReferenceStart == -1) {
            assertEquals(resolvedReferenceEnd, -1, errorText);
            actualText = remove(actualText, referencePosition, REFERENCE_POSITION.length());
            textRange = null;
        }
        else {
            assertTrue(resolvedReferenceEnd != -1, errorText);

            if (resolvedReferenceStart < referencePosition) {
                actualText = remove(actualText, resolvedReferenceStart, RESOLVED_REFERENCE_START.length());
                resolvedReferenceEnd -= RESOLVED_REFERENCE_START.length();
                referencePosition -= RESOLVED_REFERENCE_START.length();

                if (referencePosition < resolvedReferenceEnd) {
                    actualText = remove(actualText, referencePosition, REFERENCE_POSITION.length());
                    resolvedReferenceEnd -= REFERENCE_POSITION.length();
                    actualText = remove(actualText, resolvedReferenceEnd, RESOLVED_REFERENCE_END.length());
                }
                else {
                    actualText = remove(actualText, resolvedReferenceEnd, RESOLVED_REFERENCE_END.length());
                    referencePosition -= RESOLVED_REFERENCE_END.length();
                    actualText = remove(actualText, referencePosition, REFERENCE_POSITION.length());
                }
            }
            else {
                actualText = remove(actualText, referencePosition, REFERENCE_POSITION.length());
                resolvedReferenceStart -= REFERENCE_POSITION.length();
                resolvedReferenceEnd -= REFERENCE_POSITION.length();

                actualText = remove(actualText, resolvedReferenceStart, RESOLVED_REFERENCE_START.length());
                resolvedReferenceEnd -= RESOLVED_REFERENCE_START.length();

                actualText = remove(actualText, resolvedReferenceEnd, RESOLVED_REFERENCE_END.length());
            }

            textRange = new TextRange(resolvedReferenceStart, resolvedReferenceEnd);
        }

        assertEquals(actualText.indexOf(REFERENCE_POSITION), -1, errorText);
        assertEquals(actualText.indexOf(RESOLVED_REFERENCE_START), -1, errorText);
        assertEquals(actualText.indexOf(RESOLVED_REFERENCE_END), -1, errorText);

        doTest(actualText, referencePosition, textRange, errorText);
    }

    @NotNull
    private String remove(@NotNull final String text, final int start, final int length) {
        return text.substring(0, start) + text.substring(start + length);
    }

    private void doTest(@NotNull final String actualText,
                        final int referencePosition,
                        @Nullable final TextRange resolvedReferenceRange,
                        @NotNull final String errorText) throws Exception {        
        final ASTNode root = ParserTestUtil.buildTree(actualText, ourLanguage.getParserDefinition());
        final PsiReference reference = findReferenceAt(root.getPsi(), referencePosition);
        assertNotNull(reference, errorText);

        PsiElement element = null;
        boolean containingFileRequested = false;
        try {
            element = reference.resolve();
        }
        catch (final PsiInvalidElementAccessException e) {
            containingFileRequested = true;
        }

        if (resolvedReferenceRange == null) {
            assertNull(element, errorText);
        }
        else {
            assertNotNull(element, errorText);
            assertTrue(element instanceof OCamlResolvedReference, errorText);
            assertEquals(element.getTextRange(), resolvedReferenceRange, errorText);
            assertFalse(containingFileRequested, errorText);
        }
    }

    @Nullable
    private PsiReference findReferenceAt(@NotNull final PsiElement root, final int referencePosition) {
        PsiElement element = root.findElementAt(referencePosition);
        if (element == null) return null;

        int offset = referencePosition - (element.getTextRange().getStartOffset() - root.getTextRange().getStartOffset());

        while (element != null) {
            for (final PsiReference reference : element.getReferences()) {
                if (reference.getRangeInElement().contains(offset)) {
                    return reference;
                }
            }
            if (element instanceof OCamlUnknownElement) break;
            offset += element.getStartOffsetInParent();
            element = element.getParent();
        }

        return null;
    }
}
