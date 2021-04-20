### Древовидное представление 
***

####1. Создание БД  
Самостоятельно создать POSTGRE базу, указав url в application.properties и выполнив скрипт deploy/sql/init.sql.
  Или с помощью docker-compose:  
   `docker-compose up` - запустить контейнер с БД  
   `docker-compose down` - остановить контейнер с БД.

####2. Сборка проекта
Из корня проекта запустить команду:  
`./gradlew build` - которая соберет war архив по пути `build/libs/tree-1.0-SNAPSHOT.war`

####3. Деплой war-архива в Tomcat
Сначала необходимо установить Tomcat на сервере. Инструкция для Ubuntu 18.04 (20.04)    
https://www.digitalocean.com/community/tutorials/install-tomcat-9-ubuntu-1804-ru
Далее скопировать war архив из `/build/libs` в `/opt/tomcat/webapps` или выполнить   
`./gradlew deploy`.   
Зайти на приложение по адресу `http://localhost:8080/tree-1.0-SNAPSHOT/`

   