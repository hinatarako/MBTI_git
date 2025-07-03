FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY . /app

RUN chmod +x ./gradlew \
 && ./gradlew clean bootJar -x test

EXPOSE 8080
CMD ["sh", "-c", "java -jar build/libs/*.jar"]