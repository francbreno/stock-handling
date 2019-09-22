package com.breno.projects.stockhandling.statistics.application.port.out;

import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;

/**
 * 
 * This port is responsible for updating the stok statistics of a produt 
 * 
 * @author breno
 *
 */
public interface UpdateStockStatisticsStatePort {
	/**
	 * Update statistics data related to a informed ProductStatistics.
	 * 
	 * @param stockData
	 */
	void update(ProductStatistics stockData);
}
