FROM gradle:8.2.1-jdk17

COPY . /app

RUN gradle build

FROM registry.access.redhat.com/ubi8/openjdk-17:0.4

COPY --from=0 /app/build/libs/*.jar /demo-0.0.1-SNAPSHOT.jar.jar

EXPOSE 8080

CMD ["java", "-jar", "/build/libs/demo-0.0.1-SNAPSHOT.jar"]