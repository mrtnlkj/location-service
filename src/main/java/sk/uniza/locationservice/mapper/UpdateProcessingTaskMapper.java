package sk.uniza.locationservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

import sk.uniza.locationservice.controller.bean.response.UpdateProcessingTaskResponse;
import sk.uniza.locationservice.repository.entity.UpdateProcessingTaskEntity;

@Mapper(componentModel = "spring")
public interface UpdateProcessingTaskMapper {

	UpdateProcessingTaskResponse map(@Nullable UpdateProcessingTaskEntity source);

	List<UpdateProcessingTaskResponse> map(@Nullable List<UpdateProcessingTaskEntity> source);

}
