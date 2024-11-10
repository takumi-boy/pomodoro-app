# ベースイメージとして OpenJDK を使用
FROM openjdk:17-jdk-slim

# 作業ディレクトリを作成
WORKDIR /app

# プロジェクトのファイルをすべてコピー
COPY . .

# Maven Wrapper に実行権限を付与
RUN chmod +x mvnw

# Maven Wrapper を使用して依存関係を解決し、パッケージを作成
RUN ./mvnw clean package

# アプリケーションの JAR ファイルを指定して実行
ENTRYPOINT ["java", "-jar", "target/pomodoro-0.0.1-SNAPSHOT.jar"]
