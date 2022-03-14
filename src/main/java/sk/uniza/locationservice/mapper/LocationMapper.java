package sk.uniza.locationservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

import sk.uniza.locationservice.controller.bean.response.LocationResponse;
import sk.uniza.locationservice.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	LocationResponse map(@Nullable Location source);

	List<LocationResponse> map(@Nullable List<Location> source);

}
