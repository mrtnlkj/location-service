package sk.uniza.locationservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.lang.Nullable;

import java.util.List;

import sk.uniza.locationservice.controller.bean.response.UpdateRecordResponse;
import sk.uniza.locationservice.repository.entity.UpdateRecordEntity;

@Mapper(componentModel = "spring")
public interface UpdateRecordMapper {

	UpdateRecordResponse map(@Nullable UpdateRecordEntity source);

	List<UpdateRecordResponse> map(@Nullable List<UpdateRecordEntity> source);

}
