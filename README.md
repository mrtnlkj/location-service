# location-service

Location service provides a REST API mainly for purposes of searching localities across Slovak Republic based on GPS coordinates, with possibility of extending
searched area by other countries in the future.

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