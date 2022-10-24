FROM bellsoft/liberica-openjdk-alpine:latest
COPY ./target/*.jar java-monitor-service
EXPOSE 8089
ENTRYPOINT ["java","-jar", "java-monitor-service"]