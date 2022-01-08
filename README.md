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

After that, database schema will be initialized with tables by FLYWAY migration tool
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