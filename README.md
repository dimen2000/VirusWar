# Война вирусов
## Лабораторная работа №1
### Использование технологии rmi

Правила игры

+ Играют в «войну вирусов» два игрока на доске 10 на 10 клеток, один крестиками, другой ноликами.
+ Ходят поочерёдно. Начинают крестики. Каждый ход состоит из трёх отдельных последовательных ходов (назовём их «ходиками»).
+ Каждый «ходик» является либо размножением, либо убиванием. Размножение — это выставление своего символа в любую доступную пустую клетку доски, а убивание — это объявление убитым некоторого чужого символа, который находится на доступной клетке.
+ Клетка считается доступной для крестиков, если она либо непосредственно соприкасается (по вертикали, горизонтали или диагонали) с живым крестиком, либо через цепочку убитых ноликов(но не через цепочку убитых крестиков!).
+ Аналогично определяются клетки, доступные для ноликов: либо непосредственно соприкасающиеся с одним из ноликов, либо через цепочку убитых крестиков.
+ Убитые крестики обводятся кружком, убитые нолики закрашиваются. Если игра ведётся не на бумажной доске, а при помощи доски «многоразового использования» и фишек с изображениями крестиков и ноликов, то убитую фишку надо накрыть своей фишкой.
+ В начале игры доска пуста, и полей доступных для крестиков нет, поэтому в порядке исключения они имеют право сделать свой первый «ходик» на a1. Точно также нолики имеют право своим первым «ходиком» выставиться на j9.

Запрещается:
+ Ставить свой символ в уже занятую клетку.
+ Убивать уже убитые символы противника.