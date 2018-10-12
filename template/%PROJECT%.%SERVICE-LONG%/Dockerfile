# FEDICT DOCKERFILE PROJECT
# -------------------------
# This Dockerfile creates an image containing a Spring Boot application
#
# HOW TO BUILD THIS IMAGE
# -----------------------
# Put all downloaded files in the same directory as this Dockerfile
# Run:
#      $ docker build -t bosa/%PROJECT%-%SERVICE-LONG% --build-arg JAR_FILE=target/%SERVICE-LONG%-0.0.1-SNAPSHOT.jar .
#
# HOW TO RUN THIS IMAGE
# -----------------------
# Run:
#      $ docker run -e SPRING_PROFILES_ACTIVE=default -p 7080:8080 --name %PROJECT%-%SERVICE-LONG% bosa/%PROJECT%-%SERVICE-LONG%
#

# Pull base image
# ---------------
FROM  openjdk:8-jre-alpine

# Additional packages
RUN apk add --update bash && rm -rf /var/cache/apk/*
RUN apk add --update openssh

# Setup port to expose
EXPOSE 8080

# Environment
# -------------------------------
ENV APP_NAME=spring-boot-app.jar
#
ENV SPRING_PROFILES_ACTIVE=docker

# Build time arguments
# -------------------------------
# Get the BUILD ARG :  JAR_FILE
ARG JAR_FILE

# Configuration
# -------------------------------
# Create a user and group used to launch processes
# The user ID 1000 is the default for the first "regular" user on Alpine,
# so there is a high chance that this ID will be equal to the current user
# making it easier to use volumes (no permission issues)
RUN mkdir -p /opt/bosa && \
    mkdir -p /opt/bosa/logs && \
    addgroup bosa -g 1000 && \
    adduser -D -u 1000 -G bosa -h /opt/bosa -s /sbin/nologin bosa && \
    chmod 755 /opt/bosa

# Add "JAR_FILE" to the image
COPY ${JAR_FILE} /opt/bosa/${APP_NAME}

RUN sh -c 'touch /opt/bosa/$APP_NAME' && \
    sh -c 'chown -R bosa:bosa /opt/bosa' && \
    sh -c 'chmod -R 755 /opt/bosa'

# Set the working directory to fedict' user home directory
WORKDIR /opt/bosa

# create log file directory and set read-write properties
RUN mkdir -p -m 777 /opt/bosa/logs && \
    chown -Rf 1001:0 /opt/bosa/logs && \
    chmod -R ug+rw /opt/bosa/logs && \
    find /opt/bosa/logs -type d -exec chmod ug+x {} \;

# Specify the user which should be used to execute all commands below
USER bosa

# Configuration
# -------------------------------

ENV JAVA_OPTS=""
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/bosa/$APP_NAME"]
