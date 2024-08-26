FROM gradle:jdk19-alpine as build

WORKDIR /app
COPY . /app
RUN gradle --refresh-dependencies clean build -x test

FROM openjdk:19-jdk-alpine3.16

COPY --from=build /app/build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]