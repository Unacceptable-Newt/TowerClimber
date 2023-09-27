FROM gradle:8.3.0-jdk17-alpine as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/TowerCrawler-1.0.jar
COPY --from=build /home/gradle/src/src/cache /app/src/cache
COPY --from=build /home/gradle/src/src/data /app/data

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/TowerCrawler-1.0.jar", "-terminal"]