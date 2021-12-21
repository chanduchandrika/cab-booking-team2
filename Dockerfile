FROM openjdk:16-alpine3.13
LABEL maintainer="yarramsettichandrika888@gmail.com"
EXPOSE 8080
ADD target/cab-booking.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
