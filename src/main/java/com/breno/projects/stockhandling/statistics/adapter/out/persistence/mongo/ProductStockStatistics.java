package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class ProductStockStatistics {
	private final String stockId;
	private final Instant timestamp;
	private final Integer quantity;
}
