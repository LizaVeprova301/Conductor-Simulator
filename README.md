### Мобильная игра Conductor-Simulator

#### Описание игры
Игра представляет собой симулятор, в котором игрок выступает в роли кондуктора трамвая. Основная задача игрока — собирать оплату за проезд от пассажиров, перемещаясь между вагонами. Игрок должен следить за своим балансом, так как за каждого пассажира, который не оплатил проезд, кондуктор получает штраф. Игра продолжается до тех пор, пока у кондуктора не закончатся деньги.

#### Основные механики игры
1. Сбор оплаты: Игрок должен подходить к пассажирам и просить их оплатить проезд. Успешная оплата увеличивает баланс и счет игрока, а неудачная — уменьшает. 
2. Штрафы: Если пассажир не оплатил проезд, кондуктор получает штраф, который вычитается из его баланса и счета в целом, но в разных пропорциях.
3. Уровень сложности: С каждой остановкой количество пассажиров увеличивается, они становятся более быстрыми, а остановки происходят чаще.
4. Пассажиры: Каждый пассажир имеет свои характеристики, такие как уровень недовольства и количество остановок, которые ему нужно проехать, оплачен ли у него билет.
5. Время суток: Игра меняет время суток, что влияет на атмосферу и визуальные эффекты.

#### Графика и технологии
Для реализации графики и игры в целом можно использовать следующие технологии и инструменты:

1. Game Engine: 
   - LibGDX: 
   - Unity с Kotlin/Native:

2. Графика:
   - Spine или DragonBones: Для анимации персонажей и пассажиров можно использовать инструменты для создания 2D-скелетной анимации.
   - Tiled Map Editor: Для создания уровней и карт можно использовать Tiled, который позволяет легко проектировать 2D-игровые уровни.

3. UI:
   - Для создания пользовательского интерфейса можно использовать встроенные возможности LibGDX или другие библиотеки, такие как Scene2D.

4. База данных:
   - Для хранения данных о прогрессе игрока и его достижениях  используем локальное  хранение данных.

#### План разработки
1. Прототипирование:
   - Создание базового прототипа игры с основными механиками (сбор оплаты, штрафы, перемещение между вагонами).

2. Разработка графики:
   - Создание графических ресурсов (персонажи, вагоны, фон) и анимаций.

3. Разработка логики игры:
   - Реализация механик игры, таких как сбор оплаты, штрафы, уровень сложности и поведение пассажиров.

4. Тестирование:
   - Проведение тестирования на различных устройствах для выявления и исправления ошибок.

5. Оптимизация:
   - Оптимизация производительности игры, чтобы обеспечить плавный игровой процесс.

6. Запуск и маркетинг:
   - Подготовка игры к запуску на маркетенговыъх платформах
