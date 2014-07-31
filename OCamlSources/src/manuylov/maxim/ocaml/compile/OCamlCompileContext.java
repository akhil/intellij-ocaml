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

import com.intellij.compiler.options.CompileStepBeforeRun;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.openapi.compiler.CompileContext;
import manuylov.maxim.ocaml.run.OCamlRunConfiguration;
import manuylov.maxim.ocaml.run.OCamlRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 10.04.2010
 */
class OCamlCompileContext {
    @Nullable private final OCamlRunConfiguration myRunConfiguration;
    private final boolean myIsStandaloneCompile;
    private final boolean myIsDebugMode;

    private OCamlCompileContext(final boolean isStandaloneCompile,
                               @Nullable final OCamlRunConfiguration runConfiguration,
                               final boolean isDebugMode) {
        myIsStandaloneCompile = isStandaloneCompile;
        myRunConfiguration = runConfiguration;
        myIsDebugMode = isDebugMode;
    }

    public boolean isStandaloneCompile() {
        return myIsStandaloneCompile;
    }

    @Nullable
    public OCamlRunConfiguration getRunConfiguration() {
        return myRunConfiguration;
    }

    public boolean isDebugMode() {
        return myIsDebugMode;
    }

    @NotNull
    public static OCamlCompileContext createOn(@NotNull final CompileContext context) {
        final RunConfiguration configuration = CompileStepBeforeRun.getRunConfiguration(context);
        if (configuration == null || !(configuration instanceof OCamlRunConfiguration)) {
            return new OCamlCompileContext(true, null, false);
        }
        final String debugExecutorId = DefaultDebugExecutor.getDebugExecutorInstance().getId();
        final Executor currentExecutor = OCamlRunner.getCurrentExecutor();
        final boolean isDebugMode = currentExecutor != null && debugExecutorId.equals(currentExecutor.getId());
        return new OCamlCompileContext(false, (OCamlRunConfiguration) configuration, isDebugMode);
    }
}
