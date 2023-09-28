FROM gradle:8.3.0-jdk17-alpine as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:17

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/TowerCrawler-1.0-all.jar /app/TowerCrawler-1.0-all.jar
COPY --from=build /home/gradle/src/src/cache /app/src/cache
COPY --from=build /home/gradle/src/src/data /app/src/data

WORKDIR /app

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/TowerCrawler-1.0-all.jar", "-terminal"]