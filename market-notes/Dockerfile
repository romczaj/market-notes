FROM amazoncorretto:21

LABEL maintainer="ajemromek@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=/main/target/*.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]