FROM openjdk:11
EXPOSE 8080
ADD target/ev-charger-app-server.jar ev-charger-app-server.jar
ENTRYPOINT ["java","-jar","ev-charger-app-server.jar"]