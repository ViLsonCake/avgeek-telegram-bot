FROM gradle:jdk19 as build

WORKDIR /app
COPY . /app
RUN gradle --refresh-dependencies clean build -x test

FROM openjdk:19-jdk

COPY --from=build /app/build/libs/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]