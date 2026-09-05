# Lawndesk

Lawndesk is an Android launcher without app drawer, based on [Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair).

[Download](https://github.com/renzhn/Lawndesk/releases)

## Background

This branch grew out of a few concrete gaps found while using the launcher day to day:

- **No way to choose which workspace page is "home."** The launcher always opened on page 0, and pressing Home always returned there, with no way to pin a different page as the actual home screen.
- **No way to grow the workspace to the left.** Stock Launcher3 can only add a new page at the right edge (by dragging an icon past the last page). There was no equivalent action for the left edge.
- **Debug-only diagnostics were leaking into normal use.** A debug notification fired on every package-manager event (app installed/updated/removed), surfacing internal details ("Lawndesk Debug: PackageUpdatedTask, op: 2, packages: [...]") that a real user has no reason to see. This turned out to be a symptom of the app being built and distributed as a **debug** build instead of a signed **release** build — debug builds carry extra logging/instrumentation gated behind `BuildConfig.DEBUG`, and are correspondingly heavier and slower than a release build.

## Current state

### Configurable home page
Desktop settings expose a page picker ("Home screen") listing the current workspace pages. The selection is stored by the page's stable screen id rather than its position, so it survives page reordering. It is applied:
- on a fresh launch of the launcher (not on rotation/configuration-change restores, and not in screenshot mode), and
- on every Home button press, snapping directly to the chosen page in a single scroll instead of bouncing through page 0 first.

If the saved page no longer exists (for example, it was removed), the launcher falls back to the first page instead of failing.

### Add page to the left
Long-pressing empty desktop space now offers an "Add page to left" option alongside Wallpaper/Widgets/Settings, gated behind the same "lock desktop" setting as Widgets. It inserts a new, empty page at the very left of the workspace and slides over to it, mirroring the existing right-edge growth behavior. A newly added empty page follows the normal page lifecycle: it sticks once something is dropped on it, and is otherwise reclaimed automatically like any other unused page.

### Release build & signing
The project now builds and ships as a signed **release** build rather than a debug build:
- Release signing is wired through `build.gradle`, reading keystore and key credentials from environment variables rather than checking signing material into the source tree.
- Continuous integration produces the signed release APK as a build artifact.
- Because `BuildConfig.DEBUG` is `false` in a release build, the debug package-update notifications described above no longer appear — building the correct variant was the fix, no additional code changes were needed for that specifically.

## License
Lawndesk is distributed under the [*GPLv3* license](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Credit
[Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair)
