
FROM alpine

RUN apk add  openjdk11


EXPOSE 8089

ADD target/tp-foyer-1.0.0.jar tp-foyer-1.0.0.jar  # Ensure this matches your JAR file
ENTRYPOINT ["java", "-jar", "/tp-foyer-1.0.0.jar"] 

