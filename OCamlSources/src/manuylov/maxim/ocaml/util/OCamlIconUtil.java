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

package manuylov.maxim.ocaml.util;

import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
public class OCamlIconUtil {
    @NotNull private static final Icon ourMLFileIcon = IconLoader.getIcon("/img/ml-file.png");
    @NotNull private static final Icon ourMLIFileIcon = IconLoader.getIcon("/img/mli-file.png");
    @NotNull private static final Icon ourOCamlBigIcon = IconLoader.getIcon("/img/ocaml-big.png");
    @NotNull private static final Icon ourOCamlSmallIcon = IconLoader.getIcon("/img/ocaml-small.png");
    @NotNull private static final Icon ourCloseViewIcon = IconLoader.getIcon("/img/cancel.png");
    @NotNull private static final Icon ourSettingsIcon = IconLoader.getIcon("/img/settings.png");
    @NotNull private static final Icon ourOpenConsoleIcon = IconLoader.getIcon("/img/open-console.png");

    @NotNull
    public static Icon getSmallOCamlIcon() {
        return ourOCamlSmallIcon;
    }

    @NotNull
    public static Icon getBigOCamlIcon() {
        return ourOCamlBigIcon;
    }

    @NotNull
    public static Icon getMLFileIcon() {
        return ourMLFileIcon;
    }

    @NotNull
    public static Icon getMLIFileIcon() {
        return ourMLIFileIcon;
    }

    @NotNull
    public static Icon getOpenConsoleIcon() {
        return ourOpenConsoleIcon;
    }

    @NotNull
    public static Icon getCloseViewIcon() {
        return ourCloseViewIcon;
    }

    @NotNull
    public static Icon getSettingsIcon() {
        return ourSettingsIcon;
    }
}
