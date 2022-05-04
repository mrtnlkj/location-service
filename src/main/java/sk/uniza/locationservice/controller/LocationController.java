package sk.uniza.locationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.controller.bean.queryfilters.CoordinatesFilter;
import sk.uniza.locationservice.controller.bean.queryfilters.LimitAndOffsetFilter;
import sk.uniza.locationservice.controller.bean.queryfilters.LocationsFilter;
import sk.uniza.locationservice.controller.bean.response.GetLocationsResponse;
import sk.uniza.locationservice.controller.bean.response.LocationResponse;
import sk.uniza.locationservice.controller.bean.response.SuccessResponse;
import sk.uniza.locationservice.controller.error.ErrorResponse;
import sk.uniza.locationservice.controller.openapi.examples.ErrorExamples;
import sk.uniza.locationservice.controller.openapi.examples.OpenApiExamples;
import sk.uniza.locationservice.controller.openapi.examples.SchemaType;

import static sk.uniza.locationservice.controller.openapi.examples.ErrorExamples.HTTP_400_DESCRIPTION;

@RestController
@RequestMapping(value = "/api/v1/locations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = LocationController.API_TAG, description = LocationController.API_DESCRIPTION)
@Validated
public class LocationController {

	public static final String API_TAG = "LocationController";
	public static final String API_DESCRIPTION = "The main purpose of Location Controller is to provide operations with location resources. " +
			"It exposes API endpoints mainly used for searching locations by many various ways.";

	private final LocationService locationService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "1001 - Get locations",
			description = "Returns a location response including a list of the locations and their count " +
					"with the possibility to filter results by refining search criteria with query parameters. Locations are sorted by field **nameSk** in ascending order.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = GetLocationsResponse.class),
							examples = {
									@ExampleObject(name = "GetLocationsResponse",
											description = "Locations found by search criteria",
											value = OpenApiExamples.LC_GET_LOCATIONS),
									@ExampleObject(name = "GetLocationsResponse - not found",
											description = "No locations by search criteria was found",
											value = OpenApiExamples.LC_GET_LOCATIONS_NOT_FOUND),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.LC_GET_LOCATIONS_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getLocations(@Valid @ParameterObject LocationsFilter filter) {
		GetLocationsResponse response = locationService.getLocationsOverviewByFilter(filter);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{locationId}")
	@Operation(summary = "1002 - Get location by id",
			description = "Returns a single location for specified unique identifier - locationId, response extended by GeoJson boundary string.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = LocationResponse.class),
							examples = {
									@ExampleObject(name = "LocationResponse",
											description = "Location by specified id",
											value = OpenApiExamples.LC_GET_LOCATION_BY_ID),
									@ExampleObject(name = "LocationResponse with embedded GeoJson",
											description = "Location by specified id with embedded GeoJson",
											value = OpenApiExamples.LC_GET_LOCATION_BY_ID_EMBED_GEO_JSON),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.LC_GET_LOCATION_BY_ID_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getLocationById(
			@Parameter(required = true, description = "Unique locationId identifier.", example = "\"11256\"")
			@NotNull @PathVariable(value = "locationId") @Positive Long locationId,
			@Parameter(description = "If set to **true**, response will include a GeoJson with location boundary.", example = "false")
			@Nullable Boolean embedGeoJson) {
		LocationResponse response = locationService.getLocationById(locationId, embedGeoJson);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{locationId}/gps-coords-occurrence")
	@Operation(summary = "1003 - GPS coordinates occurrence",
			description = "Endpoint verifies whether the given GPS coordinates occurs in specified location and returns appropriate response.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = Boolean.class),
							examples = {
									@ExampleObject(name = "Example response - occurrence found",
											description = "GPS coordinates occur in given location",
											value = OpenApiExamples.LC_GPS_OCCURRENCE),
									@ExampleObject(name = "Example response - occurrence not found",
											description = "GPS coordinates does not occur in given location",
											value = OpenApiExamples.LC_GPS_OCCURRENCE_NOT_FOUND),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.LC_GPS_OCCURRENCE_400)
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> gpsCoordsOccurrenceWithinLocation(
			@Parameter(required = true, description = "Unique locationId identifier.", example = "11256")
			@NotNull @PathVariable(value = "locationId") @Positive Long locationId,
			@Valid @ParameterObject CoordinatesFilter filter) {
		SuccessResponse response = locationService.gpsCoordsOccurrenceWithinLocation(locationId, filter);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/within-distance")
	@Operation(summary = "1004 - Get locations within specified distance from given GPS coordinates.",
			description = "Returns a location response including a list of the locations and their count by search criteria. Locations are sorted by distance between location and given GPS coordinates in ascending order.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = GetLocationsResponse.class),
							examples = {
									@ExampleObject(name = "GetLocationsResponse",
											description = "Locations found by search criteria",
											value = OpenApiExamples.LC_GET_LOCATIONS_WITHIN_DISTANCE),
									@ExampleObject(name = "GetLocationsResponse - not found",
											description = "No locations by search criteria was found",
											value = OpenApiExamples.LC_GET_LOCATIONS_WITHIN_DISTANCE_NOT_FOUND),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.LC_GET_LOCATIONS_WITHIN_DISTANCE_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getLocationsWithinSpecifiedDistance(@Parameter(required = true, description = "Distance [m]", example = "100.00")
																 @NotNull @Min(0) @RequestParam BigDecimal distance,
																 @Valid @ParameterObject CoordinatesFilter coordsFilter,
																 @Valid @ParameterObject LimitAndOffsetFilter limitAndOffsetFilter,
																 @Parameter(
																		 description = "If set to **true**, response will include a GeoJson with location boundary.",
																		 example = "false")
																 @Nullable Boolean embedGeoJson) {
		GetLocationsResponse response = locationService.getLocationsWithinSpecifiedDistance(distance, coordsFilter, limitAndOffsetFilter, embedGeoJson);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/nearest-by-gps-coords")
	@Operation(summary = "1005 - Get nearest location by GPS coordinates",
			description = "Returns a single location that is the nearest to the specified GPS coordinates.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = LocationResponse.class),
							examples = {
									@ExampleObject(name = "LocationResponse",
											description = "Location nearest to the specified GPS coordinates",
											value = OpenApiExamples.LC_GET_NEAREST_LOCATION),
									@ExampleObject(name = "LocationResponse with embedded GeoJson",
											description = "Location nearest to the specified GPS coordinates including embedded GeoJson",
											value = OpenApiExamples.LC_GET_NEAREST_LOCATION_EMBED_GEO_JSON),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.LC_GET_NEAREST_LOCATION_400)
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(
									type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getNearestLocation(@Valid @ParameterObject CoordinatesFilter filter,
												@Parameter(description = "If set to **true**, response will include a GeoJson with location boundary.",
														example = "false")
												@Nullable Boolean embedGeoJson) {
		LocationResponse response = locationService.getNearestLocationByGpsCoords(filter, embedGeoJson);
		return ResponseEntity.ok().body(response);
	}

}