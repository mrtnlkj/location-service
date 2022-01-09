package sk.uniza.locationservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.net.MalformedURLException;
import java.net.URL;

@ReadingConverter
public class StringToURLConverter implements Converter<String, URL> {

	@Override
	public URL convert(String source) {
		try {
			return new URL(source);
		} catch (MalformedURLException e) {
			return null;
		}
	}
}