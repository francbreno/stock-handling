package com.breno.projects.stockhandling.statistics.adapter.out.persistence.inmemory;

import java.util.List;

import com.breno.projects.stockhandling.shared.adapter.in.api.response.StockDto;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.ProductSellsDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class StatisticsData {
	private final List<StockDto> topAvailableProducts;
	private final List<ProductSellsDto> topSellingProducts;
}
