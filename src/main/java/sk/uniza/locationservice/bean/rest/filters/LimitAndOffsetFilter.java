package sk.uniza.locationservice.bean.rest.filters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitAndOffsetFilter {

	private static final Long DEFAULT_LIMIT = 10L;
	private static final Long DEFAULT_OFFSET = 0L;

	@Nullable
	@Parameter(example = "10", schema = @Schema(implementation = Long.class, example = "10", defaultValue = "10"),
			description = "Limit returned records by specifying number of items to skip before returning the results.")
	private Long limit = DEFAULT_LIMIT;

	@Nullable
	@Parameter(example = "0", schema = @Schema(implementation = Long.class, example = "0", defaultValue = "0"),
			description = "Offset returned records by specified number of records to skip before returning the results.")
	private Long offset = DEFAULT_OFFSET;

}
