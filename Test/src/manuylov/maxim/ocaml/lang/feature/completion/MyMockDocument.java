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
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 26.05.2010
 */
public class MyMockDocument extends MockDocument {
    public MyMockDocument() {
        super("");
    }

    @NotNull
    @Override
    public RangeMarker createRangeMarker(final int startOffset, final int endOffset) {
        return new RangeMarker() {
            @NotNull
            public Document getDocument() {
                return MyMockDocument.this;
            }

            public int getStartOffset() {
                return 0;
            }

            public int getEndOffset() {
                return 0;
            }

            public boolean isValid() {
                return false;
            }

            public void setGreedyToLeft(final boolean greedy) {
            }

            public void setGreedyToRight(final boolean greedy) {
            }

            public boolean isGreedyToRight() {
                return false;
            }

            public boolean isGreedyToLeft() {
                return false;
            }

            public <T> T getUserData(@NotNull final Key<T> key) {
                return null;
            }

            public <T> void putUserData(@NotNull final Key<T> key, @Nullable final T value) {              
            }
        };
    }
}
