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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.uniza.locationservice.bean.RunUpdateRequest;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.common.openapi.examples.Examples;
import sk.uniza.locationservice.service.DataUpdateRunner;

@Slf4j
@RestController
@RequestMapping("/api/v1/update-records")
@RequiredArgsConstructor
@Tag(name = UpdateRecordController.API_TAG, description = UpdateRecordController.API_DESCRIPTION)
public class UpdateRecordController {

	public static final String API_TAG = "UpdateRecordController";
	public static final String API_DESCRIPTION = "The main purpose of update record controller is manually trigger the update, " +
			"check latest triggered update for a status and possibility to list history records of previous updates.";

	private final DataUpdateRunner dataUpdateRunner;

	@PostMapping("/run-update")
	@Operation(
			summary = "Triggers manual location data update.",
			description = "Perform a manual update of location data. This process contains of downloading OSM data file from server, importing raw OSM data to DB using osm2pgql and importing modified version of location data to custom database."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Success, manual update was triggered successfully and location data there will be available new version of location data in few minutes.",
					content = @Content(schema = @Schema(implementation = UpdateRecord.class), examples = {
							@ExampleObject(name = "Update record, response of processing manual update.", value = Examples.UPDATE_RECORD_EXAMPLE),
					}))
	})
	public ResponseEntity<?> triggerManualUpdate(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = false,
					description = "Optional request body that defines additional info to update process.",
					content = @Content(schema = @Schema(implementation = RunUpdateRequest.class), examples = {
							@ExampleObject(name = "Run update request with default settings.", value = Examples.RUN_UPDATE_REQUEST_EXAMPLE),
							@ExampleObject(name = "Run update request with specified custom url.", value = Examples.RUN_UPDATE_REQUEST_WITH_URL_EXAMPLE),
					}))
			@RequestBody(required = false) RunUpdateRequest request) {
		UpdateRecord response = dataUpdateRunner.manualUpdate(request);
		return ResponseEntity.ok().body(response);
	}
}
