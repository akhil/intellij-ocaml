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

package manuylov.maxim.ocaml.lang.feature.refactoring.surround.surrounder;

import com.intellij.lang.surroundWith.Surrounder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 08.05.2010
 */
abstract class BaseOCamlSurrounder implements Surrounder {
    @NotNull private final String myDescription;

    public BaseOCamlSurrounder(@NotNull final String description) {
        myDescription = description;
    }

    @NotNull
    public String getTemplateDescription() {
        return myDescription;
    }

    public boolean isApplicable(@NotNull final PsiElement[] elements) {
        return true;
    }

    @Nullable
    public TextRange surroundElements(@NotNull final Project project, @NotNull final Editor editor, @NotNull final PsiElement[] elements) throws IncorrectOperationException {
        if (elements.length == 0) {
            return null;
        }
        final TextRange range = elements[0].getTextRange();
        final int startPos = range.getStartOffset();
        final int endPos = range.getEndOffset();
        final Document document = editor.getDocument();
        final String surroundedText = doSurround(document.getCharsSequence().subSequence(startPos, endPos));
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            public void run() {
                document.replaceString(startPos, endPos, surroundedText); //todo apply code formatting
            }
        });
        return getTextRange(startPos, surroundedText);
    }

    @NotNull
    protected TextRange getTextRange(final int startPos, @NotNull final String surroundedText) {
        return new TextRange(startPos, startPos + surroundedText.length());
    }

    @NotNull
    protected abstract String doSurround(@NotNull final CharSequence text);
}