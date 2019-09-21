package com.breno.projects.stockhandling.statistics.application.port.in;

public enum StatisticsRangeFilterType {
	TODAY, LAST_MONTH;
	
	public static StatisticsRangeFilterType of(String range) {
		return "lastMonth".equalsIgnoreCase(range) ? LAST_MONTH : TODAY;
	}
}
