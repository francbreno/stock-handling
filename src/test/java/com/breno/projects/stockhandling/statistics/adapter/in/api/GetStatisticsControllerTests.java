package com.breno.projects.stockhandling.statistics.adapter.in.api;

import static com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType.LAST_MONTH;
import static com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType.TODAY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.breno.projects.stockhandling.statistics.application.port.in.TopStatistics;
import com.breno.projects.stockhandling.statistics.application.service.LoadStatisticsService;
import com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GetStatisticsController.class)
class GetStatisticsControllerTests {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private LoadStatisticsService loadStatisticsService;
	
	static List<TopStatistics> statistics;
	
	@BeforeAll
	static void setup() {
		statistics = new ArrayList<>();
	}
	
	@Test
	void shouldReturnProductStatisticsForToday() throws Exception {
		when(loadStatisticsService.loadStatistics(
				Mockito.same(TODAY), Mockito.any(Instant.class)))
			.thenReturn(new TopStatistics(
					new ArrayList<>(),
					new ArrayList<>()));
		
		mockMvc
			.perform(get("/statistics")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.param("time", "today"))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	void shouldReturnProductStatisticsForTheLastMonth() throws Exception {
		when(loadStatisticsService.loadStatistics(
				Mockito.same(LAST_MONTH), Mockito.any(Instant.class)))
			.thenReturn(new TopStatistics(
					new ArrayList<>(),
					new ArrayList<>()));
		
		mockMvc
			.perform(get("/statistics")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.param("time", "lastMonth"))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
	}

}
