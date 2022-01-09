package sk.uniza.locationservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import static java.util.Objects.isNull;
import static sk.uniza.locationservice.util.DurationUtils.durationBetween;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileDownloaderUtils {

	//1s = 1000ms
	private static final int CONNECTION_TIMEOUT_MILLIS = 1000 * 60 * 5;
	private static final int READ_TIMEOUT_MILLIS = 1000 * 60 * 5;

	public static File download(URL url, String downloadedFilePath) throws IOException {
		log.info("File download STARTED. Download URL: \"{}\", Downloaded file path: \"{}\".", url, downloadedFilePath);
		Instant start = Instant.now();
		File file = new File(downloadedFilePath);
		FileUtils.copyURLToFile(url, file, CONNECTION_TIMEOUT_MILLIS, READ_TIMEOUT_MILLIS);
		Instant end = Instant.now();
		long sizeInKilobytes = bytesToKilobytes(file.length());
		log.info("File download FINISHED. Total time elapsed: {}s with file size: {}kb.", durationBetween(start, end), sizeInKilobytes);
		return file;
	}

	public static String getFileNameFromDownloadURL(URL url) {
		if (isNull(url)) {
			return StringUtils.EMPTY;
		}
		String urlString = url.toString();
		final int lastIndexOfSlash = urlString.lastIndexOf('/');
		final int length = urlString.length();
		if (length - 1 == lastIndexOfSlash) {
			return StringUtils.EMPTY;
		}
		return urlString.substring(lastIndexOfSlash + 1).split("\\?")[0].split("#")[0];
	}

	public static long bytesToKilobytes(long bytes) {
		return bytes / 1024;
	}
}
