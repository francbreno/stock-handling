package com.breno.projects.stockhandling.statistics.domain;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class ProductStatisticsSummarized {
	private final String productId;
	private final String stockId;
	private final Instant timestamp;
	private final Integer quantity;
	private final Integer totalSales;
}
