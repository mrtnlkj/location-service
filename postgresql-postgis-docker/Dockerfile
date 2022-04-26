FROM postgres
RUN apt-get update \
    && apt-get install postgis postgresql-14-postgis-3 -y

CMD ["/usr/local/bin/docker-entrypoint.sh","postgres"]

EXPOSE 5455