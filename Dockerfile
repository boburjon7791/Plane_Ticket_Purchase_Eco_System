# Asosiy image sifatida OpenJDK 17 ni tanlaymiz
FROM openjdk:17-jdk

# Muallif ma'lumotlarini belgilaymiz
LABEL authors="Boburjon"

# Jar faylini saqlash uchun papka yaratamiz
WORKDIR /app

# build.gradle va gradlew fayllarini image ga nusxa olamiz
COPY build.gradle gradlew /app/
COPY gradle /app/gradle

# Gradle ni ishga tushirib lozimli kutubxonalarni yuklab olamiz
#RUN ./gradlew build --no-daemon
#|| return 0
# Manba kodni image ga nusxa olamiz
COPY src /app/src

# Jar faylini yaratamiz
#RUN ./gradlew build --no-daemon

# Jar faylini ishga tushirish uchun kirish nuqtasini belgilaymiz
ENTRYPOINT ["java", "-jar", "/app/build/libs/demo-0.0.1-SNAPSHOT.jar"]
