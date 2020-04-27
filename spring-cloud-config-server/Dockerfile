FROM openjdk:8 
ADD target/cloud-config.jar cloud-config.jar 
EXPOSE 8765 
ENTRYPOINT ["java", "-jar", "cloud-config.jar"]