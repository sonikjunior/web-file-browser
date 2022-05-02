# build stage
FROM maven:3.8.5-openjdk-11-slim as build
RUN mkdir -p workspace
WORKDIR workspace
COPY . /workspace
RUN mvn -Dmaven.test.skip package

# run stage
FROM eclipse-temurin:11.0.15_10-jre-alpine
COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
