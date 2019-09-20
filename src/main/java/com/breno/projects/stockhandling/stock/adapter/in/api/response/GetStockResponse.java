package com.breno.projects.stockhandling.stock.adapter.in.api.response;

import java.time.Instant;

import com.breno.projects.stockhandling.shared.adapter.in.api.response.StockDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class GetStockResponse {
	private final String productId;
	private final Instant requestTimestamp;
	private final StockDto stock;
}
