package sk.uniza.locationservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.net.MalformedURLException;
import java.net.URL;

import sk.uniza.locationservice.bean.enums.LocationType;

@ReadingConverter
public class StringToLocationTypeConverter implements Converter<String, LocationType> {

	@Override
	public LocationType convert(String source) {
		return LocationType.fromString(source);
	}
}