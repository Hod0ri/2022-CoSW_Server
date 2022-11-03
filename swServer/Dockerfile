FROM openjdk:17-jdk
VOLUME /tmp
ARG JAR_FILE=./build/libs/swServer-0.0.1-SNAPSHOT.jar
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Duser.timezone=Asia/Seoul" ,"/app.jar"]

#
#FROM openjdk:17-jdk AS builder
#
#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle .
#COPY settings.gradle .
#COPY ./src ./src
#
#RUN chmod +x ./gradlew
#RUN ./gradlew bootJar
#
#FROM openjdk:17-jdk
#VOLUME /tmp
#COPY --from=builder build/libs/swServer-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app.jar"]
