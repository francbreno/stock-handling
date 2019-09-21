package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.statistics.application.port.ProductStockStatisticsData;
import com.breno.projects.stockhandling.statistics.application.port.StockStatisticsData;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadStatisticsPort;
import com.breno.projects.stockhandling.statistics.application.port.out.UpdateStockStatisticsStatePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductStatisticsPersistenceAdapter
	implements UpdateStockStatisticsStatePort, LoadStatisticsPort {

	private final ProductStatisticsRepository productsStatisticsRepository;

	@Override
	public void update(StockStatisticsData stockData) {
		Objects.requireNonNull(stockData);
		
		ProductStatisticsDocument productStatistics =
				mapStockStatisticsDataToDocument(stockData);
		
		productsStatisticsRepository.save(productStatistics);
	}
	
	@Override
	public List<ProductStockStatisticsData> loadStatistics() {
		return productsStatisticsRepository.findAll()
				.stream()
				.map(this::mapDocumentToProductStockStatisticsData)
				.collect(toList());
	}

	private ProductStatisticsDocument mapStockStatisticsDataToDocument(
			StockStatisticsData stockData) {

		ProductStockStatistics productStockStats =
			ProductStockStatistics.builder()
				.stockId(stockData.getStockId())
				.timestamp(stockData.getTimestamp())
				.quantity(stockData.getQuantity())
				.build();
		
		ProductStatisticsDocument productStatistics =
			ProductStatisticsDocument.builder()
				.id(stockData.getProductId())
				.stockStatistics(productStockStats)
				.build();
		return productStatistics;
	}
	
	private ProductStockStatisticsData mapDocumentToProductStockStatisticsData(
			ProductStatisticsDocument productStatistics) {
		
		return ProductStockStatisticsData.builder()
				.productId(productStatistics.getId())
				.stockId(productStatistics.getStockStatistics().getStockId())
				.timestamp(productStatistics.getStockStatistics().getTimestamp())
				.quantity(productStatistics.getStockStatistics().getQuantity())
				.totalSales(productStatistics.getTotalSales())
				.build();
	}
}
