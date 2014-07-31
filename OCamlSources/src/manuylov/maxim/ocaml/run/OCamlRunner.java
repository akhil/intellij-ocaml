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

package manuylov.maxim.ocaml.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 10.04.2010
 */
public class OCamlRunner extends DefaultProgramRunner {
    @Nullable private static Executor ourCurrentExecutor = null;

    @NotNull
    public String getRunnerId() {
        return "OCamlRunner";
    }

    public boolean canRun(@NotNull final String executorId, @NotNull final RunProfile profile) {
        return profile instanceof OCamlRunConfiguration &&
            (executorId.equals(DefaultRunExecutor.EXECUTOR_ID) || executorId.equals(DefaultDebugExecutor.EXECUTOR_ID));
    }

    @Override
    public void execute(@NotNull final Executor executor, @NotNull final ExecutionEnvironment env, @Nullable final Callback callback) throws ExecutionException {
        ourCurrentExecutor = executor;
        super.execute(executor, env, callback);
        ourCurrentExecutor = null;
    }

    @Nullable
    public static Executor getCurrentExecutor() {
        return ourCurrentExecutor;
    }
}