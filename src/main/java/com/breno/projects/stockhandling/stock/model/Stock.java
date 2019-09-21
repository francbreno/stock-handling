package com.breno.projects.stockhandling.stock.model;

import java.time.Instant;
import java.util.Objects;

import com.breno.projects.stockhandling.stock.model.exception.FutureStockTimestampException;
import com.breno.projects.stockhandling.stock.model.exception.InvalidStockQuantityException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class represent the <em>business concept</em> of a <strong>Stock</strong>.
 * 
 * <p>It has the methods responsible for some business operations related to
 * the stock of a product.
 * 
 * @author breno
 *
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class Stock {
	private final Long id;
	private final String stockId;
	private final String productId;
	private Integer quantity;
	private Instant timestamp;
	private final Integer version;
	
	public static Stock withoutVersion(
			String stockId, String productId, Integer quantity, 
			Instant timestamp) {

		return new Stock(null, stockId, productId, quantity, timestamp, null);
	}
	
	public static Stock withVersion(
			Long id, String stockId, String productId, Integer quantity, 
			Instant timestamp, Integer version) {

		return new Stock(id, stockId, productId, quantity, timestamp, version);
	}

	/**
	 * This method represents the business operation of updating
	 * {@link Stock Stock}.
	 * 
	 * It validates some rules related to quantity and timestamp.
	 * 
	 * @param 	quantity
	 * 			The quantity value. Must be >= 0
	 * @param 	timestamp
	 * 			The timestamp of the update. Must not be in future.
	 */
	public void updateStock(Integer quantity, Instant timestamp) {
		// If any of the arguments are null at this point, something is really wrong some steps before
		Objects.requireNonNull(quantity, "quantity cannot be null");
		Objects.requireNonNull(timestamp, "timestamp cannot be null");
		
		if (quantity < 0) {
			throw new InvalidStockQuantityException("Can't update stock with quantity less than zero");
		}
		
		if (timestamp.isAfter(Instant.now())) {
			throw new FutureStockTimestampException("Can't update stock with a future timestamp");
		}
		
		this.quantity = quantity;
		this.timestamp = timestamp;
	}
}
