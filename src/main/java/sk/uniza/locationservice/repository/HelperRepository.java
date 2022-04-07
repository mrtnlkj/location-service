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
}
