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

package manuylov.maxim.ocaml.lang.feature.resolving;

import manuylov.maxim.ocaml.lang.feature.resolving.util.OCamlResolvingUtil;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlExtendedModuleName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 28.03.2009
 */
public class ResolvingBuilder {
    @NotNull private final OCamlResolvedReferencesProcessor myProcessor;
    @NotNull private ResolvingContext myContext;

    private boolean myResolvingFinished = false;
    private OCamlElement myLastParent = null;
    private ElementPosition myLastParentPosition = null;
    private boolean myIsPervasivesProcessing = false;
    private boolean myIsInPervasives = false;
    private int myModulePathOffset = 0;

    public ResolvingBuilder(@NotNull final OCamlResolvedReferencesProcessor processor, @NotNull final ResolvingContext context) {
        myProcessor = processor;
        myContext = context;
        myProcessor.setResolvingBuilder(this);
    }

    @NotNull
    public OCamlResolvedReferencesProcessor getProcessor() {
        return myProcessor;
    }

    @NotNull
    public ResolvingContext getContext() {
        return myContext;
    }

    @Nullable
    public OCamlElement getLastParent() {
        return myLastParent;
    }

    @Nullable
    public ElementPosition getLastParentPosition() {
        return myLastParentPosition;
    }

    public void setLastParent(@NotNull final OCamlElement lastParent) {
        myLastParent = lastParent;
    }

    public void setLastParentPosition(@NotNull final ElementPosition lastParentPosition) {
        myLastParentPosition = lastParentPosition;
    }

    public void pervasivesProcessingStarted() {
        myIsPervasivesProcessing = true;
        myIsInPervasives = false;
    }

    public void pervasivesProcessingFinished() {
        myIsPervasivesProcessing = false;
    }

    public boolean canProcessElement() {
        return !myResolvingFinished && myModulePathOffset == myContext.getModulePath().size();
    }

    @Nullable
    public String getCurrentModuleName() {
        final List<? extends OCamlExtendedModuleName> modulePath = myContext.getModulePath();
        return myModulePathOffset < modulePath.size() ? modulePath.get(myModulePathOffset).getName() : null;
    }

    public int getModulePathOffset() {
        return myModulePathOffset;
    }

    public boolean childWasAlreadyProcessed(@NotNull final OCamlElement childElement) {
        return myLastParentPosition == ElementPosition.Child && myLastParent == childElement;
    }

    public boolean tryProcessModule(@NotNull final String moduleName, @NotNull final ModuleProcessor... processors) {
        if (processModuleStart(moduleName)) {
            try {
                for (final ModuleProcessor processor : processors) {
                    if (processor != null && processor.process()) {
                        return true;
                    }
                }
                return false;
            }
            finally {
                processModuleEnd(moduleName);
            }
        }
        return false;
    }

    private boolean processModuleStart(@NotNull final String moduleName) {
        if (myIsPervasivesProcessing && !myIsInPervasives && myModulePathOffset == 0 && moduleName.equals(OCamlResolvingUtil.PERVASIVES)) {
            myIsInPervasives = true;
            return true;
        }

        final List<? extends OCamlExtendedModuleName> modulePath = myContext.getModulePath();
        if (myModulePathOffset < modulePath.size() && moduleName.equals(modulePath.get(myModulePathOffset).getName())) {
            myModulePathOffset++;
            return true;
        }

        return false;
    }

    private void processModuleEnd(final String moduleName) {
        if (myIsPervasivesProcessing && myIsInPervasives && moduleName.equals(OCamlResolvingUtil.PERVASIVES)) {
            myModulePathOffset = 0;
            return;
        }
        myResolvingFinished = true;
    }

    public static interface ModuleProcessor {
        boolean process();
    }
}
