package sk.uniza.locationservice.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import sk.uniza.locationservice.bean.enums.LocationType;

@ReadingConverter
public class StringToLocationTypeConverter implements Converter<String, LocationType> {

	@Override
	public LocationType convert(@Nullable String source) {
		return LocationType.fromString(source);
	}
}