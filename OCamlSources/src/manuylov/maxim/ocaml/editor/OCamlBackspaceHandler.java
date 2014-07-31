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

package manuylov.maxim.ocaml.editor;

import com.intellij.codeInsight.editorActions.BackspaceHandler;
import com.intellij.codeInsight.editorActions.BackspaceHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.psi.PsiFile;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 08.05.2010
 */
public class OCamlBackspaceHandler extends BackspaceHandlerDelegate {
    private LogicalPosition myTargetPosition;

    public void beforeCharDeleted(final char c, @NotNull final PsiFile file, @NotNull final Editor editor) {
        if (!OCamlFileUtil.isOCamlSourceFile(file.getFileType())) return;
        myTargetPosition = BackspaceHandler.getBackspaceUnindentPosition(file, editor);
    }

    public boolean charDeleted(final char c, @NotNull final PsiFile file, @NotNull final Editor editor) {
        if (myTargetPosition != null) {
            final int offset = editor.getCaretModel().getOffset();
            editor.getSelectionModel().setSelection(offset - editor.getCaretModel().getVisualPosition().column + myTargetPosition.column, offset);
            EditorModificationUtil.deleteSelectedText(editor);
            editor.getCaretModel().moveToLogicalPosition(myTargetPosition);
            myTargetPosition = null;
            return true;
        }
        return false;
    }
}