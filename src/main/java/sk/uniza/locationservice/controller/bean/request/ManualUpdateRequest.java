package sk.uniza.locationservice.controller.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.net.URL;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ManualUpdateRequest {

	private URL url;
	private String filePath;
	private String description;

}
