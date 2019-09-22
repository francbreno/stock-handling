package com.breno.projects.stockhandling.statistics.domain;

/**
 * Represents the type of filter to be used on
 * searches.
 * 
 * @author breno
 *
 */
public enum StatisticsRangeFilterType {
	TODAY, LAST_MONTH;
	
	/**
	 * Returns a {@link StatisticsRangeFilterType StatisticsRangeFilterType}
	 * to be used to look for product statistics
	 * 
	 * @param range
	 * @return
	 */
	public static StatisticsRangeFilterType of(String range) {
		return "lastMonth".equalsIgnoreCase(range) ? LAST_MONTH : TODAY;
	}
}
