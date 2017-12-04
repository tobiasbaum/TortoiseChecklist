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
package de.tntinteractive.tortoisechecklist.plugins.basic;

import java.util.Collections;
import java.util.List;

import de.tntinteractive.tortoisechecklist.core.ChecklistItem;
import de.tntinteractive.tortoisechecklist.core.ChecklistItemSource;
import de.tntinteractive.tortoisechecklist.core.PasswordManager;

public class QuestionSource extends ChecklistItemSource {

    private final String text;

    public QuestionSource(final String text) {
        this.text = text;
    }

    @Override
    protected String getDescription() {
        return this.text;
    }

    @Override
    protected List<ChecklistItem> createChecklistItems(
            final String wcRoot,
            final List<String> relativePaths,
            final String commitComment,
            final PasswordManager passwords) {
        return Collections.singletonList(ChecklistItem.createQuestion(this.text));
    }

}
