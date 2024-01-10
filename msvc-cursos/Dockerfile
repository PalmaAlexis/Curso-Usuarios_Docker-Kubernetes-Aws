FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY ./target/mcsv-cursos-0.0.1-SNAPSHOT.jar .
EXPOSE 8002
ENTRYPOINT ["java","-jar","mcsv-cursos-0.0.1-SNAPSHOT.jar"]