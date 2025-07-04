FROM maven:3.9.5-eclipse-temurin-21 AS base-builder
FROM base-builder AS build

WORKDIR /build
# install dependencies
COPY pom.xml /build/pom.xml
RUN mvn dependency:go-offline
# copy source code
COPY src /build/src
# generate jar
RUN mvn install -DskipTests

FROM eclipse-temurin:21-jre-jammy AS base-runner
FROM base-runner AS runner

USER root
WORKDIR /app
COPY --from=build /build/target/bot-store-messages-*.jar app.jar
RUN adduser bot-store-messages

USER bot-store-messages

ENV PATH="/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
ENV JAVA_HOME="/opt/java/openjdk"
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80"
ENV LC_ALL="en_US.UTF-8"
ENV LANG="en_US.UTF-8"

ENTRYPOINT java "$JAVA_OPTS" -jar /app/app.jar

EXPOSE 8080
EXPOSE 18080
