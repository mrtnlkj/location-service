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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.uniza.locationservice.bean.ExampleResponse;
import sk.uniza.locationservice.common.openapi.examples.ErrorExamples;
import sk.uniza.locationservice.common.openapi.examples.OpenApiExamples;
import sk.uniza.locationservice.service.ExampleService;

@Slf4j
@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
@Tag(name = ExampleController.API_TAG, description = ExampleController.API_DESCRIPTION)
public class ExampleController {

	public final static String API_TAG = "ExampleController";
	public final static String API_DESCRIPTION = "Description of example controller.";

	private final ExampleService exampleService;

	@GetMapping("/{name}")
	@Operation(
			summary = "Summary.",
			description = "Description."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success.",
					content = @Content(schema = @Schema(implementation = ExampleResponse.class), examples = {
							@ExampleObject(name = "ExampleResponse", value = OpenApiExamples.EXAMPLE_RESPONSE),
					})),
			@ApiResponse(responseCode = "500", description = "Internal Server Error.",
					content = @Content(schema = @Schema(implementation = String.class), examples = {
							@ExampleObject(name = "Error", value = ErrorExamples.ERROR, description = "Unexpected error occurred."),
					}))
	})
	public ResponseEntity<?> getExample(@PathVariable(name = "name") String name) {
		ExampleResponse response = exampleService.getExample(name);
		return ResponseEntity.ok().body(response);
	}
}
