package sk.uniza.locationservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import sk.uniza.locationservice.bean.enums.UpdateTrigger;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateWrapper extends RunUpdateRequest {

	private UpdateTrigger trigger;

}
