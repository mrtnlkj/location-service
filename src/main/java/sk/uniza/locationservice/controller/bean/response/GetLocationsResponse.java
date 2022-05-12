package sk.uniza.locationservice.controller.bean.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class GetLocationsResponse extends ListResponse<LocationResponse> {
}
