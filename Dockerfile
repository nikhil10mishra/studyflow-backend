FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

CMD ["sh", "-c", "java -jar target/studyflow-backend-0.0.1-SNAPSHOT.jar --server.port=$PORT"]