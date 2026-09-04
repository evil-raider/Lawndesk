/*
 *     This file is part of Lawnchair Launcher.
 *
 *     Lawnchair Launcher is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Lawnchair Launcher is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Lawnchair Launcher.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.android.launcher3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import ch.deletescape.lawnchair.LawnchairLauncher;

/**
 * Lawndesk: a {@link Workspace} that treats the user-configured page (stored under
 * {@link LawnchairLauncher#PREF_DEFAULT_HOME_SCREEN}) as the default/home screen. This makes
 * pressing Home snap straight to the chosen page in a single scroll, instead of first bouncing to
 * page 0 (as the stock {@link Workspace#moveToDefaultScreen()} does) and then being corrected.
 */
public class LawndeskWorkspace extends Workspace {

    public LawndeskWorkspace(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LawndeskWorkspace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    void moveToDefaultScreen() {
        int page = getConfiguredDefaultPage();
        // Mirror the stock guard (workspaceInModalState() == !isInState(NORMAL)) using the
        // package-private mLauncher field, but snap to the configured page instead of page 0.
        if (mLauncher.isInState(LauncherState.NORMAL) && getNextPage() != page) {
            snapToPage(page);
        }
        View child = getChildAt(page);
        if (child != null) {
            child.requestFocus();
        }
    }

    /**
     * Resolves the page index configured as the home page. The preference stores the screen id (as
     * a string); if it is missing, malformed, or no longer exists we fall back to the first page.
     */
    private int getConfiguredDefaultPage() {
        String raw = Utilities.getPrefs(getContext())
                .getString(LawnchairLauncher.PREF_DEFAULT_HOME_SCREEN, "0");
        if (raw == null) {
            return 0;
        }
        long screenId;
        try {
            screenId = Long.parseLong(raw);
        } catch (NumberFormatException e) {
            return 0;
        }
        int index = getPageIndexForScreenId(screenId);
        return (index >= 0 && index < getChildCount()) ? index : 0;
    }
}
