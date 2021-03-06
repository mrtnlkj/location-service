package sk.uniza.locationservice.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationUtils {

	private static final String DURATION_PRETTY_PRINT_PATTERN = "HH:mm:ss.SSS";

	public static String secondsToPrettyPrintFormat(long millis) {
		return DurationFormatUtils.formatDuration(millis, DURATION_PRETTY_PRINT_PATTERN, true);
	}

	public static String prettyPrintDurationBetween(Instant start, Instant end) {
		return secondsToPrettyPrintFormat(Duration.between(start, end).toMillis());
	}

	public static boolean gte(Duration duration, Duration duration2) {
		return duration.compareTo(duration2) >= 0;
	}
}
