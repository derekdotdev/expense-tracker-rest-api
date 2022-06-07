FROM openjdk:11

COPY target/expensetrackerrestapi.jar expensetrackerrestapi.jar

ENTRYPOINT ["java", "-jar", "/expensetrackerrestapi.jar"]