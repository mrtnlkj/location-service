package sk.uniza.locationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import sk.uniza.locationservice.bean.Location;
import sk.uniza.locationservice.bean.LocationsFilter;
import sk.uniza.locationservice.bean.OverviewResponse;
import sk.uniza.locationservice.common.openapi.examples.Examples;
import sk.uniza.locationservice.service.LocationService;

@Slf4j
@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
@Tag(name = LocationController.API_TAG, description = LocationController.API_DESCRIPTION)
public class LocationController {

	public static final String API_TAG = "LocationController";
	public static final String API_DESCRIPTION = "The main purposes of location controller are locations. " +
			"Location controller exposing endpoints used to search location by many various parameters and many various ways.";
	private final LocationService locationService;

	@GetMapping()
	@Operation(
			summary = "Returns list of locations.",
			description = "Returns list of location records. Possibility to filter results by specified query parameters."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Success.",
					content = @Content(schema = @Schema(implementation = List.class), examples = {
							@ExampleObject(name = "Locations.", value = Examples.LOCATIONS_OVERVIEW_EXAMPLE),
					}))
	})
	public ResponseEntity<?> getLocationsOverviewByFilter(@ParameterObject LocationsFilter filter) {
		OverviewResponse<Location> response = locationService.getLocationsOverviewByFilter(filter);
		return ResponseEntity.ok().body(response);
	}
}
