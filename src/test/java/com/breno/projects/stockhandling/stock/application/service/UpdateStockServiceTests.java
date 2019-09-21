package com.breno.projects.stockhandling.stock.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockCommand;
import com.breno.projects.stockhandling.stock.application.port.out.LoadStockPort;
import com.breno.projects.stockhandling.stock.application.port.out.NotifyStatisticsMediatorPort;
import com.breno.projects.stockhandling.stock.application.port.out.UpdateStockStatePort;
import com.breno.projects.stockhandling.stock.model.Stock;
import com.breno.projects.stockhandling.stock.model.exception.FutureStockTimestampException;
import com.breno.projects.stockhandling.stock.model.exception.InvalidStockQuantityException;

class UpdateStockServiceTests {
	
	@Mock
	LoadStockPort loadStockPort;
	
	@Mock
	UpdateStockStatePort updateStockStatePort;
	
	@Mock
	NotifyStatisticsMediatorPort notifyStatisticsMediatorPort;
	
	@InjectMocks
	UpdateStockService updateStockService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void shouldUpdateStockWithValidData() {
		String productId = "P22";
		
		Stock aStock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId(productId)
				.quantity(10)
				.timestamp(Instant.now())
				.build();

		UpdateStockCommand updateStockCommand =
				UpdateStockCommand.builder()
					.id(aStock.getStockId())
					.productId(aStock.getProductId())
					.timestamp(aStock.getTimestamp())
					.quantity(aStock.getQuantity())
					.build();
		
		when(loadStockPort.loadStock(updateStockCommand.getProductId()))
			.thenReturn(aStock);
		
		updateStockService.updateStock(updateStockCommand);
		
		Mockito.verify(updateStockStatePort).update(Mockito.any(Stock.class));
		Mockito.verify(notifyStatisticsMediatorPort).notify(Mockito.any(Stock.class));
	}

	@Test
	void shouldThrowInvalidStockQuantityExceptionWhenUpdateStockWhenNegativeQuantity() {
		String productId = "P22";
		
		Stock aStock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId(productId)
				.quantity(-1)
				.timestamp(Instant.now())
				.build();

		UpdateStockCommand updateStockCommand =
				UpdateStockCommand.builder()
					.id(aStock.getStockId())
					.productId(aStock.getProductId())
					.timestamp(aStock.getTimestamp())
					.quantity(aStock.getQuantity())
					.build();
		
		when(loadStockPort.loadStock(updateStockCommand.getProductId()))
			.thenReturn(aStock);
		
		assertThrows(InvalidStockQuantityException.class,
				() -> updateStockService.updateStock(updateStockCommand));
	}
	
	@Test
	void shouldThrowFutureStockTimestampExceptionWhenUpdateStockWhenFutureTimestamp() {
		String productId = "P22";
		
		Stock aStock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId(productId)
				.quantity(1)
				.timestamp(Instant.now().plus(1, ChronoUnit.DAYS))
				.build();

		UpdateStockCommand updateStockCommand =
				UpdateStockCommand.builder()
					.id(aStock.getStockId())
					.productId(aStock.getProductId())
					.timestamp(aStock.getTimestamp())
					.quantity(aStock.getQuantity())
					.build();
		
		when(loadStockPort.loadStock(updateStockCommand.getProductId()))
			.thenReturn(aStock);
		
		assertThrows(FutureStockTimestampException.class,
				() -> updateStockService.updateStock(updateStockCommand));
	}
	
	@Test
	void shouldThrowEntityNotFoundExceptionWhenUpdateStockWhenStockDoesntExists() {
		String productId = "P22";
		
		Stock aStock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId(productId)
				.quantity(1)
				.timestamp(Instant.now().plus(1, ChronoUnit.DAYS))
				.build();

		UpdateStockCommand updateStockCommand =
				UpdateStockCommand.builder()
					.id(aStock.getStockId())
					.productId(aStock.getProductId())
					.timestamp(aStock.getTimestamp())
					.quantity(aStock.getQuantity())
					.build();
		
		doThrow(EntityNotFoundException.class)
			.when(loadStockPort).loadStock(updateStockCommand.getProductId());
		
		assertThrows(EntityNotFoundException.class,
				() -> updateStockService.updateStock(updateStockCommand));
	}
}
