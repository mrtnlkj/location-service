package sk.uniza.locationservice.business.executabletasks;

import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

public interface ProcessingTask {

	UpdateWrapper execute(UpdateWrapper wrapper) throws Exception;

	UpdateProcessingTaskCode getUpdateTaskCode();

	int getOrder();

}
