package sk.uniza.locationservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.controller.bean.queryfilters.CoordinatesFilter;
import sk.uniza.locationservice.controller.bean.response.LocationResponse;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

	private static final String CONTROLLER_PATH = "/api/v1/locations";
	private static final String NEAREST_BY_GPS_COORDS_PATH = CONTROLLER_PATH + "/nearest-by-gps-coords";
	private static final Long ID = 100L;

	private static final BigDecimal LAT = new BigDecimal("49.2234674");
	private static final BigDecimal LON = new BigDecimal("18.7393139");
	private final LocationResponse expectedLocationResponse = LocationResponse.builder().locationId(ID).build();

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private LocationService locationService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void getNearestLocation_givenCorrectParams_shouldReturn200HttpCode() throws Exception {
		final CoordinatesFilter filter = CoordinatesFilter.builder()
														  .lat(LAT)
														  .lon(LON)
														  .build();

		mockMvc.perform(get(NEAREST_BY_GPS_COORDS_PATH)
								.queryParam("lat", LAT.toString())
								.queryParam("lon", LON.toString()))
			   .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(content().json(mapper.writeValueAsString(expectedLocationResponse)));

		verify(locationService).getNearestLocationByGpsCoords(filter, false);
	}

	@Test
	void getNearestLocation_missingParams_shouldReturn400HttpCode() throws Exception {

		mockMvc.perform(get(NEAREST_BY_GPS_COORDS_PATH))
			   .andDo(print())
			   .andExpect(status().isBadRequest())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			   .andExpect(ErrorResponseContentMatcher.containsErrorType(ErrorType.CONSTRAINT_VIOLATION));
	}
}
