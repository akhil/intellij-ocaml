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

package manuylov.maxim.ocaml.compile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 02.06.2010
 */
class FailureMessage {
    @NotNull private final Type myType;
    @NotNull private final String myMessageText;
    @Nullable private final String myFilePath;
    private final int myLineNumber;
    private final int myStartPosition;
    private final int myEndPosition;

    FailureMessage(@NotNull final Type type,
                   @NotNull final String messageText,
                   @Nullable final String filePath,
                   final int lineNumber,
                   final int startPosition,
                   final int endPosition) {
        myType = type;
        myMessageText = messageText;
        myFilePath = filePath;
        myLineNumber = lineNumber;
        myStartPosition = startPosition;
        myEndPosition = endPosition;
    }

    @NotNull
    public Type getType() {
        return myType;
    }

    @NotNull
    public String getMessageText() {
        return myMessageText;
    }

    @Nullable
    public String getFilePath() {
        return myFilePath;
    }

    public int getLineNumber() {
        return myLineNumber;
    }

    public int getStartPosition() {
        return myStartPosition;
    }

    public int getEndPosition() {
        return myEndPosition;
    }

    public static enum Type {
        ERROR,
        WARNING
    }
}
