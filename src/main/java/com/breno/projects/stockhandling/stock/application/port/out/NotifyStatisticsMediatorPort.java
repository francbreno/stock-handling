package com.breno.projects.stockhandling.stock.application.port.out;

import com.breno.projects.stockhandling.stock.model.Stock;

/**
 * This interface defines the method to notify the statistics
 * context about a stock update.
 * 
 * @author breno
 *
 */
public interface NotifyStatisticsMediatorPort {
	
	/**
	 * Send the new data through the mediator to the
	 * statistics context.
	 * 
	 * @param 	stock
	 * 			The dada related to the stock update
	 */
	void notify(Stock stock);
}
