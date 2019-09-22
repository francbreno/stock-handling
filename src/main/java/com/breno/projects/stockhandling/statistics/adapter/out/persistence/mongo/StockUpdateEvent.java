package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the embedded event data to be inserted into the
 * ProductStatisticsDocument.
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
@Builder
@Getter
public class StockUpdateEvent {
	private final String stockId;
	private final Instant timestamp;
	private final Integer quantity;
}
