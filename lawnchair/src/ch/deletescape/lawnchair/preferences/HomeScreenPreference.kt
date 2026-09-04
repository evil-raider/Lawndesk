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

package ch.deletescape.lawnchair.preferences

import android.content.Context
import android.support.v7.preference.ListPreference
import android.util.AttributeSet
import com.android.launcher3.LauncherSettings
import com.android.launcher3.R

/**
 * A [ListPreference] that lets the user pick which workspace page should be used as the home
 * (default) page. The list of pages is built from the currently saved workspace screens, and the
 * stored value is the screen id (as a string) so that the selection survives page reordering.
 */
class HomeScreenPreference(context: Context, attrs: AttributeSet?) : ListPreference(context, attrs) {

    init {
        reloadScreens()
    }

    override fun onClick() {
        // Rebuild the list right before showing the dialog so it always reflects the current pages.
        reloadScreens()
        super.onClick()
    }

    private fun reloadScreens() {
        val labels = ArrayList<CharSequence>()
        val values = ArrayList<CharSequence>()
        try {
            val cursor = context.contentResolver.query(
                    LauncherSettings.WorkspaceScreens.CONTENT_URI,
                    arrayOf("_id"),
                    null, null,
                    LauncherSettings.WorkspaceScreens.SCREEN_RANK)
            cursor?.use {
                val idIndex = it.getColumnIndex("_id")
                var page = 1
                while (it.moveToNext()) {
                    val screenId = it.getLong(idIndex)
                    labels.add(context.getString(R.string.home_screen_page_format, page))
                    values.add(screenId.toString())
                    page++
                }
            }
        } catch (t: Throwable) {
            // Ignore and fall back to a single default entry below.
        }
        if (values.isEmpty()) {
            labels.add(context.getString(R.string.home_screen_page_format, 1))
            values.add("0")
        }
        entries = labels.toTypedArray()
        entryValues = values.toTypedArray()
        if (value == null || !values.contains(value)) {
            value = values[0].toString()
        }
    }
}
