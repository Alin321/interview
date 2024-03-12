FROM maven:3.9.6-eclipse-temurin-21 AS build

COPY src src
COPY pom.xml .
RUN mvn clean package

FROM openjdk:21

COPY --from=build /target/interview-*.jar /opt/interview.jar
CMD ["java","--enable-preview","-jar","/opt/interview.jar"]