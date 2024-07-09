# SmartHome (Яндекс-лампа)
## Цель проекта
Разработать мобильное приложение под ОС Android для управления умной лампой по Wi-Fi.
## Описание проекта
Мобильное приложение предназначено для управления умной лампой Tuya. Для реализации управления используется открытый Яндекс API с методами GET, POST, DELETE: https://yandex.ru/dev/dialogs/smart-home/doc/en/concepts/platform-protocol.
Применяются следующие методы: получение информации об устройствах пользователя, управление устройством (включение/выключение лампы, регулировка температуры цвета, установка цвета).
## Используемые технологии
1. Clean Architecture (data, domain, presentation).
2. Model-View-ViewModel.
3. Navigation component (навигация между экранами).
4. Retrofit (для работы с сетью).
5. OkHttp (http-клиент, логирование http-запросов).
6. Gson (для преобразования JSON-объектов в Kotlin-объекты и наоборот, сериализация и десериализация).
7. Viewpager (для отображения фрагментов в виде слайдера со вкладками).
8. Room (для локального хранения данных).
## Демонстрация приложения
1. Экран для управления лампой <br> <img src="https://github.com/tdrykova/YandexTest/assets/92788954/12f87547-a472-49ef-aa29-4d36a2561c35" width="240" height="420">
2. Экран для настроек элементов управления <br> <img src="https://github.com/tdrykova/YandexTest/assets/92788954/4d654be8-586b-4ca0-a339-ad7439613321" width="240" height="420"> 

