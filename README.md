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

DataUpdater with DataUpdateExecutor manages updates of location data. Process of updating data consist of downloading OSM data file from specified URL, importing the file into
PostgreSQL/PostGIS database using osm2pgsql tool and finally, updating data in location table.

Data updater must be configured by following properties:

* **scheduled-cron-update** -> cron expression that defines scheduled update time
* **update-at-startup-enabled** -> possibility to **_turn ON/OFF_** data update by setting this property to **_true/false_** value. Data update basically updates location data at every startup of application, only if data ARE NOT already PRESENT in database from previous update runs
* **force-update-at-startup-enabled** -> possibility to **_turn ON/OFF_** force data update by setting this property to **_true/false_** value. Force data updates basically updates location data at every startup of application, even if data ARE already PRESENT in database from previous update runs


* **osm2pgsql.base-path** -> base path of osm2pgsql tool located on server
* **osm2pgsql.exe-file-name** -> name of executable file of om2pgsql cmd tool
* **osm2pgsql.style-file-name** -> file name of style that defines advanced import properties during import by osm2pgsql tool


* **file-downloader.download-url** -> url for download OSM data file
* **file-downloader.dest-file-base-path** -> base path for downloaded file located on server

Example:

```
location-service.data-updater.scheduled-cron-update=0 30 03 * * *
location-service.data-updater.force-update-after-start-enabled=false

location-service.data-updater.osm2pgsql.base-path=C:/osm2pgsql/osm2pgsql-bin
location-service.data-updater.osm2pgsql.exe-file-name=osm2pgsql.exe
location-service.data-updater.osm2pgsql.style-file-name=default.style

location-service.data-updater.file-downloader.download-url=https://download.geofabrik.de/europe/slovakia-latest.osm.pbf
location-service.data-updater.file-downloader.dest-file-base-path=C:/data/location_service/osm_data
```
