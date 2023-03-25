FROM amazoncorretto:11-alpine-jdk
MAINTAINER nv
COPY target/nv-0.0.1-SNAPSHOT.jar nv-app.jar
ENTRYPOINT ["java","-jar", "/nv-app.jar"]
