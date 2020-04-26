FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/rulette-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

