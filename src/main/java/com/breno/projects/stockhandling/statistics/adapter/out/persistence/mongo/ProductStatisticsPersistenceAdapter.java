package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.statistics.application.port.out.LoadProductStatisticsPort;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadStatisticsPort;
import com.breno.projects.stockhandling.statistics.application.port.out.UpdateStockStatisticsStatePort;
import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;
import com.breno.projects.stockhandling.statistics.domain.StockEvent;

import lombok.RequiredArgsConstructor;

/**
 * This class represents a persistence adapter responsible for the
 * data base operations over mongoDB
 * 
 * @author breno
 *
 */
@Component
@RequiredArgsConstructor
public class ProductStatisticsPersistenceAdapter implements UpdateStockStatisticsStatePort,
	LoadStatisticsPort, LoadProductStatisticsPort {

	private final ProductStatisticsRepository productsStatisticsRepository;

	@Override
	public void update(ProductStatistics productStatistics) {
		Objects.requireNonNull(productStatistics);
		
		ProductStatisticsDocument productStatisticsDocument =
				mapProductStatisticsToDocument(productStatistics);
		
		productsStatisticsRepository.save(productStatisticsDocument);
	}
	
	@Override
	public List<ProductStatistics> loadStatistics() {
		return productsStatisticsRepository.findAll()
				.stream()
				.map(this::mapDocumentToProductStockStatistics)
				.collect(toList());
	}	

	@Override
	public Optional<ProductStatistics> load(String productId) {
		Optional<ProductStatisticsDocument> productStatisticsDocument =
				productsStatisticsRepository.findById(productId);
		
		return productStatisticsDocument.map(p -> {
			List<StockEvent> stockEvents =
				p.getStockUpdateEvents().stream()
					.map(this::mapStockUpdateEventToStockEvent)
					.collect(toList());
			
			return ProductStatistics.builder()
					.productId(p.getId())
					.stockEvents(stockEvents)
					.build();
		});
	}

	private StockEvent mapStockUpdateEventToStockEvent(StockUpdateEvent event) {
		return StockEvent.builder()
			.stockId(event.getStockId())
			.timestamp(event.getTimestamp())
			.quantity(event.getQuantity())
			.build();
	}

	private ProductStatisticsDocument mapProductStatisticsToDocument(
			ProductStatistics productStatistics) {
		
		List<StockUpdateEvent> stockUpdateEvent =
				productStatistics.getStockEvents().stream()
					.map(this::mapStockEventToStockUpdateEvent)
					.collect(toList());
		
		return ProductStatisticsDocument.builder()
				.id(productStatistics.getProductId())
				.stockUpdateEvents(stockUpdateEvent)
				.build();
	}
	
	private StockUpdateEvent mapStockEventToStockUpdateEvent(StockEvent event) {
		return StockUpdateEvent.builder()
				.stockId(event.getStockId())
				.timestamp(event.getTimestamp())
				.quantity(event.getQuantity())
				.build();
	}

	private ProductStatistics mapDocumentToProductStockStatistics(
			ProductStatisticsDocument productStatistics) {
		
		List<StockEvent> stockEvents =
				productStatistics.getStockUpdateEvents()
					.stream()
					.map(this::mapStockUpdateEventToStockEvent)
					.collect(toList());
			
		return ProductStatistics.builder()
				.productId(productStatistics.getId())
				.stockEvents(stockEvents)
				.build();
	}
}
