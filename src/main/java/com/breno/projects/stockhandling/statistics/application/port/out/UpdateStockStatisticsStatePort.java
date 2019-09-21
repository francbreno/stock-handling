package com.breno.projects.stockhandling.statistics.application.port.out;

import com.breno.projects.stockhandling.statistics.application.port.StockStatisticsData;

public interface UpdateStockStatisticsStatePort {
	void update(StockStatisticsData stockData);
}
