package sk.uniza.locationservice.controller.bean.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ListResponse<T> {

	@ArraySchema(arraySchema = @Schema(description = "List of all available requested records."))
	List<T> records;
	@Schema(description = "Total count of available requested records.", example = "543", implementation = Long.class)
	Long recordsCount;
}
