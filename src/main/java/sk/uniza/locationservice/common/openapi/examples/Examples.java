package sk.uniza.locationservice.common.openapi.examples;

public class Examples {

	public static final String EXAMPLE_RESPONSE = "{\"data\":\"data\"}";

	public static final String UPDATE_RECORD_EXAMPLE = "{\n" +
			"  \"updateId\": 1,\n" +
			"  \"startedTime\": \"2022-01-01T12:00:00.000Z\",\n" +
			"  \"finishedTime\": null,\n" +
			"  \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"status\": \"RUNNING\",\n" +
			"  \"trigger\": \"MANUAL_UPDATE\",\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"";

	public static final String UPDATE_RECORDS_EXAMPLE = "[\n" +
			"\n" +
			"{\n" +
			"  \"updateId\": 1,\n" +
			"  \"startedTime\": \"2022-01-01T12:00:00.000Z\",\n" +
			"  \"finishedTime\": \"2022-01-01T15:00:00.000Z\",\n" +
			"  \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"status\": \"FAILED\",\n" +
			"  \"trigger\": \"SCHEDULED_UPDATE\",\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"},\n" +
			"{\n" +
			"  \"updateId\": 2,\n" +
			"  \"startedTime\": \"2022-01-15T12:00:00.000Z\",\n" +
			"  \"finishedTime\": \"2022-01-15T15:00:00.000Z\",\n" +
			"  \"dataDownloadUrl\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"status\": \"FINISHED\",\n" +
			"  \"trigger\": \"SCHEDULED_RETRY_UPDATE\",\n" +
			"  \"description\": null\n" +
			"}\n" +
			"\n" +
			"]";

	public static final String RUN_UPDATE_REQUEST_EXAMPLE = "{\n" +
			"  \"url\": null,\n" +
			"  \"description\": \"Trying to run manual update of locations data. Default data updater setting will be applied.\"\n" +
			"}";

	public static final String RUN_UPDATE_REQUEST_WITH_URL_EXAMPLE = "{\n" +
			"  \"url\": \"https://download.geofabrik.de/europe/slovakia-latest.osm.pbf\",\n" +
			"  \"description\": \"Trying to run manual data update of locations data with specified custom URL for OSM data file download.\"\n" +
			"}";
}
