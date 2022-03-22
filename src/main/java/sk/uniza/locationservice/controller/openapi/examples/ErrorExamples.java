package sk.uniza.locationservice.controller.openapi.examples;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("java:S1192")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorExamples {

	public static final String HTTP_400 = "400 BAD REQUEST";
	public static final String HTTP_500 = "500 INTERNAL SERVER ERROR";

	public static final String HTTP_400_DESCRIPTION = "Bad request.\n " +
			"List of possible error codes: " +
			"<ul>" +
			"<li>LS0001 - Request is not valid</li>" +
			"</ul>";
	public static final String HTTP_500_DESCRIPTION = "Internal server error.\n " +
			"List of possible error codes:" +
			"<ul>" +
			"<li>LS0005 - PostgreSQL JDBC connection failure</li>" +
			"</ul>";
	public static final String HTTP_500_EXAMPLE = "{\n" +
			"  \"errorMessage\": {\n" +
			"    \"referenceId\": \"efee2335-7e32-4be8-bd3b-65f2d06b6970\",\n" +
			"    \"message\": \"PostgreSQL JDBC connection failure. (cause: Failed to obtain JDBC Connection; nested exception is org.postgresql.util.PSQLException: FATAL: database \\\"xyz\\\" does not exist)\"\n" +
			"  },\n" +
			"  \"businessExceptions\": [\n" +
			"    {\n" +
			"      \"code\": \"LS0005\",\n" +
			"      \"message\": \"PostgreSQL JDBC connection failure.\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"fieldValidations\": []\n" +
			"}";

	public static final String LC_GET_LOCATIONS_400 = "{\n" +
			"    \"errorMessage\": {\n" +
			"        \"referenceId\": \"70b2e0fc-c656-4d6d-9e3e-0a85f43c3b91\",\n" +
			"        \"message\": \"Constraint violation (cause: org.springframework.validation.BeanPropertyBindingResult: 1 errors\\nField error in object 'locationsFilter' on field 'limit': rejected value [-5]; codes [Min.locationsFilter.limit,Min.limit,Min.java.lang.Long,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [locationsFilter.limit,limit]; arguments []; default message [limit],1]; default message [must be greater than or equal to 1])\"\n" +
			"    },\n" +
			"    \"businessExceptions\": [\n" +
			"        {\n" +
			"            \"code\": \"LS0001\",\n" +
			"            \"message\": \"Constraint violation\"\n" +
			"        }\n" +
			"    ],\n" +
			"    \"fieldValidations\": [\n" +
			"        {\n" +
			"            \"field\": \"limit\",\n" +
			"            \"message\": \"must be greater than or equal to 1\"\n" +
			"        }\n" +
			"    ]\n" +
			"}";

	public static final String LC_GET_LOCATIONS_BY_ID_400 = "{\n" +
			"    \"errorMessage\": {\n" +
			"        \"referenceId\": \"5dcc4e1e-1ba6-4d83-a429-c1a44711594a\",\n" +
			"        \"message\": \"Constraint violation (cause: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\\"sfasf\\\")\"\n" +
			"    },\n" +
			"    \"businessExceptions\": [\n" +
			"        {\n" +
			"            \"code\": \"LS0001\",\n" +
			"            \"message\": \"Constraint violation\"\n" +
			"        }\n" +
			"    ],\n" +
			"    \"fieldValidations\": [\n" +
			"        {\n" +
			"            \"field\": \"locationId\",\n" +
			"            \"message\": \"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\\"sfasf\\\"\"\n" +
			"        }\n" +
			"    ]\n" +
			"}";

	public static final String LC_GET_NEAREST_LOCATION_400 = "{\n" +
			"    \"errorMessage\": {\n" +
			"        \"referenceId\": \"f22a73a1-412a-470d-a8fe-ca31430e07a0\",\n" +
			"        \"message\": \"Constraint violation (cause: org.springframework.validation.BeanPropertyBindingResult: 1 errors\\nField error in object 'coordinatesFilter' on field 'lat': rejected value [200]; codes [Range.coordinatesFilter.lat,Range.lat,Range.java.math.BigDecimal,Range]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [coordinatesFilter.lat,lat]; arguments []; default message [lat],90,-90]; default message [must be between -90 and 90])\"\n" +
			"    },\n" +
			"    \"businessExceptions\": [\n" +
			"        {\n" +
			"            \"code\": \"LS0001\",\n" +
			"            \"message\": \"Constraint violation\"\n" +
			"        }\n" +
			"    ],\n" +
			"    \"fieldValidations\": [\n" +
			"        {\n" +
			"            \"field\": \"lat\",\n" +
			"            \"message\": \"must be between -90 and 90\"\n" +
			"        }\n" +
			"    ]\n" +
			"}";

	public static final String LC_GET_LOCATIONS_WITHIN_DISTANCE_400 = LC_GET_NEAREST_LOCATION_400;
	public static final String LC_GPS_OCCURRENCE_400 = LC_GET_NEAREST_LOCATION_400;

}