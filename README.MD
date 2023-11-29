# Overview

Разработать консольное приложение для Clever-Bank.

## Стек

- Java 17
- Gradle
- PostgreSQL
- JDBC
- Lombok
- log4j2

**_Запрещены_** к условию по заданию:

- Spring
- Hibernate

## База данных

Основные сущности:

- Банк (>=5)
- Пользователь (>=20)
- Счёт (>=40)
- Транзакция

У пользователей могут быть счета в разных банках.

## Предусловия

1. Перед первым запуском необходимо создать application.properties в директории resources:
   ``src/main/resources/application.properties``
2. application.properties должен содержать следующие значения:
    - jdbc.driver.name
    - jdbc.url
    - jdbc.user
    - jdbc.password
    - jdbc.database

Пример заполнения: [application.properties](example%2Fapplication.properties). Данный файл готов к использованию.

```
jdbc.driver.name=org.postgresql.Driver
jdbc.url=jdbc:postgresql://localhost:5432/
jdbc.user=postgres
jdbc.password=postgresql
jdbc.database=clever_bank
```