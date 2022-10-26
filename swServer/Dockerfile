FROM openjdk:17-jdk
VOLUME /tmp
ARG JAR_FILE=./swServer/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
