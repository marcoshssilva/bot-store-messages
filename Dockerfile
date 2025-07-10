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

USER 1001

ARG JAVA_VM_OPTIONS
ARG MANAGEMENT_PORT
ARG PORT

ENV PATH="/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
ENV JAVA_HOME="/opt/java/openjdk"
ENV JAVA_VM_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80"
ENV PORT="8080"
ENV MANAGEMENT_PORT="8080"

ENTRYPOINT ["bash", "-c"]
CMD ["exec java $JAVA_VM_OPTIONS -jar /app/app.jar"]

HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD sh -c "wget --no-verbose --tries=1 --spider http://localhost:$MANAGEMENT_PORT/actuator/health || exit 1"

EXPOSE 8080