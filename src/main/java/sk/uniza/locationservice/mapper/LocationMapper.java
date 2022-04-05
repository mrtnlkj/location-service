package sk.uniza.locationservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

import sk.uniza.locationservice.controller.bean.response.LocationResponse;
import sk.uniza.locationservice.repository.entity.LocationEntity;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	LocationResponse map(@Nullable LocationEntity source);

	List<LocationResponse> map(@Nullable List<LocationEntity> source);

}
