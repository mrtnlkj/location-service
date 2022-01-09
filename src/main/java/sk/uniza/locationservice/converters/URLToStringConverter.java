package sk.uniza.locationservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.net.URL;

@WritingConverter
public class URLToStringConverter implements Converter<URL, String> {

	@Override
	public String convert(URL source) {
		return source.toString();
	}

}