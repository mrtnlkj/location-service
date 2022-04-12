package sk.uniza.locationservice.controller.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.net.URL;

import sk.uniza.locationservice.controller.bean.enums.UpdateType;
import sk.uniza.locationservice.repository.entity.LocationVersionEntity;
import sk.uniza.locationservice.repository.entity.UpdateEntity;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateWrapper extends ManualUpdateRequest {

	private UpdateType type;
	private File osmFile;
	private UpdateEntity update;
	private LocationVersionEntity locationVersion;

	public abstract static class UpdateWrapperBuilder<C extends UpdateWrapper, B extends UpdateWrapperBuilder<C, B>>
			extends ManualUpdateRequestBuilder<C, B> {

		public B fromRunUpdateRequest(ManualUpdateRequest request, URL defaultUrl) {
			return this.url(defaultIfNull(request.getUrl(), defaultUrl))
					   .description(request.getDescription())
					   .skipDownload(request.isSkipDownload())
					   .filePath(request.getFilePath());
		}
	}

}
