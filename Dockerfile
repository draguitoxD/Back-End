FROM amazoncorretto:11-alpine-jdk
MAINTAINER nazarenoviero
COPY target/nv-0.0.1-SNAPSHOT.jar nv-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/nv-0.0.1-SNAPSHOT.jar"]