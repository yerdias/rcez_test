Конфигурация
Файл конфигурации application.yaml содержит настройки, необходимые для работы приложения. Этот файл находится в корневой директории проекта.
Настройки
В файле application.yaml необходимо указать следующие параметры:

server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/testdb
    username: postgres
    password: 1234
  jpa:
    generate-ddl: true
    
Изменение данных
Откройте файл application.yaml в текстовом редакторе.
Измените значение параметров в соответствии с вашими требованиями.
Сохраните изменения в файле.

Пример
Пример измененного файла application.yaml:

server:
  port: 8081  # Изменили порт на 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/proddb  # Изменили URL базы данных
    username: admin  # Изменили имя пользователя
    password: newpassword  # Изменили пароль
  jpa:
    generate-ddl: false  # Изменили параметр генерации DDL
