package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
@Document(collection = "productStatistics")
public class ProductStatisticsDocument {

	@Id
	private final String id;
	
	private final ProductStockStatistics stockStatistics;
	
	@Builder.Default
	private final Integer totalSales = 0;
}
