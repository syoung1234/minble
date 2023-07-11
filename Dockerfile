FROM amazoncorretto:17
# FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} minble.jar
# COPY build/libs/*.jar minble.jar
ENTRYPOINT ["java","-jar","/minble.jar"]

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
