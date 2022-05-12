package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitAndOffsetFilter {

	private static final Long DEFAULT_LIMIT = 10L;
	private static final Long DEFAULT_OFFSET = 0L;

	@Parameter(example = "10", schema = @Schema(implementation = Long.class, example = "10", defaultValue = "10"),
			description = "Limit returned records by specifying number of items to skip before returning the results.")
	@Min(1)
	@Nullable
	private Long limit = DEFAULT_LIMIT;

	@Parameter(example = "0", schema = @Schema(implementation = Long.class, example = "0", defaultValue = "0"),
			description = "Offset returned records by specified number of records to skip before returning the results.")
	@Min(0)
	@Nullable
	private Long offset = DEFAULT_OFFSET;

}
