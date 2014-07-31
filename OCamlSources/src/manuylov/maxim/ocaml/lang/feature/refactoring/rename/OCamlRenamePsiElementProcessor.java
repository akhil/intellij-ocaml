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

package manuylov.maxim.ocaml.lang.feature.refactoring.rename;

import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.MultiMap;
import manuylov.maxim.ocaml.entity.OCamlModule;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlReference;
import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlResolvingUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementProcessorAdapter;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Maxim.Manuylov
 *         Date: 03.06.2010
 */
public class OCamlRenamePsiElementProcessor extends RenamePsiElementProcessor {
    @Override
    public boolean canProcessElement(@NotNull final PsiElement element) {
        return element instanceof OCamlElement;
    }

    @Override
    public void findExistingNameConflicts(@NotNull final PsiElement element, @NotNull final String newName, @NotNull final MultiMap<PsiElement, String> conflicts) {
        super.findExistingNameConflicts(element, newName, conflicts);

        //noinspection UnnecessaryLocalVariable
        final PsiElement elementBefore = element;
        final String oldName = getName(elementBefore);
        if (oldName == null) return;

        final Collection<PsiReference> referencesBefore = getReferences(elementBefore, true, null);
        for (final PsiReference referenceBefore : referencesBefore) {
            final PsiElement referenceElementBefore = referenceBefore.getElement();
            if (referenceElementBefore == null) continue;
            final PsiElement referenceElementAfter = OCamlPsiUtil.copy(referenceElementBefore);
            final PsiReference referenceAfter = referenceElementAfter.getReference();
            if (referenceAfter == null) continue;
            PsiElement definitionAfter = referenceAfter.resolve();
            if (definitionAfter == null) continue;
            if (!rename(referenceElementAfter, newName)) continue;
            final PsiFile referenceOriginalFile = referenceElementBefore.getContainingFile();
            final PsiFile referenceFakeFile = referenceElementAfter.getContainingFile();
            final PsiFile definitionOriginalFile = definitionAfter.getContainingFile();
            final PsiFile definitionFakeFile;
            final PsiElement actualDefinitionAfter;
            if (definitionOriginalFile == referenceFakeFile) {
                if (!rename(definitionAfter, newName)) continue;
                definitionFakeFile = null;
                actualDefinitionAfter = referenceAfter.resolve();
            }
            else {
                definitionAfter = OCamlPsiUtil.copy(definitionAfter);
                if (!rename(definitionAfter, newName)) continue;
                definitionFakeFile = definitionAfter.getContainingFile();
                if (!(definitionFakeFile instanceof OCamlFile)) continue;
                actualDefinitionAfter = OCamlResolvingUtil.resolveWithFakeModules(referenceAfter, (OCamlFile) definitionFakeFile);
            }
            if (actualDefinitionAfter == null) continue;
            if (definitionAfter != actualDefinitionAfter) {
                if (!rename(referenceElementAfter, oldName)) continue;
                if (!rename(definitionAfter, oldName)) continue;
                addConflictIfNeeded(conflicts, actualDefinitionAfter, "Warning: %s is already defined.",
                    Pair.create(referenceFakeFile, referenceOriginalFile),
                    Pair.create(definitionFakeFile, definitionOriginalFile)
                );
            }
        }

        final PsiElement elementAfter = OCamlPsiUtil.copy(element);
        if (!rename(elementAfter, newName)) return;

        final PsiFile originalFile = elementBefore.getContainingFile();
        final PsiFile fakeFile = elementAfter.getContainingFile();
        final Collection<PsiReference> newReferencesAfter = getReferences(elementAfter, false, originalFile);
        if (!rename(elementAfter, oldName)) return;
        for (final PsiReference reference : newReferencesAfter) {
            addConflictIfNeeded(conflicts, reference.getElement(), "Warning: there is a usage of %s.", Pair.create(fakeFile, originalFile));
        }
    }

    private boolean rename(@NotNull final PsiElement element, @NotNull final String newName) {
        try {
            if (element instanceof PsiNamedElement) {
                ((PsiNamedElement) element).setName(newName);
                return true;
            }
        }
        catch (final IncorrectOperationException e) {
            return false;
        }
        return false;
    }

