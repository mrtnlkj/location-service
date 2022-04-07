package sk.uniza.locationservice.business.executabletasks;

import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

public interface UpdateTaskExecutable {

	UpdateWrapper execute(UpdateWrapper wrapper) throws Exception;

	UpdateProcessingTaskCode getUpdateTaskCode();

	int getOrder();

}
