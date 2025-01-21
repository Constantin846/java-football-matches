# Football Matches
### Описание
Приложение состовляет статистику футбольных матчах.

Сервис регистрирует результаты матча, у которого указаны сезон, дата проведения, 
команда хозяев и гостей, итоговый счет.
Также сервис формирует турнирную таблицу на указанную дату.

### Запуск проекта
* Склонируйте репозиторий: git clone https://github.com/Constantin846/java-football-matches.git
* Перейдите в директорию проекта: cd java-football-matches
* Создайте исполняемый jar: mvn package
* Создайте и запустите docker контейнер: docker-compose up

### Технологии
* Java
* Spring Boot
* JPA, Hibernate
* SQL (PostgreSQL)
* Maven
* Docker