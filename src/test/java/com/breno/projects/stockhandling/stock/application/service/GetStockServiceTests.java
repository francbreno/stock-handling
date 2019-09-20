package com.breno.projects.stockhandling.stock.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.breno.projects.stockhandling.stock.application.port.out.LoadStockPort;
import com.breno.projects.stockhandling.stock.model.Stock;

class GetStockServiceTests {

	@Mock
	LoadStockPort loadStockPort;
	
	@InjectMocks
	GetStockService getStockService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void shoudThrowEntityNotFoundIfTheresNoStockWithProvidedProductId() {
		String productId  = "product-ZZZ";
		
		when(loadStockPort.loadStock(productId)).thenThrow(EntityNotFoundException.class);
		
		assertThrows(EntityNotFoundException.class, () -> getStockService.getStock(productId));
	}
	
	@Test
	void shouldReturnAStockWhenProductIdIsValid() {
		String productId  = "product-01";

		Stock expectedStock = Stock.builder()
				.id(1L)
				.productId(productId)
				.stockId("stock-01")
				.timestamp(Instant.now())
				.quantity(2)
				.version(4)
				.build();
		
		when(loadStockPort.loadStock(productId)).thenReturn(expectedStock);
		
		Stock stock = getStockService.getStock(productId);
		
		assertThat(stock).isEqualToComparingFieldByField(expectedStock);
	}
	
	@Test
	void shouldThrowExceptionWhenProductIdIsNull() {
		checkExceptionForProductIdValue(null);
	}

	@Test
	void shouldThrowExceptionWhenProductIdIsEmpty() {
		checkExceptionForProductIdValue("");
	}

	private void checkExceptionForProductIdValue(String productId) {
		assertThrows(RuntimeException.class, () -> getStockService.getStock(""));
	}
}
