package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This class represents a MongoDB document to keep data
 * related to Product Statistics.
 * 
 * @author breno
 *
 */
@Getter
@RequiredArgsConstructor
@Builder
@Document(collection = "productStatistics")
public class ProductStatisticsDocument {

	@Id
	private final String id;
	private final List<StockUpdateEvent> stockUpdateEvents;
}
