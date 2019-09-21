package com.breno.projects.stockhandling.statistics.adapter.in.api.response;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class GetStatisticsResponse {
	private final Instant requestTimestamp;
	private final String range;
	private final List<AvailableProductDto> topAvailableProducts;
	private final List<ProductSellsDto> topSellingProducts;
}
