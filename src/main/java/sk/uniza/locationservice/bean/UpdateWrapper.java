package sk.uniza.locationservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.net.URL;

import sk.uniza.locationservice.bean.enums.UpdateTrigger;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateWrapper extends RunUpdateRequest {

	private UpdateTrigger trigger;

	public abstract static class UpdateWrapperBuilder<C extends UpdateWrapper, B extends UpdateWrapperBuilder<C, B>>
			extends RunUpdateRequestBuilder<C, B> {

		public B fromRunUpdateRequest(RunUpdateRequest request, URL defaultUrl) {
			return this.url(defaultIfNull(request.getUrl(), defaultUrl))
					   .description(request.getDescription());
		}
	}

}
