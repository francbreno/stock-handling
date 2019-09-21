package com.breno.projects.stockhandling;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo.ProductStatisticsDocument;
import com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo.ProductStatisticsRepository;
import com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo.ProductStockStatistics;
import com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa.StockEntity;
import com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa.StockRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	
	private final StockRepository stockRepository;
	private final ProductStatisticsRepository statisticsRepository;

	@Override
	public void run(String... args) throws Exception {
		IntStream.range(0, 20)
			.forEach(i -> {
				int quantity = ThreadLocalRandom.current().nextInt(0, 60);
				int totalSales = ThreadLocalRandom.current().nextInt(0, 1000);
				Instant timestamp = Instant.now().minus(quantity, ChronoUnit.DAYS);
				String productId = "P" + i;
				String stockId = "S" + i;
				stockRepository.save(StockEntity.builder()
					.stockId(stockId)
					.productId(productId)
					.quantity(quantity)
					.timestamp(timestamp)
					.build());
				
				statisticsRepository.save(ProductStatisticsDocument.builder()
						.id(productId)
						.totalSales(totalSales)
						.stockStatistics(ProductStockStatistics.builder()
								.stockId(stockId)
								.timestamp(timestamp)
								.quantity(quantity)
								.build())
						.build());
			});
		
	}
	
}
