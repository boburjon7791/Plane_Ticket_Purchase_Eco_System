FROM registry.access.redhat.com/ubi8/openjdk-17:0.4

COPY . /app

RUN mvn compile exec:java -Dexec.mainClass=com.example.DemoApplication

EXPOSE 8080