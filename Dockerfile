FROM openjdk:17
MAINTAINER Vitaly Poptsov <stalker-kzn@mail.ru>
ARG JAR_FILE=/target/SpringbootProject-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8082