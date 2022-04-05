package sk.uniza.locationservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
@Slf4j
public class StringToMapSerializer extends JsonSerializer<String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeObject(objectMapper.readValue(s, new TypeReference<Map<String, Object>>() {}));
	}
}
