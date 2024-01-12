FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
COPY ../target/OrdersDelayNotificationSystem-v1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar" ]
