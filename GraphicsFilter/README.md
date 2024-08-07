## Лабораторная работа №2

Пользователь имеет возможность открывать файлы изображений (PNG, JPEG, BMP, GIF). После загрузки выбранное изображение 
отображается в области просмотра в режиме реального размера. Имеется возможность переключения
в режим просмотра «подогнать под экран», в таком случае изображение вписывается в область просмотра,
сохраняя соотношения сторон.

Пользователь имеет возможность сохранить текущий результат обработки изображения в файл
в формате PNG.

Реализованы следующие инструменты обработки изображения:
- Перевод цветного изображения в черно-белое (оттенки серого).
- Преобразование изображения в негативное (инверсия).
- Сглаживающий фильтр с выбором размера окна от 3 до 11 (размер окна – нечетный)
- Фильтр повышения резкости.
- Тиснение.
- Гамма-коррекция. Значение параметра гамма ограничено от 0.1 до 10.
- Фильтры выделения границ (операторы Робертса и Собеля) с выбором параметра бинаризации.
- Дизеринг алгоритмом Флойда-Стейнберга с выбором числа квантования для каждого цвета (красного, синего и зеленого). От 2 до 128.
- Упорядоченный дизеринг с выбором числа квантования для каждого цвета. От 2 до 128. Размер матрицы определяется автоматически.
- Акварелизация.
- Поворот изображения на произвольный угол. Значение параметра ограничено от -180 до +180
  градусов. Поворот выполняется относительно центра изображения. Цвет фона – белый.

Дополнительно реализован фильтр матового стекла.

## Дизеринг алгоритмом Флойда-Стейнберга

![](https://github.com/mrgoshha/nsu_computer_graphics/blob/master/GraphicsFilter/images/floydSteinbergDithering.png)

## Упорядоченный дизеринг

![](https://github.com/mrgoshha/nsu_computer_graphics/blob/master/GraphicsFilter/images/orderedDithering.png)

## Тиснение

![](https://github.com/mrgoshha/nsu_computer_graphics/blob/master/GraphicsFilter/images/embossing.png)


## Эффект матового стекла

![](https://github.com/mrgoshha/nsu_computer_graphics/blob/master/GraphicsFilter/images/frostedGlass.png)


