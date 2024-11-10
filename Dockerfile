# ベースイメージとして OpenJDK を使用
FROM openjdk:17-jdk-slim

# アプリケーションの JAR ファイルを指定
ARG JAR_FILE=target/pomodoro-0.0.1-SNAPSHOT.jar

# JAR ファイルをコンテナにコピー
COPY ${JAR_FILE} app.jar

# アプリケーションを実行
ENTRYPOINT ["java", "-jar", "/app.jar"]
