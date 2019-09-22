package com.breno.projects.stockhandling.statistics.application.service;

import static com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType.TODAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.breno.projects.stockhandling.shared.utils.DateUtil;
import com.breno.projects.stockhandling.statistics.application.port.in.TopStatistics;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadStatisticsPort;
import com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType;
import com.breno.projects.stockhandling.statistics.util.ProductStatisticsTestDataProvider;

class LoadStatisticsServiceTests {
	
	@Mock
	LoadStatisticsPort loadStatisticsPort;
	
	@InjectMocks
	LoadStatisticsService loadStatisticsService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void shouldLoadOnlyStatisticsOfTheDay() {
		StatisticsRangeFilterType filter = TODAY;
		Instant now = Instant.now();
		
		when(loadStatisticsPort.loadStatistics())
			.thenReturn(ProductStatisticsTestDataProvider.createStatistics());
		
		TopStatistics statistics = loadStatisticsService.loadStatistics(filter, now);
		
		assertThat(statistics.getTopAvailableProducts())
			.allMatch(stats ->
				DateUtil.isFromStartOfDayUntilNow(stats.getTimestamp(), now));
	}
}
