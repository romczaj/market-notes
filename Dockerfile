FROM eclipse-temurin:21

LABEL maintainer="ajemromek@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} my-application.jar

ENTRYPOINT ["java","-jar","/my-application.jar"]