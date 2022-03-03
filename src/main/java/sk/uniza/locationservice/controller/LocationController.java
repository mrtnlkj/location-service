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
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.uniza.locationservice.bean.Location;
import sk.uniza.locationservice.bean.rest.LocationOverviewResponse;
import sk.uniza.locationservice.bean.rest.filters.LocationsFilter;
import sk.uniza.locationservice.common.openapi.examples.OpenApiExamples;
import sk.uniza.locationservice.service.LocationService;

@Slf4j
@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Tag(name = LocationController.API_TAG, description = LocationController.API_DESCRIPTION)
public class LocationController {

	public static final String API_TAG = "LocationController";
	public static final String API_DESCRIPTION = "The main purpose of location controller is providing operations with location resources. " +
			"It exposes endpoints used for searching locations by many various ways.";
	private final LocationService locationService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(
			summary = "Get location overview response.",
			description = "Returns a location overview response with list of location objects and location objects count, " +
					"with possibility to filter results by refining search criteria with query parameters.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200_RESPONSE_CODE,
					description = "Success response.",
					content = @Content(schema = @Schema(implementation = LocationOverviewResponse.class),
							examples = {
									@ExampleObject(name = "Example Location Overview Response.",
											description = "Location overview response by refining search criteria is returned.",
											value = OpenApiExamples.LocationControllerExamples.LOCATIONS_OVERVIEW),
									@ExampleObject(name = "Example Location Overview Response with no locations found.",
											description = "No locations by refining search criteria was found. Location overview response with empty locations list is returned",
											value = OpenApiExamples.LocationControllerExamples.LOCATIONS_OVERVIEW_NOT_FOUND),
							}
					)
			),
	})
	public ResponseEntity<?> getLocationsOverviewByFilter(@ParameterObject LocationsFilter filter) {
		LocationOverviewResponse response = locationService.getLocationsOverviewByFilter(filter);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{locationId}")
	@Operation(
			summary = "Get location by specified unique ID - locationId.",
			description = "Returns a single location for provided unique ID - locationId, extended by GeoJson boundary string."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200_RESPONSE_CODE,
					description = "Success response.",
					content = @Content(schema = @Schema(implementation = Location.class),
							examples = {
									@ExampleObject(name = "Example Location.",
											description = "Location by specified locationId is returned.",
											value = OpenApiExamples.LocationControllerExamples.LOCATION_BY_ID),
									@ExampleObject(name = "Example Location for non-existing locationId.",
											description = "Location by specified locationId is not found. Endpoint returned empty response body.",
											value = OpenApiExamples.EMPTY_RESPONSE_BODY),
							}))
	})
	public ResponseEntity<?> getLocationById(
			@Parameter(required = true, description = "Unique ID identifier of the location.", example = "\"969\"") @PathVariable Long locationId) {
		Location response = locationService.getLocationById(locationId);
		return ResponseEntity.ok().body(response);
	}
}
