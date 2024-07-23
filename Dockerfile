# define base docker image
FROM openjdk:21
LABEL authors="tradovac"
EXPOSE 8080
ADD target/healthcheck-monitoring-app-0.0.1-SNAPSHOT.jar healthcheck-monitoring-app.jar
ENTRYPOINT ["java", "-jar", "healthcheck-monitoring-app.jar"]