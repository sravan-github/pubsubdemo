FROM openjdk:11-jdk-oraclelinux8
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
