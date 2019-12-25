# Card Delivery Order Test Mode Reporting
[![Build status](https://ci.appveyor.com/api/projects/status/n22yhfyo1h4e2062?svg=true)](https://ci.appveyor.com/project/VEAlekseev/reporting)

## Инструкция по запуску :wink:

##### Склонировать репозиторий git clone https://github.com/VEAlekseev/Reporting.git
##### Перейти в директорию с проектом
##### Перейти в каталог с файлом jar cd artifacts и запустить целевой сервис набрав в консоли команду java -jar app-card-delivery.jar
##### Выполнить команду gradlew.bat test
##### Для запуска тестов Allure выполнить команду gradlew clean test allureReport для просмотра результатов gradlew allureServe
