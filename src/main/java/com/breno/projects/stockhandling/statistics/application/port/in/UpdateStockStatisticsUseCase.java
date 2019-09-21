package com.breno.projects.stockhandling.statistics.application.port.in;

import com.breno.projects.stockhandling.statistics.application.port.StockStatisticsData;

public interface UpdateStockStatisticsUseCase {

	void update(StockStatisticsData stockStatisticsData);
}
