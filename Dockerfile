# Asosiy tasvirni tanlash
FROM openjdk:17-jdk-slim

# Muallif haqida ma'lumot
MAINTAINER yourname.com

# Gradle yordamida jar faylini yaratish
RUN ./gradle clean build -x test

# Jar faylini Docker konteyneriga nusxa ko'chirish
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# Docker konteynerini ishga tushirish
ENTRYPOINT ["java","-jar","/app.jar"]
