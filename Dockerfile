FROM openjdk:17-jdk

WORKDIR /app

COPY target/players-0.0.1-SNAPSHOT.jar /app/players.jar
COPY player.csv /app/player.csv

EXPOSE 8090

CMD ["java", "-jar", "players.jar"]