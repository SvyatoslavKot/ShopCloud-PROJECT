# Shop Cloud Project
## Specification
Учебный проект, представляющий собой модель интерент магазина.
Проект разбит на модули, где каждый модуль выполняет свою фукции. 
Модули слабосвязаны и могут работать независимо друг от друга.

### Shop-MODULE
Данный модуль содержит графический интерфейс проекта. 
Взаимодействует с другими сервисами. Получает данные и выводит непосредственно 
пользователю в виде Html ответа, либо обрабатывает данные с html запроса 
и передает другому сервису.

### Auth-MODULE
Модуль Аутендификации и авторизации пользователя

### Order-MODULE
Модуль для обработки заказов 

### Product-MODULE
Модуль для работы с продуктами. Построение связи **_продукт => покупатель_**

### ShopClient-MODULE
Данный модуль содержит все что свезано с Пользователями приложения.

## Tools
В данном проекте активно применятется SpringFramework 
Для взаимодействия между сервисами используются разные способы :
**_REST, GRpc, RabbitMq broker, KafkaMq broker_**.
Хранение данных организованно в реляционноц базе данных Postgresql
Для динамического обновленния данных на странице html использую WebSocket Stomp
Для безопасности приложения использую SpringSecurity с аутендификаций 
через JWT(токен).

---
## Get Stared

### localHost
Данный проект можно запустить локально. Каждый модуль запускаеться поочередно
на своем порту. Предварительно также необходимо убедиться, что запущены 
вспомогательные службы (база данных,брокеры сообщений).

### Docker-compose

Также проект упакован в докер контейнер и опубликован в докер репозитории.
Для каждого модуля свой докер образ.
Все настраеваемые параметры **_application properties _** вынесены в 
переменные окружения образа, что упрощает внесение измений в настройки проекта.


