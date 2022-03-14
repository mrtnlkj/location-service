package sk.uniza.locationservice.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationUtils {

	public static long durationBetween(Instant start, Instant end) {
		return Duration.between(start, end).get(ChronoUnit.SECONDS);
	}
}
