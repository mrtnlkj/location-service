package sk.uniza.locationservice.controller.bean.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data
public class SuccessResponse extends CustomResponse<Boolean> {

}
