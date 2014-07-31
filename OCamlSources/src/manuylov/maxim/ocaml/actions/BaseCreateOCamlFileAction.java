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

package manuylov.maxim.ocaml.actions;

import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateElementActionBase;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import manuylov.maxim.ocaml.util.OCamlFileUtil;
import manuylov.maxim.ocaml.util.OCamlModuleUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.regex.Pattern;

/**
 * @author Maxim.Manuylov
 *         Date: 13.04.2010
 */
abstract class BaseCreateOCamlFileAction extends CreateElementActionBase {
    @NotNull private static final Pattern MODULE_NAME_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9_']*");

    protected BaseCreateOCamlFileAction(@NotNull final String text, @NotNull final String description, @NotNull final Icon icon) {
        super(text, description, icon);
    }

    @NotNull
    protected abstract String getCapitalizedType();

    @NotNull
    protected abstract FileType getFileType();

    @NotNull
    @Override
    protected PsiElement[] invokeDialog(@NotNull final Project project, @NotNull final PsiDirectory directory) {
        final MyInputValidator validator = new MyInputValidator(project, directory);
        Messages.showInputDialog(project, "Enter module name:", "New OCaml Module " + getCapitalizedType() + " File", Messages.getQuestionIcon(), null, validator);
        return validator.getCreatedElements();
    }

    @Override
    protected void checkBeforeCreate(@NotNull final String newName, @NotNull final PsiDirectory directory) throws IncorrectOperationException {
        if (!MODULE_NAME_PATTERN.matcher(newName).matches()) {
            throw new IncorrectOperationException("Incorrect module name");
        }
        directory.checkCreateFile(getFileName(newName));
    }

    @NotNull
    protected String getFileName(@NotNull final String newName) {
        return OCamlFileUtil.getFileName(newName, getFileType());
    }

    @NotNull
    @Override
    protected PsiElement[] create(final String newName, final PsiDirectory directory) throws Exception {
        return new PsiElement[] { directory.createFile(getFileName(newName)) };
    }

    @Override
    protected boolean isAvailable(@NotNull final DataContext dataContext) {
        final Project project = PlatformDataKeys.PROJECT.getData(dataContext);
        if (project == null) {
          return false;
        }

        if (DumbService.getInstance(project).isDumb() && !isDumbAware()) {
          return false;
        }

        final IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (view == null || view.getDirectories().length == 0) {
          return false;
        }

        final PsiDirectory dir = view.getOrChooseDirectory();
        if (dir == null) {
            return false;
        }

        final VirtualFile virtualDir = dir.getVirtualFile();

        final Module module = ModuleUtil.findModuleForFile(virtualDir, project);
        //noinspection SimplifiableIfStatement
        if (!OCamlModuleUtil.isOCamlModule(module)) {
            return false;
        }

        return ModuleRootManager.getInstance(module).getFileIndex().isInSourceContent(virtualDir);
    }

    @Override
    @NotNull
    protected String getErrorTitle() {
        return "Cannot create OCaml module " + getType() + " file";
    }

    @NotNull
    private String getType() {
        return getCapitalizedType().toLowerCase();
    }

    @Override
    @NotNull
    protected String getCommandName() {
        return "Create OCaml module " + getType() + " file";
    }

    @Override
    @NotNull
    protected String getActionName(@NotNull final PsiDirectory directory, @NotNull final String newName) {
        return "Creating file \"" + FileUtil.toSystemDependentName(new File(directory.getVirtualFile().getPath(), getFileName(newName)).getAbsolutePath()) + "\"...";
    }
}