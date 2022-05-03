package sk.uniza.locationservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HelperRepository {

	private final JdbcTemplate jdbcTemplate;

	public void cleanupPublicSchemaTables() {
		final String query = "DROP TABLE public.osm_point, public.osm_polygon, public.osm_line, public.osm_roads cascade; ";
		jdbcTemplate.execute(query);
	}

	public Long importLocationDataWithVersionAndGetInsertedRecordsCount(Long locationVersionId) {
		final String query = "CALL insert_location_data_proc(" + locationVersionId + ", 0); ";
		return jdbcTemplate.queryForObject(query, Long.class);
	}

	public void processStateNames(Long locationId) {
		final String query = "CALL process_state_names_proc(" + locationId + ", 0); ";
		jdbcTemplate.execute(query);
	}

	public void processRegionNames(Long locationId) {
		final String query = "CALL process_region_names_proc(" + locationId + ", 0); ";
		jdbcTemplate.execute(query);
	}

	public void processDistrictNames(Long locationId) {
		final String query = "CALL process_district_names_proc(" + locationId + ", 0); ";
		jdbcTemplate.execute(query);
	}
}
