FROM eclipse-temurin:17-jre-alpine
COPY *.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]