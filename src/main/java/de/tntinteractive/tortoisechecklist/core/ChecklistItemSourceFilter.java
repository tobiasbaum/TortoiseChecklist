/*
    Copyright (C) 2014  Tobias Baum <tbaum at tntinteractive.de>

    This file is a part of TortoiseChecklist.

    TortoiseChecklist is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TortoiseChecklist is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TortoiseChecklist.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.tntinteractive.tortoisechecklist.core;

import java.util.List;

public abstract class ChecklistItemSourceFilter {

    public abstract boolean matches(
            String wcRoot, List<String> relativePaths, String commitComment, PasswordManager passwords) throws Exception;

    public ChecklistItemSourceFilter or(final ChecklistItemSourceFilter f) {
        if (this instanceof FileFilter && f instanceof FileFilter) {
            return new OrFileFilter((FileFilter) this, (FileFilter) f);
        } else {
            return new OrFilter(this, f);
        }
    }

    public ChecklistItemSourceFilter and(final ChecklistItemSourceFilter f) {
        return new AndFilter(this, f);
    }

    public ChecklistItemSourceFilter xor(final ChecklistItemSourceFilter f) {
        return new XorFilter(this, f);
    }

    public ChecklistItemSourceFilter not() {
        return new NotFilter(this);
    }

}
