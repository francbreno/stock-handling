package com.breno.projects.stockhandling.statistics.application.port.out;

import java.util.List;

import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;

/**
 * This port represents a an out port to be used by the application
 * to obtain all statistics data.
 * 
 * @author breno
 *
 */
public interface LoadStatisticsPort {
	List<ProductStatistics> loadStatistics();
}
