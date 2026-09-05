# Lawndesk

Lawndesk — Android-лаунчер без app drawer, сделанный на базе [Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair).

## Предыстория

Эта ветка появилась из-за нескольких конкретных проблем, вскрывшихся в повседневном использовании:

- **Нельзя было выбрать, какая страница рабочего стола — «домашняя».** Лаунчер всегда открывался на странице 0, и нажатие Home всегда возвращало туда же — закрепить другую страницу как настоящий домашний экран было нельзя.
- **Нельзя было расширить рабочий стол влево.** Стоковый Launcher3 умеет добавлять новую страницу только с правого края (перетаскиванием иконки за последнюю страницу). Аналогичного действия для левого края не существовало.

## Current state

### Configurable home page
Desktop settings expose a page picker ("Home screen") listing the current workspace pages. The selection is stored by the page's stable screen id rather than its position, so it survives page reordering. It is applied:
- on a fresh launch of the launcher (not on rotation/configuration-change restores, and not in screenshot mode), and
- on every Home button press, snapping directly to the chosen page in a single scroll instead of bouncing through page 0 first.

If the saved page no longer exists (for example, it was removed), the launcher falls back to the first page instead of failing.

### Add page to the left
Long-pressing empty desktop space now offers an "Add page to left" option alongside Wallpaper/Widgets/Settings, gated behind the same "lock desktop" setting as Widgets. It inserts a new, empty page at the very left of the workspace and slides over to it, mirroring the existing right-edge growth behavior. A newly added empty page follows the normal page lifecycle: it sticks once something is dropped on it, and is otherwise reclaimed automatically like any other unused page.

### Release build & signing
The project builds and ships as a signed **release** build:
- Release signing is wired through `build.gradle`, reading keystore and key credentials from environment variables rather than checking signing material into the source tree.
- Continuous integration produces the signed release APK as a build artifact.

## License
Lawndesk is distributed under the [*GPLv3* license](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Credit
[Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair)
