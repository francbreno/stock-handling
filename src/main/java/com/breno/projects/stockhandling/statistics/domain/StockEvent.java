package com.breno.projects.stockhandling.statistics.domain;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This class represents an update event on a Product stock
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
@Builder
@Getter
public class StockEvent {
	private final String stockId;
	private final Instant timestamp;
	private final Integer quantity;
}
