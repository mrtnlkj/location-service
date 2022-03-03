package sk.uniza.locationservice.bean.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverviewResponse<T> {

	List<T> records;
	Long recordsCount;
}
