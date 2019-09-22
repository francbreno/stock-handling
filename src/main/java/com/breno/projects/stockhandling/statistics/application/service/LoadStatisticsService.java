package com.breno.projects.stockhandling.statistics.application.service;

import static com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType.TODAY;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.breno.projects.stockhandling.shared.utils.DateUtil;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.AvailableProductDto;
import com.breno.projects.stockhandling.statistics.adapter.in.api.response.ProductSellsDto;
import com.breno.projects.stockhandling.statistics.application.port.in.LoadStatisticsQuery;
import com.breno.projects.stockhandling.statistics.application.port.in.TopStatistics;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadStatisticsPort;
import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;
import com.breno.projects.stockhandling.statistics.domain.ProductStatisticsSummarized;
import com.breno.projects.stockhandling.statistics.domain.StatisticsRangeFilterType;
import com.breno.projects.stockhandling.statistics.domain.StockEvent;

import lombok.RequiredArgsConstructor;

/**
 * Service responsible for querying all statistics of all products
 * 
 * <p>Not the best option since we could let the database
 * deal with it, but it gave-me the possibility to explore more
 * the streams API. 
 * 
 * @author breno
 *
 */
@Service
@RequiredArgsConstructor
public class LoadStatisticsService implements LoadStatisticsQuery {
	private final LoadStatisticsPort loadStatisticsPort;
	
	@Override
	public TopStatistics loadStatistics(
			StatisticsRangeFilterType rangeType, Instant timestamp) {

		List<ProductStatistics> statistics = loadStatisticsPort.loadStatistics();
		
		List<ProductStatisticsSummarized> statisticsSummarized = statistics
			.stream()
			.flatMap(s -> {
				List<StockEvent> filteredEvents = s.getStockEvents()
					.stream()
					.filter(defineFilterRange(rangeType, timestamp))
					.sorted(comparing(StockEvent::getTimestamp).reversed())
					.collect(toList());
				
				if (filteredEvents.isEmpty()) {
					return Stream.empty();
				}
				
				StockEvent firstEvent = filteredEvents.get(0);
				// FIXME - Problem here: Works considering selling events only
				StockEvent lastEvent = filteredEvents.get(filteredEvents.size() - 1);
				
				Integer totalSales = lastEvent.getQuantity() - firstEvent.getQuantity();
				
				return Stream.of(ProductStatisticsSummarized.builder()
						.productId(s.getProductId())
						.stockId(lastEvent.getStockId())
						.timestamp(lastEvent.getTimestamp())
						.quantity(lastEvent.getQuantity())
						.totalSales(totalSales)
						.build());
			})
			.collect(toList());
			
		List<AvailableProductDto> topAvailableProducts =
			extractTopAvailableProducts(statisticsSummarized);	

		List<ProductSellsDto> topSellingProducts = 
			extractTopSellingProducts(statisticsSummarized);
				
		return new TopStatistics(topAvailableProducts, topSellingProducts);
	}

	private Predicate<? super StockEvent> defineFilterRange(
			StatisticsRangeFilterType rangeType,
			Instant timestamp) {

		return rangeType == TODAY
			? statsTimestampIsInTheLastMonth()
			: statTimestampIsToday(timestamp);
	}

	private List<ProductSellsDto> extractTopSellingProducts(
			List<ProductStatisticsSummarized> statisticsSummarized) {

		return statisticsSummarized
				.stream()
				.sorted(comparing(ProductStatisticsSummarized::getTotalSales).reversed())
				.limit(3)
				.map(this::mapToProductSellsDto)
				.collect(toList());
	}

	private List<AvailableProductDto> extractTopAvailableProducts(
			List<ProductStatisticsSummarized> topStatistics) {

		return topStatistics
				.stream()
				.sorted(comparing(ProductStatisticsSummarized::getQuantity).reversed())
				.limit(3)
				.map(this::mapToAvailableProductDto)
				.collect(toList());
	}

	private Predicate<? super StockEvent> statsTimestampIsInTheLastMonth() {
		return stats -> {
			LocalDateTime dateTime =
					DateUtil.getLocalDateTimeFromInstant(stats.getTimestamp());

			return DateUtil.isInTheLastMonth(dateTime);
		};
	}
	
	private Predicate<? super StockEvent> statTimestampIsToday(
			Instant requestTimestamp) {

		return stats ->
			DateUtil.isFromStartOfDayUntilNow(
					stats.getTimestamp(), requestTimestamp);
	}
	
	private AvailableProductDto mapToAvailableProductDto(
			ProductStatisticsSummarized statisticsSummarized) {

		return AvailableProductDto.builder()
				.id(statisticsSummarized.getStockId())
				.productId(statisticsSummarized.getProductId())
				.timestamp(statisticsSummarized.getTimestamp())
				.quantity(statisticsSummarized.getQuantity())
				.build();
	}
	
	private ProductSellsDto mapToProductSellsDto(
			ProductStatisticsSummarized statisticsSummarized) {

		return ProductSellsDto.builder()
				.productId(statisticsSummarized.getProductId())
				.itemsSold(statisticsSummarized.getTotalSales())
				.build();
	}
	
}
