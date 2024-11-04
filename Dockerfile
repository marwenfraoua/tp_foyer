# Use the official Alpine image as a base
FROM alpine

# Install OpenJDK 11
RUN apk add --no-cache openjdk11

# Copy your JAR file into the image
COPY app.jar /app.jar

# Expose port 80
EXPOSE 80

# Specify the command to run your application
CMD ["java", "-jar", "/app.jar"]
