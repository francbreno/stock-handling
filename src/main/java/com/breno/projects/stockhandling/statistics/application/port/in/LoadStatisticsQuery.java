package com.breno.projects.stockhandling.statistics.application.port.in;

import java.time.Instant;

public interface LoadStatisticsQuery {
	TopStatistics loadStatistics(
		StatisticsRangeFilterType rangeType, Instant timestamp);
}
