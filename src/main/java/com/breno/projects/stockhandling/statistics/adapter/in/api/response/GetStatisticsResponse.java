package com.breno.projects.stockhandling.statistics.adapter.in.api.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.breno.projects.stockhandling.shared.adapter.in.api.response.StockDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class GetStatisticsResponse {
	private final OffsetDateTime requestTimestamp;
	private final String range;
	private final List<StockDto> topAvailableProducts;
	private final List<ProductSellsDto> topSellingProducts;
}
