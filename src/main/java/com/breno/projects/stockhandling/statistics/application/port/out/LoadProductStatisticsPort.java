package com.breno.projects.stockhandling.statistics.application.port.out;

import java.util.Optional;

import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;

/**
 * This interface represents an out port to be used by the 
 * application core to load data from an Adapter class.
 * 
 * @author breno
 *
 */
public interface LoadProductStatisticsPort {
	/**
	 * Load all statistics data related to a product
	 * 
	 * @param 	productId
	 * 			The id of the product to be retrieved.
	 * @return
	 */
	Optional<ProductStatistics> load(String productId);
}
