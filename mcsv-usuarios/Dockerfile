FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY ./target/mcsv-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
ENTRYPOINT ["java","-jar","mcsv-usuarios-0.0.1-SNAPSHOT.jar"]