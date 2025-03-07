FROM amazoncorretto:17.0.7-alpine
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]