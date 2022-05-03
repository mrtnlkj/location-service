package sk.uniza.locationservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * OpenApiCreator
 * Test class used ONLY for generating openapi.yaml swagger file. (ROOT/openapi/openapi.yaml)
 *
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "springdoc.paths-to-match=/api/**")
class OpenApiCreatorTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getOpenApiYaml() throws Exception {
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs"));

		MockHttpServletResponse response = action.andReturn().getResponse();
		String jsonString = response.getContentAsString();

		BufferedWriter writer = new BufferedWriter(new FileWriter("openapi/openapi.json"));
		writer.write(jsonString);
		writer.close();

		JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
		String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);

		writer = new BufferedWriter(new FileWriter("openapi/openapi.yaml"));
		writer.write(jsonAsYaml);
		writer.close();

		assertThat(Boolean.TRUE).isTrue();
	}
}