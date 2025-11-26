 url=https://github.com/red1iscoding/TP7_demoSpringBoot/blob/main/Dockerfile
# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage (JRE)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# EXPOSE is informational — Render will provide $PORT at runtime
EXPOSE 8080

# Use the shell form so $PORT is substituted and passed into the JVM
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar /app/app.jar"]