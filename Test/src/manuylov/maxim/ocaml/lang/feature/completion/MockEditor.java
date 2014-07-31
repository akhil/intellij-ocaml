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

import com.intellij.mock.MockDocument;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Maxim.Manuylov
 *         Date: 26.05.2010
 */
@SuppressWarnings({"ConstantConditions"})
public class MockEditor implements Editor {
    @NotNull
    public Document getDocument() {
        return new MyMockDocument();
    }

    public boolean isViewer() {
        return false;
    }

    @NotNull
    public JComponent getComponent() {
        return null;
    }

    @NotNull
    public JComponent getContentComponent() {
        return null;
    }

    @NotNull
    public SelectionModel getSelectionModel() {
        return new SelectionModel() {
            public int getSelectionStart() {
                return 0;
            }

            public int getSelectionEnd() {
                return 0;
            }

            public String getSelectedText() {
                return null;
            }

            public int getLeadSelectionOffset() {
                return 0;
            }

            public boolean hasSelection() {
                return false;
            }

            public void setSelection(final int startOffset, final int endOffset) {
            }

            public void removeSelection() {
            }

            public void addSelectionListener(final SelectionListener listener) {
            }

            public void removeSelectionListener(final SelectionListener listener) {
            }

            public void selectLineAtCaret() {
            }

            public void selectWordAtCaret(final boolean honorCamelWordsSettings) {
            }

            public void copySelectionToClipboard() {
            }

            public void setBlockSelection(final LogicalPosition blockStart, final LogicalPosition blockEnd) {
            }

            public void removeBlockSelection() {
            }

            public boolean hasBlockSelection() {
                return false;
            }

            @NotNull
            public int[] getBlockSelectionStarts() {
                return new int[0];
            }

            @NotNull
            public int[] getBlockSelectionEnds() {
                return new int[0];
            }

            public LogicalPosition getBlockStart() {
                return null;
            }

            public LogicalPosition getBlockEnd() {
                return null;
            }

            public boolean isBlockSelectionGuarded() {
                return false;
            }

            public RangeMarker getBlockSelectionGuard() {
                return null;
            }

            public TextAttributes getTextAttributes() {
                return null;
            }
        };
    }

    @NotNull
    public MarkupModel getMarkupModel() {
        return null;
    }

    @NotNull
    public FoldingModel getFoldingModel() {
        return null;
    }

    @NotNull
    public ScrollingModel getScrollingModel() {
        return null;
    }

    @NotNull
    public CaretModel getCaretModel() {
        return new CaretModel() {
            public void moveCaretRelatively(final int columnShift, final int lineShift, final boolean withSelection, final boolean blockSelection, final boolean scrollToCaret) {
            }

            public void moveToLogicalPosition(final LogicalPosition pos) {
            }

            public void moveToVisualPosition(final VisualPosition pos) {
            }

            public void moveToOffset(final int offset) {
            }

            public LogicalPosition getLogicalPosition() {
                return null;
            }

            public VisualPosition getVisualPosition() {
                return null;
            }

            public int getOffset() {
                return 0;
            }

            public void addCaretListener(final CaretListener listener) {
            }

            public void removeCaretListener(final CaretListener listener) {
            }

            public int getVisualLineStart() {
                return 0;
            }

            public int getVisualLineEnd() {
                return 0;
            }

            public TextAttributes getTextAttributes() {
                return null;
            }
        };
    }

    @NotNull
    public EditorSettings getSettings() {
        return null;
    }

    @NotNull
    public EditorColorsScheme getColorsScheme() {
        return null;
    }

    public int getLineHeight() {
        return 0;
    }

    @NotNull
    public Point logicalPositionToXY(@NotNull final LogicalPosition pos) {
        return null;
    }

    public int logicalPositionToOffset(@NotNull final LogicalPosition pos) {
        return 0;
    }

    @NotNull
    public VisualPosition logicalToVisualPosition(@NotNull final LogicalPosition logicalPos) {
        return null;
    }

    @NotNull
    public Point visualPositionToXY(@NotNull final VisualPosition visible) {
        return null;
    }

    @NotNull
    public LogicalPosition visualToLogicalPosition(@NotNull final VisualPosition visiblePos) {
        return null;
    }

    @NotNull
    public LogicalPosition offsetToLogicalPosition(final int offset) {
        return null;
    }

    @NotNull
    public VisualPosition offsetToVisualPosition(final int offset) {
        return null;
    }

    @NotNull
    public LogicalPosition xyToLogicalPosition(@NotNull final Point p) {
        return null;
    }

    @NotNull
    public VisualPosition xyToVisualPosition(@NotNull final Point p) {
        return null;
    }

    public void addEditorMouseListener(@NotNull final EditorMouseListener listener) {
      
    }

    public void removeEditorMouseListener(@NotNull final EditorMouseListener listener) {
      
    }

    public void addEditorMouseMotionListener(@NotNull final EditorMouseMotionListener listener) {
      
    }

    public void removeEditorMouseMotionListener(@NotNull final EditorMouseMotionListener listener) {
      
    }

    public boolean isDisposed() {
        return false;
    }

    public Project getProject() {
        return null;
    }

    public boolean isInsertMode() {
        return false;
    }

    public boolean isColumnMode() {
        return false;
    }

    public boolean isOneLineMode() {
        return false;
    }

    @NotNull
    public EditorGutter getGutter() {
        return null;
    }

    public EditorMouseEventArea getMouseEventArea(@NotNull final MouseEvent e) {
        return null;
    }

    public void setHeaderComponent(@Nullable final JComponent header) {
      
    }

    public boolean hasHeaderComponent() {
        return false;
    }

    public JComponent getHeaderComponent() {
        return null;
    }

    public IndentsModel getIndentsModel() {
        return null;
    }

    public <T> T getUserData(@NotNull final Key<T> key) {
        return null;
    }

    public <T> void putUserData(@NotNull final Key<T> key, @Nullable final T value) {
      
    }
}
