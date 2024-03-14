FROM openjdk:17
LABEL author=mikail
EXPOSE 8080
COPY ./target/relex-test-task-0.0.1-SNAPSHOT.jar /usr/src/myapp/
ENTRYPOINT ["java", "-jar", "/usr/src/myapp/relex-test-task-0.0.1-SNAPSHOT.jar"]