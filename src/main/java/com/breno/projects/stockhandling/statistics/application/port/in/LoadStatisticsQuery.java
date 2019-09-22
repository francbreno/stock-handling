package com.breno.projects.stockhandling.statistics.application.port.in;

import java.time.Instant;

import com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType;

/**
 * This interface represents a Port to load statistics data.
 * 
 * @author breno
 *
 */
public interface LoadStatisticsQuery {
	/**
	 * Returns all statistics data
	 * 
	 * @param 	rangeType
	 * 			What filter to use on the query
	 * @param 	timestamp
	 * 			A reference point in time to be used as a pivot
	 * 			for data operations
	 * @return
	 * 			a {@link TopStatistics} object containing a list
	 * 			of stocks availability and another of product sells.
	 */
	TopStatistics loadStatistics(
		StatisticsRangeFilterType rangeType, Instant timestamp);
}
