
FROM alpine

RUN apk add  openjdk11


EXPOSE 80

# Specify the command to run your application
CMD "java"
