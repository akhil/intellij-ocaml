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

package manuylov.maxim.ocaml.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.SourcePathsBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
class OCamlModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {
    @NotNull private String myRelativeSourcesPath = "src";
    private boolean myShouldCreateSourcesDir = true;
    @Nullable private String myContentRootPath = null;
    @Nullable private Sdk mySdk = null;

    public void setupRootModel(@NotNull final ModifiableRootModel rootModel) throws ConfigurationException {
        if (mySdk != null) {
            rootModel.setSdk(mySdk);
        } else {
            rootModel.inheritSdk();
        }
        if (myContentRootPath != null) {
            final LocalFileSystem lfs = LocalFileSystem.getInstance();
            //noinspection ConstantConditions
            final VirtualFile moduleContentRoot = lfs.refreshAndFindFileByPath(FileUtil.toSystemIndependentName(myContentRootPath));
            if (moduleContentRoot != null) {
                final ContentEntry contentEntry = rootModel.addContentEntry(moduleContentRoot);
                if (myShouldCreateSourcesDir) {
                    final File sourcesDir = getSourcesDir();
                    if (!sourcesDir.isDirectory()) {
                        //noinspection ResultOfMethodCallIgnored
                        sourcesDir.mkdirs();
                    }
                    final VirtualFile sourceRoot = lfs.refreshAndFindFileByIoFile(sourcesDir);
                    if (sourceRoot != null) {
                        contentEntry.addSourceFolder(sourceRoot, false, "");
                    }
                }
            }
        }
    }

    @NotNull
    private File getSourcesDir() {
        final String[] dirs = myRelativeSourcesPath.replace("\\", "/").split("/");
        File result = new File(myContentRootPath);
        for (final String dir : dirs) {
            result = new File(result, dir);
        }
        return result;
    }

    @NotNull
    public ModuleType getModuleType() {
        return OCamlModuleType.getInstance();
    }

    @Nullable
    public String getContentEntryPath() {
        return myContentRootPath;
    }

    public void setContentEntryPath(@Nullable final String contentRootPath) {
        myContentRootPath = contentRootPath;
    }

    public boolean isShouldCreateSourcesDir() {
        return myShouldCreateSourcesDir;
    }

    public void setShouldCreateSourcesDir(final boolean shouldCreateSourcesDir) {
        myShouldCreateSourcesDir = shouldCreateSourcesDir;
    }

    @NotNull
    public String getRelativeSourcesPath() {
        return myRelativeSourcesPath;
    }

    public void setRelativeSourcesPath(@NotNull final String relativeSourcesPath) {
        myRelativeSourcesPath = relativeSourcesPath;
    }

    public void setSdk(@Nullable final Sdk sdk) {
        mySdk = sdk;
    }

    public List<Pair<String, String>> getSourcePaths() {
        throw new UnsupportedOperationException();
    }

    public void setSourcePaths(final List<Pair<String, String>> sourcePaths) {
        throw new UnsupportedOperationException();
    }

    public void addSourcePath(final Pair<String, String> sourcePathInfo) {
        throw new UnsupportedOperationException();
    }
}
