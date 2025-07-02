FROM eclipse-temurin:17-jdk
WORKDIR /app

# ソース一式をコピー
COPY . /app

# Gradle Wrapperでビルド（テストをスキップ）
RUN chmod +x ./gradlew \
    && ./gradlew clean bootJar -x test

# 出力先のJAR名を合わせる
EXPOSE 8080
CMD ["java", "-jar", "build/libs/MBTI_git-0.0.1-SNAPSHOT.jar"]