FROM adoptopenjdk/openjdk11
EXPOSE 3000
ARG JAR_FILE=build/libs/Aldy-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} spring-server.jar
ENTRYPOINT ["java", "-jar", "spring-server.jar"]
