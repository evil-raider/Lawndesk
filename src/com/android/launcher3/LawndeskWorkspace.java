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
     * Lawndesk: create a brand new, empty workspace page at the very left (index 0) and slide over
     * to it. The stock launcher can only grow pages at the right edge (drag an icon past the last
     * page); this exposes the mirror-image action as an explicit desktop long-press option.
     *
     * <p>The new page follows the same lifecycle as any other page: drop at least one item on it
     * and it sticks across relaunches; leave it empty and the launcher reclaims it on the next
     * {@link #stripEmptyScreens()} pass (same as an unused right-edge page).
     */
    public void addEmptyPageToLeft() {
        if (mLauncher.isWorkspaceLoading()) {
            // Adding screens while the model is loading is unsafe and can corrupt the DB.
            return;
        }
        // Generate a fresh, unique screen id from the same source the stock right-edge commit uses.
        long newId = LauncherSettings.Settings.call(getContext().getContentResolver(),
                LauncherSettings.Settings.METHOD_NEW_SCREEN_ID)
                .getLong(LauncherSettings.Settings.EXTRA_VALUE);
        // Insert the page at the very front (index 0 == leftmost page in LTR).
        insertNewWorkspaceScreen(newId, 0);
        // Persist the new order so the page is remembered once it has content.
        LauncherModel.updateWorkspaceScreenOrder(mLauncher, getScreenOrder());
        // Front-insertion shifts every existing page right by one. Pin the currently visible page
        // first (avoids a visual jump), then animate across to the freshly created page.
        setCurrentPage(getNextPage() + 1);
        snapToPage(0);
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
