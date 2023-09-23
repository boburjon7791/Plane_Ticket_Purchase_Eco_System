FROM ubuntu:latest
LABEL authors="Boburjon"

ENTRYPOINT ["java", "-jar", "/build/libs/demo-0.0.1-SNAPSHOT.jar"]