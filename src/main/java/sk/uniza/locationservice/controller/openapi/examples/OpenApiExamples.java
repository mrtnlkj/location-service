package sk.uniza.locationservice.controller.openapi.examples;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("java:S1192")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenApiExamples {

	public static final String HTTP_200 = "200 OK";
	public static final String HTTP_200_DESCRIPTION = "Success.";
	public static final String EMPTY_RESPONSE_BODY = "";

	public static final String LC_GET_LOCATIONS = "{}";//TODO
	public static final String LC_GET_LOCATIONS_NOT_FOUND = "{\n" +
			"  \"records\": [],\n" +
			"  \"recordsCount\": 0\n" +
			"}";
	public static final String LC_GET_LOCATION_BY_ID = "{}";//TODO
	public static final String LC_GET_NEAREST_LOCATION = LC_GET_LOCATION_BY_ID;

	public static final String LC_GET_LOCATION_BY_ID_NOT_FOUND = EMPTY_RESPONSE_BODY;

	public static final String LC_GET_NEAREST_LOCATION_NOT_FOUND = EMPTY_RESPONSE_BODY;

	public static final String LC_GET_LOCATIONS_WITHIN_DISTANCE = LC_GET_LOCATIONS;
	public static final String LC_GET_LOCATIONS_WITHIN_DISTANCE_NOT_FOUND = LC_GET_LOCATIONS_NOT_FOUND;

	public static final String LC_GPS_OCCURRENCE = "true";
	public static final String LC_GPS_OCCURRENCE_NOT_FOUND = "false";

	public static final String UPDATE_RECORD_EXAMPLE = "{\n" +
			"  \"updateId\": 1,\n" +
			"  \"startedTime\": \"2022-01-01T12:00:00.000Z\",\n" +
			"  \"finishedTime\": null,\n" +
			"  \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"status\": \"RUNNING\",\n" +
			"  \"trigger\": \"MANUAL_UPDATE\",\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\" " +
			"}\n";
	public static final String UPDATE_RECORDS_EXAMPLE = "{\n" +
			"  \"records\": [\n" +
			"    {\n" +
			"      \"updateId\": 6,\n" +
			"      \"startedTime\": \"2022-01-27T19:11:19.735359Z\",\n" +
			"      \"finishedTime\": \"2022-01-27T19:48:24.306794Z\",\n" +
			"      \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"      \"status\": \"FINISHED\",\n" +
			"      \"trigger\": \"SCHEDULED_RETRY_UPDATE\",\n" +
			"      \"description\": null\n" +
			"    },\n" +
			"    {\n" +
			"      \"updateId\": 1,\n" +
			"      \"startedTime\": \"2022-01-27T16:05:42.357711Z\",\n" +
			"      \"finishedTime\": \"2022-01-27T16:33:06.266130Z\",\n" +
			"      \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"      \"status\": \"FINISHED\",\n" +
			"      \"trigger\": \"MANUAL_UPDATE\",\n" +
			"      \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"recordsCount\": 2\n" +
			"}";
	public static final String RUN_UPDATE_REQUEST_EXAMPLE = "{\n" +
			"  \"url\": null,\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"}";
	public static final String RUN_UPDATE_REQUEST_WITH_URL_EXAMPLE = "{\n" +
			"  \"url\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"description\": \"Trying to run manual data update of locations data with specified custom URL for OSM data file download.\"\n" +
			"}";

}
