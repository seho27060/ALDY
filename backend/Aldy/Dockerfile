FROM adoptopenjdk/openjdk11
EXPOSE 3000
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} spring-server.jar
ENTRYPOINT ["java", "-jar", "itda-server.jar"]
