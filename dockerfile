FROM eclipse-temurin:latest
LABEL author=mikail
EXPOSE 8080
COPY ./target/relex-test-task-0.0.1-SNAPSHOT.jar /opt/app/
ENTRYPOINT ["java", "-jar", "/opt/app/relex-test-task-0.0.1-SNAPSHOT.jar"]