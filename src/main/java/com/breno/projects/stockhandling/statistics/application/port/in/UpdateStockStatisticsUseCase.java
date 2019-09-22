package com.breno.projects.stockhandling.statistics.application.port.in;

/**
 * This use case is a port to be used by external layers to
 * communicate with the inner application.
 * 
 * @author breno
 *
 */
public interface UpdateStockStatisticsUseCase {

	/**
	 * Update a product stock
	 * 
	 * @param 	updateStockStatisticsCommand
	 * 			An object containing data to be used to update
	 * 			the statistics of a product.
	 */
	void update(UpdateStockStatisticsCommand updateStockStatisticsCommand);
}
