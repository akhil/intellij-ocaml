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

package manuylov.maxim.ocaml.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.io.FileUtil;
import manuylov.maxim.ocaml.sdk.OCamlSdkType;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
@State(
    name = "OCamlSettings",
    storages = {
        @Storage(id = "default", file = "$PROJECT_FILE$"),
        @Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/ocaml_settings.xml", scheme = StorageScheme.DIRECTORY_BASED)
    })
public class OCamlSettings implements ProjectComponent, PersistentStateComponent<OCamlState> {
    @NotNull private final Project myProject;
    @Nullable private Sdk myTopLevelSdk = null;
    @NotNull private String myTopLevelCmdParams = "";
    @NotNull private String myTopLevelCmdWorkingDir = "";

    @NotNull private static OCamlSettings ourInstance;

    @NotNull
    public static OCamlSettings getInstance() {
        return ourInstance;
    }

    public OCamlSettings(@NotNull final Project project) {
        myProject = project;
        ourInstance = this;
    }

    @NotNull
    public OCamlState getState() {
        final OCamlState state = new OCamlState();
        if (myTopLevelSdk != null) {
            state.setTopLevelSdkHomePath(FileUtil.toSystemIndependentName(myTopLevelSdk.getHomePath()));
        }
        state.setTopLevelCmdOptions(myTopLevelCmdParams);
        state.setTopLevelCmdWorkingDir(myTopLevelCmdWorkingDir);
        return state;
    }

    public void loadState(@NotNull final OCamlState state) {
        final String systemIndependentHomePath = state.getTopLevelSdkHomePath();
        if (systemIndependentHomePath == null) {
            myTopLevelSdk = null;
        }
        else {
            final List<Sdk> ocamlSdks = ProjectJdkTable.getInstance().getSdksOfType(OCamlSdkType.getInstance());
            for (final Sdk ocamlSdk : ocamlSdks) {
                if (systemIndependentHomePath.equals(FileUtil.toSystemIndependentName(ocamlSdk.getHomePath()))) {
                    myTopLevelSdk = ocamlSdk;
                    break;
                }
            }
        }
        myTopLevelCmdParams = OCamlStringUtil.getNotNull(state.getTopLevelCmdOptions());
        myTopLevelCmdWorkingDir = OCamlStringUtil.getNotNull(state.getTopLevelCmdWorkingDir());
    }

    @Nullable
    public Sdk getTopLevelSdk() {
        return myTopLevelSdk;
    }

    public void setTopLevelSdk(@Nullable final Sdk topLevelSdk) {
        myTopLevelSdk = topLevelSdk;
    }

    public void setTopLevelCmdOptions(@NotNull final String cmdParams) {
        myTopLevelCmdParams = cmdParams;
    }

    @NotNull
    public String getTopLevelCmdOptions() {
        return myTopLevelCmdParams;
    }

    public void setTopLevelCmdWorkingDir(@NotNull final String dir) {
        myTopLevelCmdWorkingDir = dir;
    }

    @NotNull
    public String getTopLevelCmdWorkingDir() {
        return myTopLevelCmdWorkingDir;
    }

    @NotNull
    public String getComponentName() {
        return "OCamlSettings";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public void projectOpened() {
    }

    public void projectClosed() {
    }
}
