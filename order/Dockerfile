FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8065
ENTRYPOINT ["java","-jar","/app.jar"] 