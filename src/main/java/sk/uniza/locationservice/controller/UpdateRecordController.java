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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import sk.uniza.locationservice.bean.rest.OverviewResponse;
import sk.uniza.locationservice.bean.RunUpdateRequest;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateRecordsFilter;
import sk.uniza.locationservice.common.openapi.examples.OpenApiExamples;
import sk.uniza.locationservice.service.ManualUpdateTriggerService;
import sk.uniza.locationservice.service.UpdateRecordService;

@Slf4j
@RestController
@RequestMapping("/api/v1/update-records")
@RequiredArgsConstructor
@Tag(name = UpdateRecordController.API_TAG, description = UpdateRecordController.API_DESCRIPTION)
public class UpdateRecordController {

	public static final String API_TAG = "UpdateRecordController";
	public static final String API_DESCRIPTION = "The main purpose of update record controller is manually trigger the update, " +
			"check latest update progress and also possibility to list history records of previous updates.";

	private final UpdateRecordService updateRecordService;
	private final ManualUpdateTriggerService manualUpdateTriggerService;

	@PostMapping("/run-update")
	@Operation(
			summary = "Triggers manual location data update.",
			description = "Perform a manual update of location data. This process contains of downloading OSM data file from server, importing raw OSM data to DB using osm2pgql and importing modified version of location data to custom database."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Success, manual update was triggered successfully and location data there will be available new version of location data in few minutes.",
					content = @Content(schema = @Schema(implementation = UpdateRecord.class), examples = {
							@ExampleObject(name = "Update record, response of processing manual update.", value = OpenApiExamples.UPDATE_RECORD_EXAMPLE),
					}))
	})
	public ResponseEntity<?> triggerManualUpdate(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = false,
					description = "Optional request body that defines additional info to update process.",
					content = @Content(schema = @Schema(implementation = RunUpdateRequest.class), examples = {
							@ExampleObject(name = "Run update request with default settings.", value = OpenApiExamples.RUN_UPDATE_REQUEST_EXAMPLE),
							@ExampleObject(name = "Run update request with specified custom url.", value = OpenApiExamples.RUN_UPDATE_REQUEST_WITH_URL_EXAMPLE),
					}))
			@RequestBody(required = false) RunUpdateRequest request) {
		UpdateRecord response = manualUpdateTriggerService.triggerUpdate(request);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/latest")
	@Operation(
			summary = "Returns latest data update record.",
			description = "Returns latest update record location data. Prop"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Success.",
					content = @Content(schema = @Schema(implementation = UpdateRecord.class), examples = {
							@ExampleObject(name = "Update record.", value = OpenApiExamples.UPDATE_RECORD_EXAMPLE),
					}))
	})
	public ResponseEntity<?> getLatestUpdateRecord() {
		UpdateRecord response = updateRecordService.getLatestUpdateRecord();
		return ResponseEntity.ok().body(response);
	}

	@GetMapping()
	@Operation(
			summary = "Returns list of history update records.",
			description = "Returns list of history update records. Possibility to filter results by specified query parameters."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Success.",
					content = @Content(schema = @Schema(implementation = List.class), examples = {
							@ExampleObject(name = "Update records overview.", value = OpenApiExamples.UPDATE_RECORDS_EXAMPLE),
					}))
	})
	public ResponseEntity<?> getUpdateRecordsByFilter(@ParameterObject UpdateRecordsFilter filter) {
		OverviewResponse<UpdateRecord> response = updateRecordService.getUpdateRecordsByFilter(filter);
		return ResponseEntity.ok().body(response);
	}
}
