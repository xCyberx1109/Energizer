# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /Energizer

# Expose Render's required port (10000)
EXPOSE 8080


