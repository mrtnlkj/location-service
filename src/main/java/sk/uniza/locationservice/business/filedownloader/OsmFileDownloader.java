package sk.uniza.locationservice.business.filedownloader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import sk.uniza.locationservice.config.properties.UpdateProperties;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static sk.uniza.locationservice.business.filedownloader.FileDownloader.download;
import static sk.uniza.locationservice.business.filedownloader.FileDownloader.getFileNameFromDownloadURL;

@Component
@RequiredArgsConstructor
public class OsmFileDownloader {

	private final UpdateProperties updateProperties;

	public File downloadOsmFile(URL url) throws IOException {
		url = getCorrectUrl(url);
		String downloadPath = buildPathForDownloadedFile(url);
		return download(url, downloadPath);
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
}
