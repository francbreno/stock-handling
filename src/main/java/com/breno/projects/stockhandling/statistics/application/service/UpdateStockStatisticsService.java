package com.breno.projects.stockhandling.statistics.application.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiConsumer;

import org.springframework.stereotype.Service;

import com.breno.projects.stockhandling.shared.utils.Validator;
import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsCommand;
import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsUseCase;
import com.breno.projects.stockhandling.statistics.application.port.out.LoadProductStatisticsPort;
import com.breno.projects.stockhandling.statistics.application.port.out.UpdateStockStatisticsStatePort;
import com.breno.projects.stockhandling.statistics.domain.ProductStatistics;
import com.breno.projects.stockhandling.statistics.domain.StockEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateStockStatisticsService implements UpdateStockStatisticsUseCase {
	private final UpdateStockStatisticsStatePort updateStockStatisticsStatePort;
	private final LoadProductStatisticsPort loadProductStatisticsPort;

	@Override
	public void update(UpdateStockStatisticsCommand updateStockStatisticsCommand) {
		validate(updateStockStatisticsCommand);
		
		String productId = updateStockStatisticsCommand.getProductId();
		
		ProductStatistics productStatistics =
			loadProductStatisticsPort.load(productId)
				.orElseGet(() -> this.createNewProductStatistics(productId));

		StockEvent stockEvent =
			mapUpdateStockStatisticsCommandToStockEvent(updateStockStatisticsCommand);
			
		productStatistics.addStockEvent(stockEvent);
		updateStockStatisticsStatePort.update(productStatistics);
	}

	private ProductStatistics createNewProductStatistics(String productId) {
		ProductStatistics productStatistics = ProductStatistics.builder()
				.productId(productId)
				.stockEvents(new ArrayList<>())
				.build();

		return productStatistics;
	}

	private StockEvent mapUpdateStockStatisticsCommandToStockEvent(
			UpdateStockStatisticsCommand updateStockStatisticsCommand) {

		StockEvent stockEvent = StockEvent.builder()
				.stockId(updateStockStatisticsCommand.getStockId())
				.timestamp(updateStockStatisticsCommand.getTimestamp())
				.quantity(updateStockStatisticsCommand.getQuantity())
				.build();

		return stockEvent;
	}
	
	/*
	 * Experimenting an idea for validation. Needs to improve a lot!
	 */
	private void validate(UpdateStockStatisticsCommand stockStatisticsData) {
		BiConsumer<Object, Object> nonNullValidator = Validator.createValidator(Objects::nonNull);
		
		nonNullValidator.accept(stockStatisticsData.getStockId(), "stockId cannot be null");
		nonNullValidator.accept(stockStatisticsData.getProductId(), "productId cannot be null");
		nonNullValidator.accept(stockStatisticsData.getTimestamp(), "timestamp cannot be null");
		
		Validator.createValidator((Instant i) -> !i.isAfter(Instant.now()))
			.accept(stockStatisticsData.getTimestamp(), "timestamp cannot be in the future");

		Validator.createValidator((Integer i) -> i >= 0)
			.accept(stockStatisticsData.getQuantity(), "quantity cannot be null");
	}
}
