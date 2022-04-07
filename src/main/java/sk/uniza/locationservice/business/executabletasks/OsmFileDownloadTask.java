package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class OsmFileDownloadTask implements UpdateTaskExecutable {

	private final UpdateProperties updateProperties;

	@Override
	public UpdateWrapper execute(UpdateWrapper request) throws IOException {
		URL url = request.getUrl();
		url = getCorrectUrl(url);
		String downloadPath = buildPathForDownloadedFile(url);
		File file = download(url, downloadPath);
		request.setOsmFile(file);
		return request;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.OSM_FILE_DOWNLOAD;
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
		return UpdateProcessingTaskCode.OSM_FILE_DOWNLOAD.getOrder();
	}
}
