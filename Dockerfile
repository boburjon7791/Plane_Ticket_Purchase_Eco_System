FROM openjdk:17

COPY . /app

RUN gradle build

EXPOSE 8080

CMD ["java", "-jar", "/app/build/libs/*.jar"]
