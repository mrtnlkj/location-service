package sk.uniza.locationservice.controller.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.net.URL;

import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateWrapperRequest extends RunUpdateRequest {

	private UpdateTrigger trigger;

	public abstract static class UpdateWrapperRequestBuilder<C extends UpdateWrapperRequest, B extends UpdateWrapperRequestBuilder<C, B>>
			extends RunUpdateRequestBuilder<C, B> {

		public B fromRunUpdateRequest(RunUpdateRequest request, URL defaultUrl) {
			return this.url(defaultIfNull(request.getUrl(), defaultUrl))
					   .description(request.getDescription());
		}
	}

}
