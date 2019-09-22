package com.breno.projects.stockhandling.statistics.domain;

import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents all data related to update events over a Product
 * @author breno
 *
 */
@RequiredArgsConstructor
@Builder
@Getter
public class ProductStatistics {
	private final String productId;
	private final List<StockEvent> stockEvents;
	
	/**
	 * A method responsible for updating the list of events
	 * of a stock.
	 * 
	 * @param stockEvent
	 */
	public void addStockEvent(StockEvent stockEvent) {
		Objects.requireNonNull(stockEvent);

		this.stockEvents.add(stockEvent);
	}
}
