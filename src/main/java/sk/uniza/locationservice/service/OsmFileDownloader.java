package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import sk.uniza.locationservice.config.DataUpdaterProperties;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static sk.uniza.locationservice.util.FileDownloaderUtils.download;
import static sk.uniza.locationservice.util.FileDownloaderUtils.getFileNameFromDownloadURL;

@Component
@RequiredArgsConstructor
public class OsmFileDownloader {

	private final DataUpdaterProperties dataUpoDataUpdaterProperties;

	public File downloadOsmFile(URL url) throws IOException {
		url = getCorrectUrl(url);
		String downloadPath = buildPathForDownloadedFile(url);
		return download(url, downloadPath);
	}

	private String buildPathForDownloadedFile(URL url) {
		return dataUpoDataUpdaterProperties.getFileDownloader()
										   .getDestFileBasePath()
										   .concat(File.separator)
										   .concat(getFileNameFromDownloadURL(url));
	}

	private URL getCorrectUrl(URL url) {
		return defaultIfNull(url, dataUpoDataUpdaterProperties.getFileDownloader().getDownloadUrl());
	}
}
