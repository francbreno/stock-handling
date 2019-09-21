package com.breno.projects.stockhandling.stock.application.port.out;

import java.time.Instant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StockUpdatedData {
	private final String stockId;
	private final Instant timestamp;
	private final Integer quantity;
}
