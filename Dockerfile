FROM openjdk:8
ADD target/docker-spring-boot-project-example.jar docker-spring-boot-project-example.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar" , "docker-spring-boot-project-example.jar"]
