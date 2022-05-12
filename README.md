# location-service

Location service provides a REST API mainly for purposes of searching localities across Slovak Republic based on GPS coordinates, with possibility of extending
searched area by other countries in the future.

### SwaggerUI

SwaggerUI is accessible on following url:

```
http://{host}:{port}/swagger-ui.html
```

Possibility to **_enable / disable_** swagger endpoints by specifying following property with **_true/false_** value.

```
springdoc.api-docs.enabled=true
```

### PostgresSQL

Create user and database specified bellow:

```
database: location_service
user: ls_api
password: pwd
schema: ls
```

```postgresql
CREATE USER "ls_api" WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    REPLICATION;
ALTER USER "ls_api" PASSWORD 'pwd';

CREATE DATABASE "location_service"
    WITH
    OWNER = "ls_api"
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
CREATE SCHEMA IF NOT EXISTS "ls" AUTHORIZATION "ls_api";
```


#### PostGIS extension

PostGIS is a spatial database extender for PostgreSQL object-relational database. It adds support for geographic objects allowing location queries to be run in
SQL.

Version: 3.1.4

#### osm2pgsql tool

Osm2pgsql imports OpenStreetMap data into a PostgreSQL/PostGIS database.

Version: 1.5.1
https://osm2pgsql.org/doc/install.html

After DB creation, database schema will be initialized with tables by FLYWAY migration tool
using predefined scripts located in [resources/db/migration/](src/main/resources/db/migration)


### Configuration properties

#### HttpRequestResponseLoggingFilter

Request and response custom logging filter with payload included.

Filter must be configured by following properties:

* **enabled** -> possibility to **_turn ON/OFF_** logging filter by setting this property to **_true/false_** value.
* **max-payload-length** -> possibility to **_set max length_** of response payload included in each log record.

Example:

```
location-service.http-logging-filter.enabled=true
location-service.http-logging-filter.max-payload-length=15000
```

#### Data updating

Process of updating data consist of downloading OSM data file from specified URL, importing the file into PostgreSQL/PostGIS database using osm2pgsql tool and
finally, updating data in location table.

Data update process must be configured by following properties:

Example:

```
#update properties
#startup-update-executor properties
location-service.update.timeout=PT3H
location-service.update.startup-update.enabled=true
location-service.update.startup-update.force-update-enabled=false
#scheduled-update-executor properties
location-service.update.scheduled-update.enabled=true
location-service.update.scheduled-update.cron=0 30 01 1 * *
#retry-update-executor properties
location-service.update.retry-task.enabled=true
location-service.update.retry-task.max-retries=3
#manual-update properties
location-service.update.manual-update.enabled=true
#data update - osm2pgsql properties
location-service.update.osm2pgsql.run-cmd=osm2pgsql
location-service.update.osm2pgsql.style-file-path=/usr/share/osm2pgsql/default.style
#data update - file downloader properties
location-service.update.file-downloader.download-url=https://download.geofabrik.de/europe/slovakia-latest.osm.pbf
location-service.update.file-downloader.dest-file-base-path=osm_data
```
