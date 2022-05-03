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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import sk.uniza.locationservice.business.service.UpdateService;
import sk.uniza.locationservice.controller.bean.queryfilters.UpdateRecordsFilter;
import sk.uniza.locationservice.controller.bean.request.ManualUpdateRequest;
import sk.uniza.locationservice.controller.bean.response.GetUpdateRecordsResponse;
import sk.uniza.locationservice.controller.bean.response.SuccessResponse;
import sk.uniza.locationservice.controller.bean.response.UpdateResponse;
import sk.uniza.locationservice.controller.error.ErrorResponse;
import sk.uniza.locationservice.controller.openapi.examples.ErrorExamples;
import sk.uniza.locationservice.controller.openapi.examples.OpenApiExamples;
import sk.uniza.locationservice.controller.openapi.examples.SchemaType;

import static sk.uniza.locationservice.controller.openapi.examples.ErrorExamples.HTTP_400_DESCRIPTION;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/updates", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = UpdateController.API_TAG, description = UpdateController.API_DESCRIPTION)
@Validated
public class UpdateController {

	public static final String API_TAG = "UpdateController";
	public static final String API_DESCRIPTION = "Update Controller provide operations with update resources. Mainly, GET operations for listing previous updates, and POST operation for trigger MANUAL UPDATE of location records or abort currently running update.";

	private final UpdateService updateService;

	@GetMapping
	@Operation(summary = "1101 - Get update records",
			description = "Returns a update response including a list of the update records and their count.  Possibility to filter results by specified query parameters."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
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
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.UC_GET_UPDATES_400),
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
	public ResponseEntity<?> getUpdateRecords(@Valid @ParameterObject UpdateRecordsFilter filter) {
		GetUpdateRecordsResponse response = updateService.getUpdateRecords(filter);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{updateId}")
	@Operation(summary = "1102 - Get update record by id",
			description = "Returns a single update record for specified unique identifier - updateId, response extended by update step details.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = UpdateResponse.class),
							examples = {
									@ExampleObject(name = "UpdateResponse",
											description = "Update by specified id",
											value = OpenApiExamples.UC_GET_UPDATE_BY_ID),
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
											value = ErrorExamples.UC_GET_UPDATE_BY_ID_400),
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
	public ResponseEntity<?> getUpdateRecordById(
			@Parameter(required = true, description = "Unique updateId identifier.", example = "\"11256\"")
			@NotNull @PathVariable(value = "updateId") @Positive Long updateId) {
		UpdateResponse response = updateService.getUpdateRecordById(updateId);
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
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = UpdateResponse.class),
							examples = {
									@ExampleObject(name = "UpdateResponse",
											description = "Latest available update",
											value = OpenApiExamples.UC_GET_UPDATE_BY_ID),
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
	public ResponseEntity<?> getLatestUpdateRecord() {
		UpdateResponse response = updateService.getLatestUpdateRecord();
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	@Operation(summary = "1104 -  Endpoint that triggers manual update",
			description = "Performs a manual update of location data. This operation arranges download of the OSM data file from specified server, import raw OSM data to DB with osm2pgsql and prepare new version of location data."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_201, description = OpenApiExamples.HTTP_201_DESCRIPTION,
					content = @Content(
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = UpdateResponse.class),
							examples = {
									@ExampleObject(name = "Update record, response of processing manual update.",
											value = OpenApiExamples.UPDATE_RECORD_EXAMPLE),
							})),
			@ApiResponse(responseCode = ErrorExamples.HTTP_400, description = HTTP_400_DESCRIPTION,
					content = @Content(
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0001_ERROR_CODE,
											value = ErrorExamples.UC_MANUAL_UPDATE_400),
							}
					)
			),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							schema = @Schema(type = SchemaType.OBJECT,
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
	@ConditionalOnProperty(value = "location-service.update.manual-update.enabled", havingValue = "true")
	public ResponseEntity<?> manualUpdate(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(required = false,
					description = "Optional request body that defines additional info to update.",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ManualUpdateRequest.class),
							examples = {
									@ExampleObject(name = "Manual update request with default settings.",
											value = OpenApiExamples.UC_MANUAL_UPDATES_REQUEST_DEFAULT),
									@ExampleObject(name = "Manual update request with specified custom url.",
											value = OpenApiExamples.UC_MANUAL_UPDATES_REQUEST_WITH_URL_SPECIFIED),
									@ExampleObject(name = "Manual update request with skipping download.",
											value = OpenApiExamples.UC_MANUAL_UPDATES_REQUEST_WITH_SKIP_DOWNLOAD),
							}))
			@RequestBody(required = false) @Valid ManualUpdateRequest request) {
		UpdateResponse response = updateService.executeManualUpdate(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/{updateId}/abort")
	@Operation(summary = "1104 -  Endpoint that aborts running update",
			description = "Performs an abortion of given running update of location data."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = OpenApiExamples.HTTP_200, description = OpenApiExamples.HTTP_200_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = UpdateResponse.class),
							examples = {
									@ExampleObject(name = "Update aborted.",
											value = OpenApiExamples.UC_ABORT_UPDATE_EXAMPLE),
							})),
			@ApiResponse(responseCode = ErrorExamples.HTTP_500, description = ErrorExamples.HTTP_500_DESCRIPTION,
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(type = SchemaType.OBJECT,
									implementation = ErrorResponse.class),
							examples = {
									@ExampleObject(name = ErrorExamples.LS0004_ERROR_CODE,
											value = ErrorExamples.HTTP_500_EXAMPLE),
									@ExampleObject(name = ErrorExamples.LS0003_ERROR_CODE,
											value = ErrorExamples.UC_ABORT_UPDATE_500)
							}
					)
			),
	})
	@ConditionalOnProperty(value = "location-service.update.manual-update.enabled", havingValue = "true")
	public ResponseEntity<?> abortUpdate(
			@Parameter(required = true, description = "Unique updateId identifier.", example = "11256")
			@NotNull @PathVariable(value = "updateId") @Positive Long updateId) {
		SuccessResponse response = updateService.abortUpdate(updateId);
		return ResponseEntity.ok().body(response);
	}

}
