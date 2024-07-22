#
FROM openjdk:17-jdk-slim-buster AS builder

WORKDIR /app
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src/main ./src/main
RUN ./gradlew bootJar

FROM openjdk:17.0.2-jdk-slim-buster

WORKDIR /app
COPY --from=builder /app/build/libs/security-*.jar app.jar

ENV PROFILE="dev"

ENTRYPOINT java -jar app.jar --spring.profiles.active=$PROFILE

# docker build -t security-study:1.0.0 . -> 빌드
# docker run -p 8080:8080 security-study:1.0.0 -> run
