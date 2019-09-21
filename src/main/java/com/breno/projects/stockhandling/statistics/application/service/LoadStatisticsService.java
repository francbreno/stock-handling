package com.breno.projects.stockhandling.statistics.application.service;

import static com.breno.projects.stockhandling.statistics.application.port.in.StatisticsRangeFilterType.TODAY;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.breno.projects.stockhandling.shared.utils.DateUtil;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.AvailableProductDto;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.ProductSellsDto;
import com.breno.projects.stockhandling.statistics.application.port.ProductStockStatisticsData;
import com.breno.projects.stockhandling.statistics.application.port.in.LoadStatisticsQuery;
import com.breno.projects.stockhandling.statistics.application.port.in.StatisticsRangeFilterType;
import com.breno.projects.stockhandling.statistics.application.port.in.TopStatistics;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadStatisticsPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoadStatisticsService implements LoadStatisticsQuery {
	private final LoadStatisticsPort loadStatisticsPort;
	
	@Override
	public TopStatistics loadStatistics(
			StatisticsRangeFilterType rangeType, Instant timestamp) {

		List<ProductStockStatisticsData> statistics =
			loadStatisticsPort.loadStatistics();
		
		Predicate<? super ProductStockStatisticsData> rangeFilter =
			defineFilterRange(rangeType, timestamp);

		List<ProductStockStatisticsData> topStatistics =
			filterStatistics(statistics, rangeFilter);
			
		List<AvailableProductDto> topAvailableProducts =
			extractTopAvailableProducts(topStatistics);	

		List<ProductSellsDto> topSellingProducts = 
			extractTopSellingProducts(topStatistics);
				
		return new TopStatistics(topAvailableProducts, topSellingProducts);
	}

	private Predicate<? super ProductStockStatisticsData> defineFilterRange(
			StatisticsRangeFilterType rangeType,
			Instant timestamp) {

		return rangeType == TODAY
			? statsTimestampIsInTheLastMonth()
			: statTimestampIsToday(timestamp);
	}

	private List<ProductStockStatisticsData> filterStatistics(List<ProductStockStatisticsData> statistics,
			Predicate<? super ProductStockStatisticsData> rangeFilter) {

		return statistics
				.stream()
				.filter(rangeFilter)
				.collect(toList());
	}

	private List<ProductSellsDto> extractTopSellingProducts(List<ProductStockStatisticsData> topStatistics) {
		return topStatistics
				.stream()
				.sorted(comparing(ProductStockStatisticsData::getTotalSales).reversed())
				.limit(3)
				.map(this::mapToProductSellsDto)
				.collect(toList());
	}

	private List<AvailableProductDto> extractTopAvailableProducts(List<ProductStockStatisticsData> topStatistics) {
		return topStatistics
				.stream()
				.sorted(comparing(ProductStockStatisticsData::getQuantity).reversed())
				.limit(3)
				.map(this::mapToAvailableProductDto)
				.collect(toList());
	}

	private Predicate<? super ProductStockStatisticsData> statsTimestampIsInTheLastMonth() {
		return stats -> DateUtil.isInTheLastMonth(stats.getTimestamp());
	}
	
	private Predicate<? super ProductStockStatisticsData> statTimestampIsToday(
			Instant requestTimestamp) {

		return stats ->
			DateUtil.isFromStartOfDayUntilNow(
					stats.getTimestamp(), requestTimestamp);
	}
	
	private AvailableProductDto mapToAvailableProductDto(
			ProductStockStatisticsData statsData) {

		return AvailableProductDto.builder()
				.id(statsData.getStockId())
				.productId(statsData.getProductId())
				.timestamp(statsData.getTimestamp())
				.quantity(statsData.getQuantity())
				.build();
	}
	
	private ProductSellsDto mapToProductSellsDto(
			ProductStockStatisticsData statsData) {

		return ProductSellsDto.builder()
				.productId(statsData.getProductId())
				.itemsSold(statsData.getTotalSales())
				.build();
	}
	
}
