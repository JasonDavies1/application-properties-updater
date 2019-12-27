FROM openjdk:11-jre-slim
#java:8u111-jdk-alpine size: 49.28MB
ADD target/application-properties-updater.jar /opt/application-properties-updater/application-properties-updater.jar
WORKDIR /opt/application-properties-updater
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar application-properties-updater.jar"]