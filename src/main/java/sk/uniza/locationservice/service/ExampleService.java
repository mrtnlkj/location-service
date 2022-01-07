package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sk.uniza.locationservice.bean.ExampleResponse;
import sk.uniza.locationservice.repository.ExampleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleService {

	private final ExampleRepository exampleRepository;

	public ExampleResponse getExample(String name) {
		exampleRepository.getExample();
		return ExampleResponse.builder()
							  .data(name)
							  .build();
	}
}
