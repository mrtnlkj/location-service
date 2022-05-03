package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static sk.uniza.locationservice.business.filedownloader.FileDownloader.download;
import static sk.uniza.locationservice.business.filedownloader.FileDownloader.getFileNameFromDownloadURL;

@Slf4j
@Component
@RequiredArgsConstructor
public class OsmFileLoadTask implements ProcessingTask {

	private final UpdateProperties updateProperties;

	@Override
	public UpdateWrapper execute(UpdateWrapper request) throws IOException {
		File file =  downloadFile(request);
		request.setOsmFile(file);
		return request;
	}

	public File downloadFile(UpdateWrapper request) throws IOException {
		URL url = request.getUrl();
		url = getCorrectUrl(url);
		String downloadPath = buildPathForDownloadedFile(url);
		return download(url, downloadPath);
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.OSM_FILE_LOAD;
	}

	private String buildPathForDownloadedFile(URL url) {
		return updateProperties.getFileDownloader()
							   .getDestFileBasePath()
							   .concat(File.separator)
							   .concat(getFileNameFromDownloadURL(url));
	}

	private URL getCorrectUrl(URL url) {
		return defaultIfNull(url, updateProperties.getFileDownloader().getDownloadUrl());
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.OSM_FILE_LOAD.getOrder();
	}
}
