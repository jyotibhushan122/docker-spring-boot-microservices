FROM openjdk:8 
ADD target/spring-cloud-config-server.jar spring-cloud-config-server.jar 
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "spring-cloud-config-server.jar"]