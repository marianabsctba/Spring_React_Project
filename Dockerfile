FROM eclipse-temurin:21-jdk
LABEL authors="raphael.costa/Mariana.Sukevicz"

VOLUME /tmp
EXPOSE 8083

ADD target/ms-tasks-0.0.1-SNAPSHOT.jar TaskService.jar
ENTRYPOINT ["java","-jar","/TaskService.jar"]