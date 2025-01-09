FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle/
RUN ./gradlew build --refresh-dependencies --no-daemon
COPY src /app/src
RUN ./gradlew build --no-daemon
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/build/libs/users-0.0.1-SNAPSHOT.jar"]