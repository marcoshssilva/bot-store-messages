FROM app-nx.marcoshssilva.com.br/eclipse-temurin:21-jre-jammy AS base
FROM base AS runner

USER root
WORKDIR /app
COPY target/bot-store-messages-*.jar app.jar
RUN adduser bot-store-messages

USER bot-store-messages

ENV PATH "/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
ENV JAVA_HOME "/opt/java/openjdk"
ENV LC_ALL "en_US.UTF-8"
ENV LANG "en_US.UTF-8"

ENTRYPOINT java $JAVA_OPTS -jar /app/app.jar

EXPOSE 8080
EXPOSE 18080