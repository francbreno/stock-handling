package com.breno.projects.stockhandling.statistics.application.service;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsCommand;
import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsCommand.UpdateStockStatisticsCommandBuilder;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadProductStatisticsPort;
import com.breno.projects.stockhandling.statistics.application.port.out.UpdateStockStatisticsStatePort;
import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;
import com.breno.projects.stockhandling.statistics.domain.StockEvent;

class UpdateStockStatisticsServiceTests {
	
	@Mock
	UpdateStockStatisticsStatePort updateStockStatisticsStatePort;
	
	@Mock
	LoadProductStatisticsPort loadProductStatisticsPort;
	
	@InjectMocks
	UpdateStockStatisticsService updateStockStatisticsService;

	private ProductStatistics data;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		data = createPartialStockStatisticsData();
	}

	@Test
	void shouldUpdateStockStatisticsStateWhenStockStatisticsDataIsValid() {
		StockEvent stockEvent = data.getStockEvents().get(0);
		
		UpdateStockStatisticsCommand command = 
				createPartialUpdateStockStatisticsCommand(data, stockEvent)
				.build();

		updateStockStatisticsService.update(command);
		
		Mockito.verify(updateStockStatisticsStatePort).update(Mockito.any(ProductStatistics.class));
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasNoProductId() {
		StockEvent stockEvent = data.getStockEvents().get(0);
		
		UpdateStockStatisticsCommand command = 
				createPartialUpdateStockStatisticsCommand(data, stockEvent)
				.productId(null)
				.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(command),
				"stockId cannot be null");
	}
	
	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasNoStockId() {
		StockEvent stockEvent = data.getStockEvents().get(0);
		
		UpdateStockStatisticsCommand command = 
				createPartialUpdateStockStatisticsCommand(data, stockEvent)
				.stockId(null)
				.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(command),
				"productId cannot be null");
	}
	
	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasNoTimestamp() {
		StockEvent stockEvent = data.getStockEvents().get(0);
		
		UpdateStockStatisticsCommand command = 
				createPartialUpdateStockStatisticsCommand(data, stockEvent)
				.timestamp(null)
				.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(command),
				"timestamp cannot be null");
	}
	
	@Test
	void shouldThrowIllegalArgumentExceptionWhenStockStatisticsDataHasTimestampInFuture() {
		StockEvent stockEvent = data.getStockEvents().get(0);
		
		UpdateStockStatisticsCommand command = 
				createPartialUpdateStockStatisticsCommand(data, stockEvent)
				.timestamp(Instant.now().plus(1, ChronoUnit.DAYS))
				.build();

		assertThrows(
				IllegalArgumentException.class,
				() -> updateStockStatisticsService.update(command),
				"timestamp cannot be in the future");
	}
	
	private UpdateStockStatisticsCommandBuilder createPartialUpdateStockStatisticsCommand(ProductStatistics data,
			StockEvent stockEvent) {
		return UpdateStockStatisticsCommand.builder()
			.productId(data.getProductId())
			.stockId(stockEvent.getStockId())
			.timestamp(Instant.now())
			.quantity(2);
	}
	
	private ProductStatistics createPartialStockStatisticsData() {
		String productId = "P1";
		String stockId = "S1";
		List<StockEvent> events = IntStream.range(1, 10)
				.mapToObj(j -> {
					int quantity = ThreadLocalRandom.current().nextInt(0, 60);
					Instant timestamp = Instant.now().minus(j, ChronoUnit.DAYS);

					return StockEvent.builder()
							.stockId(stockId)
							.timestamp(timestamp)
							.quantity(quantity)
						.build();
				})
				.collect(toList());
		
		return ProductStatistics.builder()
			.productId("P1")
			.stockEvents(events)
			.build();
	}
}
