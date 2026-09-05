# Lawndesk

🇬🇧 [English version](#lawndesk-1)

Lawndesk — форк [Lawnchair](https://github.com/LawnchairLauncher/Lawnchair): Android-лаунчер без app drawer.

## Предыстория

Эта ветка появилась из-за нескольких конкретных проблем, всплывших в повседневном использовании:

- **Нельзя было выбрать, какая страница рабочего стола — «домашняя».** Лаунчер всегда открывался на странице 0, и нажатие Home всегда возвращало туда же. Закрепить другую страницу как настоящий домашний экран было нельзя.
- **Нельзя было расширить рабочий стол влево.** Стоковый Launcher3 умеет добавлять новую страницу только с правого края — перетаскиванием иконки за последнюю страницу. Аналогичного действия для левого края не существовало.

## Текущее состояние

### Настраиваемая домашняя страница
В настройках рабочего стола появился пункт **Home screen** со списком текущих страниц рабочего стола. Выбор сохраняется по стабильному screen id страницы, а не по её позиции, поэтому переживает изменение порядка страниц. Он применяется при холодном старте лаунчера — но не при восстановлении после поворота экрана либо смены конфигурации и не в режиме скриншота — и при каждом нажатии Home: переход выполняется сразу одним скроллом на выбранную страницу, а не через страницу 0. Если сохранённая страница больше не существует (например, была удалена), лаунчер откатывается на первую страницу вместо падения.

### Добавление страницы слева
Долгое нажатие на пустом месте рабочего стола теперь предлагает пункт **Add page to left** рядом с «Обои», «Виджеты» и «Настройки». Он доступен только при разблокированном рабочем столе — так же, как «Виджеты». Пункт вставляет новую пустую страницу в самое левое положение рабочего стола и сразу переключает на неё. Новая страница живёт по обычному жизненному циклу: остаётся, если на неё что-то добавили, и автоматически убирается, если осталась пустой, — как любая другая неиспользуемая страница.

## Лицензия
Lawndesk распространяется под лицензией [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Благодарности
[Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair)

---

# Lawndesk

Lawndesk is a fork of [Lawnchair](https://github.com/LawnchairLauncher/Lawnchair): an Android launcher without an app drawer.

## Background

This branch grew out of a few concrete gaps found while using the launcher day to day:

- **No way to choose which workspace page is "home."** The launcher always opened on page 0, and pressing Home always returned there, with no way to pin a different page as the actual home screen.
- **No way to grow the workspace to the left.** Stock Launcher3 can only add a new page at the right edge, by dragging an icon past the last page. There was no equivalent action for the left edge.

## Current state

### Configurable home page
Desktop settings now include a **Home screen** picker that lists the current workspace pages. The selection is stored by the page's stable screen id rather than its position, so it survives page reordering. It is applied on a fresh launch of the launcher — but not on rotation or configuration-change restores, and not in screenshot mode — and on every Home button press: the launcher snaps directly to the chosen page in a single scroll instead of bouncing through page 0. If the saved page no longer exists (for example, it was removed), the launcher falls back to the first page instead of failing.

### Add page to left
Long-pressing empty desktop space now offers an **Add page to left** option next to Wallpaper, Widgets and Settings. It is available only while the desktop is unlocked, just like Widgets. The option inserts a new, empty page at the very left of the workspace and slides over to it. A newly added page follows the normal page lifecycle: it sticks once something is placed on it, and is reclaimed automatically if left empty, like any other unused page.

## License
Lawndesk is distributed under the [GPLv3 license](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Credit
[Lawnchair Launcher](https://github.com/LawnchairLauncher/Lawnchair)
