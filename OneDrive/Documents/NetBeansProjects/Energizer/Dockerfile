# Use an official Tomcat image
FROM tomcat:10.1

# Set working directory inside the container
WORKDIR /usr/local/tomcat/webapps/

# Copy your compiled WAR file to Tomcat webapps directory
COPY dist/Energizer.war ROOT.war

# Expose port 8080 for web traffic
EXPOSE 8443

# Start Tomcat
CMD ["catalina.sh", "run"]
