package sk.uniza.locationservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

import sk.uniza.locationservice.controller.bean.response.UpdateResponse;
import sk.uniza.locationservice.repository.entity.UpdateEntity;

@Mapper(componentModel = "spring")
public interface UpdateMapper {

	UpdateResponse map(@Nullable UpdateEntity source);

	List<UpdateResponse> map(@Nullable List<UpdateEntity> source);

}
