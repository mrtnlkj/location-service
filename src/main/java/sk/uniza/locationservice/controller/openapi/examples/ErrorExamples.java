package sk.uniza.locationservice.controller.openapi.examples;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("java:S1192")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorExamples {

	public static final String LS0001_ERROR_CODE = "LS0001";
	public static final String LS0004_ERROR_CODE = "LS0004";
	public static final String LS0101_ERROR_CODE = "LS0101";
	public static final String LS0003_ERROR_CODE = "LS0103";
	public static final String HTTP_400 = "400";
	public static final String HTTP_500 = "500";

	public static final String HTTP_400_DESCRIPTION = "Bad request.\n " +
			"List of possible error codes: " +
			"<ul>" +
			"<li>LS0001 - Request is not valid</li>" +
			"</ul>";
	public static final String HTTP_500_DESCRIPTION = "Internal server error.\n " +
			"List of possible error codes:" +
			"<ul>" +
			"<li>LS0004 - PostgreSQL JDBC connection failure</li>" +
			"</ul>";
	public static final String UC_MANUAL_UPDATE_HTTP_500_DESCRIPTION = "Internal server error.\n " +
			"List of possible error codes:" +
			"<ul>" +
			"<li>LS0101 - Update is already in progress</li>" +
			"<li>LS0004 - PostgreSQL JDBC connection failure</li>" +
			"</ul>";
	public static final String UC_ABORT_UPDATE_HTTP_500_DESCRIPTION = "Internal server error.\n " +
			"List of possible error codes:" +
			"<ul>" +
			"<li>LS0103 - Requested update is not IN PROGRESS, unable to abort</li>" +
			"<li>LS0004 - PostgreSQL JDBC connection failure</li>" +
			"</ul>";
	public static final String HTTP_500_EXAMPLE = "{\n" +
			"  \"errorMessage\": {\n" +
			"    \"referenceId\": \"efee2335-7e32-4be8-bd3b-65f2d06b6970\",\n" +
			"    \"message\": \"PostgreSQL JDBC connection failure. (cause: Failed to obtain JDBC Connection; nested exception is org.postgresql.util.PSQLException: FATAL: database \\\"xyz\\\" does not exist)\"\n" +
			"  },\n" +
			"  \"businessExceptions\": [\n" +
			"    {\n" +
			"      \"code\": \"LS0004\",\n" +
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

	public static final String LC_GET_LOCATION_BY_ID_400 = "{\n" +
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
	public static final String UC_ABORT_400 = LC_GET_LOCATION_BY_ID_400;
	public static final String LC_GET_LOCATIONS_WITHIN_DISTANCE_400 = LC_GET_NEAREST_LOCATION_400;
	public static final String LC_GPS_OCCURRENCE_400 = LC_GET_NEAREST_LOCATION_400;

	public static final String UC_GET_UPDATES_400 = "{\n" +
			"    \"errorMessage\": {\n" +
			"        \"referenceId\": \"70b2e0fc-c656-4d6d-9e3e-0a85f43c3b91\",\n" +
			"        \"message\": \"Constraint violation (cause: org.springframework.validation.BeanPropertyBindingResult: 1 errors\\nField error in object 'updatesFilter' on field 'limit': rejected value [-5]; codes [Min.updatesFilter.limit,Min.limit,Min.java.lang.Long,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [updatesFilter.limit,limit]; arguments []; default message [limit],1]; default message [must be greater than or equal to 1])\"\n" +
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

	public static final String UC_GET_UPDATE_BY_ID_400 = LC_GET_LOCATION_BY_ID_400;
	public static final String UC_MANUAL_UPDATE_400 = "{\n" +
			"  \"errorMessage\": {\n" +
			"    \"referenceId\": \"fc3510e1-f8b1-4131-ba78-b02178e83b40\",\n" +
			"    \"message\": \"Constraint violation (cause: Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> sk.uniza.locationservice.controller.UpdateController.manualUpdate(sk.uniza.locationservice.controller.bean.request.ManualUpdateRequest): [Field error in object 'manualUpdateRequest' on field 'url': rejected value [httpsinvalid]; codes [URL.manualUpdateRequest.url,URL.url,URL.java.lang.String,URL]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [manualUpdateRequest.url,url]; arguments []; default message [url],[Ljavax.validation.constraints.Pattern$Flag;@602a3c41,,-1,,.*]; default message [must be a valid URL]] )\"\n" +
			"  },\n" +
			"  \"businessExceptions\": [\n" +
			"    {\n" +
			"      \"code\": \"LS0001\",\n" +
			"      \"message\": \"Constraint violation\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"fieldValidations\": [\n" +
			"    {\n" +
			"      \"field\": \"url\",\n" +
			"      \"message\": \"must be a valid URL\"\n" +
			"    }\n" +
			"  ]\n" +
			"}";
	public static final String UC_MANUAL_UPDATE_500 = "{\n" +
			"  \"errorMessage\": {\n" +
			"    \"referenceId\": \"10b0c42b-58db-47e6-8f34-742881779ed9\",\n" +
			"    \"message\": \"Update is already in progress.\"\n" +
			"  },\n" +
			"  \"businessExceptions\": [\n" +
			"    {\n" +
			"      \"code\": \"LS0101\",\n" +
			"      \"message\": \"Update is already in progress.\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"fieldValidations\": []\n" +
			"}";
	public static final String UC_ABORT_UPDATE_500 = "{\n" +
			"  \"errorMessage\": {\n" +
			"    \"referenceId\": \"0e6f390c-7f6c-48b8-aab0-8a653d6b5920\",\n" +
			"    \"message\": \"Requested update is not IN PROGRESS, unable to abort.\"\n" +
			"  },\n" +
			"  \"businessExceptions\": [\n" +
			"    {\n" +
			"      \"code\": \"LS0103\",\n" +
			"      \"message\": \"Requested update is not IN PROGRESS, unable to abort.\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"fieldValidations\": []\n" +
			"}";

}