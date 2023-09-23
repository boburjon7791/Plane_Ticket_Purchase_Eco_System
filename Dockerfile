FROM openjdk:17

# wget va unzip o'rnatish
RUN apt-get update && apt-get install -y wget unzip

# Gradle-ni o'rnatish
RUN wget https://services.gradle.org/distributions/gradle-8.2.1-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-*.zip && \
    ln -s /opt/gradle/gradle-8.2.1/bin/gradle /usr/bin/gradle && \
    rm -rf /tmp/gradle-*.zip

COPY . /app

RUN gradle build

EXPOSE 8080

CMD ["java", "-jar", "/app/build/libs/*.jar"]
