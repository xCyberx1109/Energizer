# Use the official Tomcat base image
FROM tomcat:10.1

# Set working directory inside the container
WORKDIR /usr/local/tomcat/webapps/

# Copy your WAR file to the Tomcat webapps directory
COPY target/your-app.war ROOT.war

# Expose port 8080 (Tomcat default)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
