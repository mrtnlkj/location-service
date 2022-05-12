FROM openjdk:8

RUN mkdir /app
RUN mkdir  /app/osm_data

WORKDIR /app

RUN apt-get update
RUN apt-get install osm2pgsql -y

ADD ./target/location-service-*.jar /app/location-service.jar

EXPOSE 8080

CMD ["sh", "-c", "java -jar location-service.jar"]
