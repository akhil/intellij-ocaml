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

package manuylov.maxim.ocaml.lang.feature.refactoring.util;

import com.intellij.codeInsight.unwrap.ScopeHighlighter;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.JBPopupAdapter;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.openapi.util.Pass;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.util.PsiTreeUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 08.05.2010
 */
public class OCamlRefactoringUtil {
    public static void showChooser(@NotNull final String title,
                                   @NotNull final Editor editor,
                                   @NotNull final List<PsiElement> elements,
                                   @NotNull final Pass<PsiElement> callback) {  //todo remove method if it is not needed
        final ScopeHighlighter highlighter = new ScopeHighlighter(editor);

        final DefaultListModel model = new DefaultListModel();
        for (final PsiElement element : elements) {
            model.addElement(element);
        }

        final JList list = new JList(model);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList list,
                                                          final Object value,
                                                          final int index,
                                                          final boolean isSelected,
                                                          final boolean cellHasFocus) {
                final Component rendererComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final PsiElement element = (PsiElement) value;
                if (element.isValid()) {
                    setText(element.getText());
                }
                return rendererComponent;
            }
        });

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(final ListSelectionEvent e) {
                highlighter.dropHighlight();
                final int index = list.getSelectedIndex();
                if (index < 0) return;
                final PsiElement element = (PsiElement) model.get(index);
                final ArrayList<PsiElement> toExtract = new ArrayList<PsiElement>();
                toExtract.add(element);
                highlighter.highlight(element, toExtract);
            }
        });

        JBPopupFactory.getInstance().createListPopupBuilder(list)
            .setTitle(title)
            .setMovable(false)
            .setResizable(false)
            .setRequestFocus(true)
            .setItemChoosenCallback(new Runnable() {
                public void run() {
                    callback.pass((PsiElement) list.getSelectedValue());
                }
            })
            .addListener(new JBPopupAdapter() {
                @Override
                public void onClosed(LightweightWindowEvent event) {
                    highlighter.dropHighlight();
                }
            })
            .createPopup().showInBestPositionFor(editor);
    }
}
