package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import sk.uniza.locationservice.business.importer.Osm2pgsqlImporter;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

@Component
@RequiredArgsConstructor
public class Osm2pgsqlImportTask implements UpdateTaskExecutable {

	private final Osm2pgsqlImporter importer;

	@Override
	public UpdateWrapper execute(UpdateWrapper request) throws IOException, InterruptedException {
		File file = request.getOsmFile();
		importer.importFile(file);
		return request;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.OSM2PGSQL_IMPORT;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.OSM2PGSQL_IMPORT.getOrder();
	}
}
