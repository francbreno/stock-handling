package com.breno.projects.stockhandling.statistics.util;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import com.breno.projects.stockhandling.shared.utils.DateUtil;
import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;
import com.breno.projects.stockhandling.statistics.domain.StockEvent;

/**
 * This class is responsible to produce statistics data for tests
 * related to the Statistics context.
 * 
 * @author breno
 *
 */
public class ProductStatisticsTestDataProvider {
	
	/*
	 * This class must not be instantiated.
	 */
	private ProductStatisticsTestDataProvider() {
        throw new AssertionError(ProductStatisticsTestDataProvider.class.getName()
        		+ " cannot be instantiated!");
    }

	/**
	 * Create a list of {@ProductStatistics ProductStatistics} with
	 * randomimzed data.
	 * 
	 * @return a {@ List List} of {@link ProductStatistics ProductStatistics}
	 * for testing.
	 */
	public static List<ProductStatistics> createStatistics() {
		return IntStream.range(0, 1000)
				.mapToObj(i -> {
					String productId = "P" + i;
					String stockId = "S" + i;
					
					List<StockEvent> events = IntStream.range(1, 10)
						.mapToObj(j -> {
							int quantity = ThreadLocalRandom.current().nextInt(0, 60);
							Instant timestamp = Instant.now().minus(j, ChronoUnit.DAYS);

							return StockEvent.builder()
									.stockId(stockId)
									.timestamp(timestamp)
									.quantity(quantity)
								.build();
						})
						.collect(toList());
					
					return ProductStatistics.builder()
							.productId(productId)
							.stockEvents(events)
							.build();
				})
				.collect(toList());
	}
}
