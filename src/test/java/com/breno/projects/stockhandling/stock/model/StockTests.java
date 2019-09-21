package com.breno.projects.stockhandling.stock.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.breno.projects.stockhandling.stock.model.exception.FutureStockTimestampException;
import com.breno.projects.stockhandling.stock.model.exception.InvalidStockQuantityException;

class StockTests {
	
	@Test
	public void shouldUpdateStockWithValidTimestampAndQuantity() {
		Instant now = Instant.now();
		Stock stock = createAStock(now);
		
		stock.updateStock(3, now);
		
		assertThat(stock.getTimestamp()).isEqualTo(now);
	}
	
	
	@Test
	public void shouldThrowInvalidStockQuantityExceptionWhenUpdateStockWithInNegativeQuantity() {
		Instant now = Instant.now();
		Stock stock = createAStock(now);
		
		assertThrows(InvalidStockQuantityException.class, () -> stock.updateStock(-1, now));
	}
	
	@Test
	public void shouldThrowFutureStockTimestampExceptionWhenUpdateStockWithFutureTimestamp() {
		Instant now = Instant.now();
		Stock stock = createAStock(now);
		
		assertThrows(FutureStockTimestampException.class, () -> stock.updateStock(0, now.plusMillis(100)));
	}
	
	@Test
	public void shouldThrowNullPointerExceptionWhenUpdateStockWithQuantityNull() {
		Instant now = Instant.now();
		Stock stock = createAStock(now);
		
		assertThrows(
				NullPointerException.class,
				() -> stock.updateStock(null, now),
				"quantity cannot be null");
	}
	
	@Test
	public void shouldThrowNullPointerExceptionWhenUpdateStockWithTimestampNull() {
		Instant now = Instant.now();
		Stock stock = createAStock(now);
		
		assertThrows(
				NullPointerException.class,
				() -> stock.updateStock(4, null),
				"timestamp cannot be null");
	}


	private Stock createAStock(Instant now) {
		Stock stock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId("Px")
				.timestamp(now.minus(2, ChronoUnit.DAYS))
				.quantity(2)
				.build();
		return stock;
	}
	
}
