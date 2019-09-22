package com.breno.projects.stockhandling.statistics.application.port.in;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A command object containing data to be used to update
 * stockc statistics.
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
@Builder
@Getter
public class UpdateStockStatisticsCommand {
	private final String productId;
	private final String stockId;
	private final Instant timestamp;
	private final Integer quantity;
}
