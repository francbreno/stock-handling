package com.breno.projects.stockhandling.statistics.adapter.out.persistence.inmemory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.shared.adapter.in.api.response.StockDto;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.ProductSellsDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatisticsPersistenceAdapter {
	private final Map<String, StockDto> stockStatistics;
	private final Map<String, ProductSellsDto> productsSellingStatistics;
	
	
}
