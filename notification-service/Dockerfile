FROM bellsoft/liberica-openjdk-alpine:latest
COPY ./target/*.jar notification-service
EXPOSE 8090
ENTRYPOINT ["java","-jar", "notification-service"]