package sk.uniza.locationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.uniza.locationservice.bean.ExampleResponse;
import sk.uniza.locationservice.service.ExampleService;

@Slf4j
@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
public class ExampleController {

	private final ExampleService exampleService;

	@GetMapping("/{name}")
	public ResponseEntity<?> getExample(@PathVariable(name = "name") String name) {
		ExampleResponse response = exampleService.getExample(name);
		return ResponseEntity.ok().body(response);
	}
}