    @Nullable
    private String getName(@NotNull final PsiElement element) {
        if (element instanceof PsiNamedElement) {
            return ((PsiNamedElement) element).getName();
        }
        return null;
    }

    private void addConflictIfNeeded(@NotNull final MultiMap<PsiElement, String> conflicts,
                                     @NotNull final PsiElement conflictingElement,
                                     @NotNull final String text,
                                     @NotNull final Pair<PsiFile, PsiFile>... pairs) {
        final PsiElement originalElement = getOriginal(conflictingElement, pairs);
        if (originalElement != null) {
            if (!conflicts.containsKey(originalElement)) {
                conflicts.putValue(originalElement, String.format(text, originalElement.toString()));
            }
        }
    }

    @Nullable
    private PsiElement getOriginal(@NotNull final PsiElement element, @NotNull final Pair<PsiFile, PsiFile>... pairs) {
        final PsiFile elementFile = element.getContainingFile();
        for (final Pair<PsiFile, PsiFile> pair : pairs) {
            final PsiFile fakeFile = pair.getFirst();
            if (fakeFile != null && elementFile == fakeFile) {
                final PsiFile originalFile = pair.getSecond();
                return OCamlPsiUtil.findElementInRange(originalFile, element.getTextRange());
            }
        }
        return element;
    }

    @NotNull
    private Collection<PsiReference> getReferences(@NotNull final PsiElement element, final boolean useCache, @Nullable final PsiFile originalFile) {
        final Collection<PsiReference> references = !useCache && originalFile != null ? findNewReferences(element, originalFile) : findReferences(element);
        //noinspection SuspiciousMethodCalls
        references.remove(element);
        return references;
    }

    @NotNull
    private Collection<PsiReference> findNewReferences(@NotNull final PsiElement element, @NotNull final PsiFile originalFile) {
        final PsiFile psiFile = element.getContainingFile();
        if (!(psiFile instanceof OCamlFile)) return Collections.emptySet();
        final OCamlFile fakeFile = (OCamlFile) psiFile;
        final VirtualFile file = originalFile.getVirtualFile();
        if (file == null) return Collections.emptySet();
        final OCamlModule fileModule = OCamlModule.getBySourceFile(file, element.getProject());
        if (fileModule == null) return Collections.emptySet();
/*
        final Collection<OCamlModule> modules = fileModule.collectAllDependenciesIgnoringCycles(); //todo fix it: should be not dependencies but depends on
        modules.add(fileModule);
*/
        final Collection<PsiReference> references = new HashSet<PsiReference>();
/*
        for (final OCamlModule module : modules) {
            collectReferencesInFile(element, fakeFile, module.getImplementationFile(), references);
            collectReferencesInFile(element, fakeFile, module.getInterfaceFile(), references);
        }
*/
        collectReferences(element, fakeFile, references);
//        collectReferencesInFile(element, fakeFile, fakeFile.getAnotherFile(), references); //todo is it needed?
        return references;
    }

    private void collectReferences(@NotNull final PsiElement element,
                                   @NotNull final OCamlFile fakeFile,
                                   @NotNull final Collection<PsiReference> references) {
        OCamlPsiUtil.acceptRecursively(fakeFile, new OCamlElementProcessorAdapter() {
            public void process(@NotNull final OCamlElement psiElement) {
                final PsiReference reference = psiElement.getReference();
                if (reference == null) return;
                if (reference.isReferenceTo(element)) {
                    references.add(reference);
                }
            }
        });
    }

    private void collectReferencesInFile(@NotNull final PsiElement element,
                                         @NotNull final OCamlFile fakeFile,
                                         @NotNull final File file,
                                         @NotNull final Collection<PsiReference> references) {
        final VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(file);
        if (virtualFile == null) return;
        final PsiFile psiFile = PsiManager.getInstance(element.getProject()).findFile(virtualFile);
        if (psiFile == null) return;
        OCamlPsiUtil.acceptRecursively(psiFile, new OCamlElementProcessorAdapter() {
            public void process(@NotNull final OCamlElement psiElement) {
                final PsiReference reference = psiElement.getReference();
                if (reference == null || !(reference instanceof OCamlReference)) return;
                if (((OCamlReference) reference).isReferenceToWithFakeModules(element, fakeFile)) {
                    references.add(reference);
                }
            }
        });
    }
}
