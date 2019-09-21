package com.breno.projects.stockhandling.statistics.application.port.out;

import java.util.List;

import com.breno.projects.stockhandling.statistics.application.port.ProductStockStatisticsData;

public interface LoadStatisticsPort {
	List<ProductStockStatisticsData> loadStatistics();
}
