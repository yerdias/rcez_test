# README

## Конфигурация

Файл конфигурации `application.yaml` содержит настройки, необходимые для работы приложения. Этот файл находится в корневой директории проекта.

### Настройки

В файле `application.yaml` необходимо указать следующие параметры:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testdb
    username: postgres
    password: 1234
  jpa:
    generate-ddl: true
