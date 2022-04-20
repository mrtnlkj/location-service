FROM openjdk:8

RUN mkdir /app

WORKDIR /app

RUN apt-get update
RUN apt-get install osm2pgsql -y

RUN mkdir  /app/osm_data

ADD ./target/location-service-*.jar /app/location-service.jar

EXPOSE 8080

CMD ["sh", "-c", "java -jar location-service.jar"]
