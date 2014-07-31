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

import com.intellij.openapi.compiler.TimestampValidityState;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.vfs.VirtualFile;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * @author Maxim.Manuylov
 *         Date: 10.04.2010
 */
class OCamlValidityState implements ValidityState {
    @NotNull private final TimestampValidityState myTimestampValidityState;
    private final boolean myIsDebugMode;

    public OCamlValidityState(@NotNull final TimestampValidityState timestampValidityState, final boolean isDebugMode) {
        myTimestampValidityState = timestampValidityState;
        myIsDebugMode = isDebugMode;
    }

    public boolean equalsTo(@Nullable final ValidityState otherState) {
        if (otherState == null || !(otherState instanceof OCamlValidityState)) {
          return false;
        }
        final OCamlValidityState thatState = (OCamlValidityState) otherState;
        return myTimestampValidityState.equalsTo(thatState.myTimestampValidityState) && myIsDebugMode == thatState.myIsDebugMode;
    }

    public void save(@NotNull final DataOutput out) throws IOException {
        myTimestampValidityState.save(out);
        out.writeBoolean(myIsDebugMode);
    }

    @NotNull
    public static ValidityState load(@NotNull final DataInput in) throws IOException {
        try {
            final TimestampValidityState timestampValidityState = TimestampValidityState.load(in);
            try {
                return new OCamlValidityState(timestampValidityState, in.readBoolean());
            }
            catch (final EOFException e) {
                return timestampValidityState;
            }
        } catch (final EOFException e) {
            return NeedRecompilationValidityState.INSTANCE;
        }
    }

    @NotNull
    public static ValidityState create(@NotNull final VirtualFile file,
                                       @NotNull final File compiledFile,
                                       final boolean isDebugMode,
                                       final boolean forceRecompilation) {
        final TimestampValidityState timestampValidityState = new TimestampValidityState(file.getTimeStamp());
        if (OCamlFileUtil.isOCamlFile(file)) {
            if (forceRecompilation || !compiledFile.exists()) {
                return NeedRecompilationValidityState.INSTANCE;
            }
            return new OCamlValidityState(timestampValidityState, isDebugMode);
        }
        else {
            return timestampValidityState;
        }
    }

    private static class NeedRecompilationValidityState implements ValidityState {
        @NotNull public static final NeedRecompilationValidityState INSTANCE = new NeedRecompilationValidityState();

        public boolean equalsTo(@NotNull final ValidityState otherState) {
            return false;
        }

        public void save(@NotNull final DataOutput out) throws IOException {
        }
    }
}
