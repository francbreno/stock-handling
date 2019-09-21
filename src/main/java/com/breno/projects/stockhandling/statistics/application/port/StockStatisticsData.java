package com.breno.projects.stockhandling.statistics.application.port;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class StockStatisticsData {
	private final String stockId;
	private final String productId;
	private final Instant timestamp;
	private final Integer quantity;
}