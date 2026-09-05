# Lawndesk

[русский](#русский) | [English](#english)

---

## русский

Lawndesk — Android-лаунчер без app drawer, сделанный на базе [Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair).

### Предыстория

Эта ветка появилась из-за нескольких конкретных проблем, вскрывшихся в повседневном использовании:

- **Нельзя было выбрать, какая страница рабочего стола — «домашняя».** Лаунчер всегда открывался на странице 0, и нажатие Home всегда возвращало туда же — закрепить другую страницу как настоящий домашний экран было нельзя.
- **Нельзя было расширить рабочий стол влево.** Стоковый Launcher3 умеет добавлять новую страницу только с правого края (перетаскиванием иконки за последнюю страницу). Аналогичного действия для левого края не существовало.

### текущее состояние

#### Настраиваемая домашняя страница
в настройках рабочего стола появился выбор страницы («Домашний экран») со списком текущих страниц рабочего стола. выбор сохраняется по стабильному screen id страницы, а не по её позиции, поэтому переживает изменение порядка страниц. апплицируется:
- при холодном старте лаунчера (не при восстановлении после поворота экрана/смены конфигурации и не в режиме скриншота), и
- при каждом нажатии Home — сразу одним скроллом переходит на выбранную страницу, вместо того чтобы сначала прыгать на страницу 0.

если сохранённая страница больше не существует (например, была удалена), лаунчер откатывается на первую страницу вместо падения.

#### Добавление страницы слева
долгое нажатие на пустом месте рабочего стола теперь предлагает пункт «Add page to left» рядом с Wallpaper/Widgets/Settings, скрытый за той же настройкой «lock desktop», что и Widgets. он вставляет новую пустую страницу в самое левое положение рабочего стола и переключает на неё. новая пустая страница живёт по обычному жизненному циклу страницы: остаётся, если на неё что-то бросили, и автоматически убирается, если осталась пустой — как и любая другая неиспользуемая страница.

#### Release-сборка и подпись
проект теперь собирается и распространяется как подписанная **release**-сборка:
- подпись релиза настроена через `build.gradle`, ключ и пароли берутся из переменных окружения, а не хранятся в исходниках.
- CI собирает подписанный release APK как артефакт сборки.

### Лицензия
Lawndesk распространяется под лицензией [*GPLv3*](https://www.gnu.org/licenses/gpl-3.0.en.html).

### Благодарности
[Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair)

---

## English

Lawndesk is an Android launcher without app drawer, based on [Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair).

### Background

This branch grew out of a few concrete gaps found while using the launcher day to day:

- **No way to choose which workspace page is "home."** The launcher always opened on page 0, and pressing Home always returned there, with no way to pin a different page as the actual home screen.
- **No way to grow the workspace to the left.** Stock Launcher3 can only add a new page at the right edge (by dragging an icon past the last page). There was no equivalent action for the left edge.

### Current state

#### Configurable home page
Desktop settings expose a page picker ("Home screen") listing the current workspace pages. The selection is stored by the page's stable screen id rather than its position, so it survives page reordering. It is applied:
- on a fresh launch of the launcher (not on rotation/configuration-change restores, and not in screenshot mode), and
- on every Home button press, snapping directly to the chosen page in a single scroll instead of bouncing through page 0 first.

If the saved page no longer exists (for example, it was removed), the launcher falls back to the first page instead of failing.

#### Add page to the left
Long-pressing empty desktop space now offers an "Add page to left" option alongside Wallpaper/Widgets/Settings, gated behind the same "lock desktop" setting as Widgets. It inserts a new, empty page at the very left of the workspace and slides over to it, mirroring the existing right-edge growth behavior. A newly added empty page follows the normal page lifecycle: it sticks once something is dropped on it, and is otherwise reclaimed automatically like any other unused page.

#### Release build & signing
The project builds and ships as a signed **release** build:
- Release signing is wired through `build.gradle`, reading keystore and key credentials from environment variables rather than checking signing material into the source tree.
- Continuous integration produces the signed release APK as a build artifact.

### License
Lawndesk is distributed under the [*GPLv3* license](https://www.gnu.org/licenses/gpl-3.0.en.html).

### Credit
[Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair)
