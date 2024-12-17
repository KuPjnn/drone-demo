# STAGE 1. Optimize JDk with Jlink
FROM eclipse-temurin:17-jdk-alpine as jlink-builder

WORKDIR /opt

COPY *.jar /opt/app.jar

# Extract dependencies from the fat JAR
RUN mkdir -p /opt/dependencies && \
    cd /opt/dependencies && \
    jar -xf /opt/app.jar

# Identify required modules
RUN jdeps --print-module-deps --ignore-missing-deps --multi-release 17 \
    --class-path '/opt/dependencies/BOOT-INF/lib/*' \
    /opt/app.jar > /opt/modules.txt

RUN echo $(cat /opt/modules.txt)

RUN $JAVA_HOME/bin/jlink \
    --add-modules $(cat /opt/modules.txt) \
    --compress 2 \
    --strip-debug \
    --no-header-files \
    --no-man-pages \
    --output /opt/jdk

# STAGE 2. Build docker image with JDK Jlink
FROM alpine:latest

COPY --from=jlink-builder /opt/jdk /opt/jdk
COPY *.jar /opt/app.jar

ENV JAVA_HOME=/opt/jdk
ENV PATH="$JAVA_HOME/bin:$PATH"

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/opt/app.jar"]