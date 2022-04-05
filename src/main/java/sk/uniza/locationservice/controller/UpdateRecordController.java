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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import sk.uniza.locationservice.business.service.UpdateRecordService;
import sk.uniza.locationservice.business.updaterunner.ManualUpdateExecutor;
import sk.uniza.locationservice.controller.bean.queryfilters.UpdateRecordsFilter;
import sk.uniza.locationservice.controller.bean.request.ManualUpdateRequest;
import sk.uniza.locationservice.controller.bean.response.GetUpdateRecordsResponse;
import sk.uniza.locationservice.controller.bean.response.UpdateRecordResponse;
import sk.uniza.locationservice.controller.error.ErrorResponse;
import sk.uniza.locationservice.controller.openapi.examples.ErrorExamples;
import sk.uniza.locationservice.controller.openapi.examples.OpenApiExamples;

import static sk.uniza.locationservice.controller.openapi.examples.ErrorExamples.HTTP_400_DESCRIPTION;

@Slf4j
@RestController
@RequestMapping("/api/v1/update")
@RequiredArgsConstructor
@Tag(name = UpdateRecordController.API_TAG, description = UpdateRecordController.API_DESCRIPTION)
@Validated
public class UpdateRecordController {

	public static final String API_TAG = "UpdateRecordController";
	public static final String API_DESCRIPTION = "Update Record Controller providing operations with update resources. Mainly, GET operations for listing previous updates, and POST operation for trigger MANUAL UPDATE of location records.";

	private final UpdateRecordService updateRecordService;
	private final ManualUpdateExecutor manualUpdateExecutor;

	@GetMapping
	@Operation(summary = "1101 - Get update records",
			description = "Returns list of history update records. Possibility to filter results by specified query parameters."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = GetUpdateRecordsResponse.class),
							examples = {
									@ExampleObject(name = "GetUpdateRecordsResponse",
											description = "Updates found by search criteria",
											value = OpenApiExamples.UC_GET_UPDATES),
									@ExampleObject(name = "GetUpdateRecordsResponse - not found",
											description = "No update records by search criteria was found",
											value = OpenApiExamples.UC_GET_UPDATES_NOT_FOUND),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.UC_GET_UPDATES_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getUpdateRecords(@Valid @ParameterObject UpdateRecordsFilter filter) {
		GetUpdateRecordsResponse response = updateRecordService.getUpdateRecords(filter);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{updateId}")
	@Operation(summary = "1102 - Get update record by id",
			description = "Returns a single update record for specified unique identifier - updateId, response extended by update step details.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = UpdateRecordResponse.class),
							examples = {
									@ExampleObject(name = "UpdateRecordResponse",
											description = "Update by specified id",
											value = OpenApiExamples.UC_GET_UPDATE_BY_ID),
									@ExampleObject(name = "UpdateRecordResponse - not found",
											description = "No update found for specified id",
											value = OpenApiExamples.UC_GET_UPDATE_BY_ID_NOT_FOUND),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.UC_GET_UPDATE_BY_ID_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getUpdateRecordById(
			@Parameter(required = true, description = "Unique updateId identifier.", example = "\"11256\"")
			@NotNull @PathVariable(value = "updateId") @Positive Long updateId) {
		UpdateRecordResponse response = updateRecordService.getUpdateRecordById(updateId);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/latest")
	@Operation(
			summary = "1103 - Get latest update",
			description = "Returns latest update record"
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = UpdateRecordResponse.class),
							examples = {
									@ExampleObject(name = "UpdateRecordResponse",
											description = "Latest available update",
											value = OpenApiExamples.UC_GET_UPDATE_BY_ID),
									@ExampleObject(name = "UpdateRecordResponse - not found",
											description = "No latest update was found",
											value = OpenApiExamples.UC_GET_UPDATE_BY_ID_NOT_FOUND),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
							}
					)
			),
	})
	public ResponseEntity<?> getLatestUpdateRecord() {
		UpdateRecordResponse response = updateRecordService.getLatestUpdateRecord();
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	@Operation(summary = "1104 -  Endpoint that triggers manual update",
			description = "Performs a manual update of location data. This operation arranges download of the OSM data file from specified server, import raw OSM data to DB with osm2pgsql and prepare new version of location data."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = UpdateRecordResponse.class),
							examples = {
									@ExampleObject(name = "Update record, response of processing manual update.",
											value = OpenApiExamples.UPDATE_RECORD_EXAMPLE),
							})),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.UC_MANUAL_UPDATE_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							schema = @Schema(
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
									@ExampleObject(name = ErrorExamples.LS0101_ERROR_CODE,
											value = ErrorExamples.UC_MANUAL_UPDATE_500),
							}
					)
			),
	})
	@ConditionalOnProperty(value = "location-service.update.manual-update-executor.enabled", havingValue = "true")
	public ResponseEntity<?> manualUpdate(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = false,
					description = "Optional request body that defines additional info to update.",
					content = @Content(
							schema = @Schema(
									implementation = ManualUpdateRequest.class),
							examples = {
									@ExampleObject(name = "Manual update request with default settings.",
											value = OpenApiExamples.UC_MANUAL_UPDATES_REQUEST_DEFAULT),
									@ExampleObject(name = "Manual update request with specified custom url.",
											value = OpenApiExamples.UC_MANUAL_UPDATES_REQUEST_WITH_URL_SPECIFIED),
							}))
			@RequestBody(required = false) @Valid ManualUpdateRequest request) {
		UpdateRecordResponse response = manualUpdateExecutor.triggerUpdate(request);
		return ResponseEntity.ok().body(response);
	}

}
