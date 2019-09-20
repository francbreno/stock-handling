package com.breno.projects.stockhandling.shared.application.out;

import java.util.function.Function;

/**
 * This interface defines the contract of an Observable to handle
 * stock update events.
 * 
 * <p>The necessity of this Observer is to decouple the bounded contexts
 * <strong>Stock</strong> and <strong>Statistics</strong>
 * 
 * @author breno
 *
 */
public interface StockUpdatedEventObservable {
	
	/**
	 * 
	 * @param id
	 * @param handler
	 * @return
	 */
	boolean register(String id, Function<StockUpdatedEvent, Void> handler);

	/**
	 * 
	 * @param event
	 */
	void notifyAll(StockUpdatedEvent event);

	/**
	 * 
	 * @param event
	 */
	void update(StockUpdatedEvent event);
}
