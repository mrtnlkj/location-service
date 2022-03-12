package sk.uniza.locationservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

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
				return null;
			}
		}
	}
}