package com.breno.projects.stockhandling.statistics.adapter.in.api.response;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Data to be returned by the API as the response to
 * API requests to the endpoint "statistics".
 * 
 * @author breno
 *
 */
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
