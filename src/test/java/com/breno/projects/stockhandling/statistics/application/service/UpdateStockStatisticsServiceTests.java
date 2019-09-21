package com.breno.projects.stockhandling.statistics.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.breno.projects.stockhandling.statistics.application.port.StockStatisticsData;
import com.breno.projects.stockhandling.statistics.application.port.StockStatisticsData.StockStatisticsDataBuilder;
import com.breno.projects.stockhandling.statistics.application.port.out.UpdateStockStatisticsStatePort;

class UpdateStockStatisticsServiceTests {
	
	@Mock
	UpdateStockStatisticsStatePort updateStockStatisticsStatePort;
	
	@InjectMocks
	UpdateStockStatisticsService updateStockStatisticsService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void shouldUpdateStockStatisticsStateWhenStockStatisticsDataIsValid() {
		StockStatisticsData data = createPartialStockStatisticsData().build();

		updateStockStatisticsService.update(data);
		
		Mockito.verify(updateStockStatisticsStatePort).update(Mockito.any(StockStatisticsData.class));
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasNoProductId() {
		StockStatisticsData data = 
				createPartialStockStatisticsData()
					.productId(null)
					.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(data),
				"stockId cannot be null");
	}
	
	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasNoStockId() {
		StockStatisticsData data = 
				createPartialStockStatisticsData()
					.stockId(null)
					.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(data),
				"productId cannot be null");
	}
	
	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasNoTimestamp() {
		StockStatisticsData data = 
				createPartialStockStatisticsData()
					.timestamp(null)
					.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(data),
				"timestamp cannot be null");
	}
	
	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasTimestampInFuture() {
		StockStatisticsData data = 
				createPartialStockStatisticsData()
					.timestamp(Instant.now().plus(1, ChronoUnit.DAYS))
					.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(data),
				"timestamp cannot be in the future");
	}
	
	private StockStatisticsDataBuilder createPartialStockStatisticsData() {
		return StockStatisticsData.builder()
			.stockId("S1")
			.productId("P1")
			.timestamp(Instant.now())
			.quantity(100);
	}
}
