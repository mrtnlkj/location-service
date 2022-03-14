package sk.uniza.locationservice.repository.converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@ReadingConverter
public class StringToURLConverter implements Converter<String, URL> {

	@Override
	public URL convert(@Nullable String source) {
		if (source == null) {
			return null;
		} else {
			try {
				return new URL(source);
			} catch (MalformedURLException e) {
				log.error("Converting String to URL failed! String: {}", source, e);
				return null;
			}
		}
	}
}