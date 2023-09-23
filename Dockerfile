FROM ubuntu:latest
LABEL authors="Boburjon"

COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
