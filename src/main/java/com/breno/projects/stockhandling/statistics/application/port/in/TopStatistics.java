package com.breno.projects.stockhandling.statistics.application.port.in;

import java.util.List;

import com.breno.projects.stockhandling.statistics.adapter.in.api.response.AvailableProductDto;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.ProductSellsDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TopStatistics {
	private final List<AvailableProductDto> topAvailableProducts;
	private final List<ProductSellsDto> topSellingProducts;
}
