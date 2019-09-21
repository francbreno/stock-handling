package com.breno.projects.stockhandling.statistics.application.service;

import java.time.Instant;
import java.util.Objects;
import java.util.function.BiConsumer;

import org.springframework.stereotype.Service;

import com.breno.projects.stockhandling.shared.utils.Validator;
import com.breno.projects.stockhandling.statistics.application.port.StockStatisticsData;
import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsUseCase;
import com.breno.projects.stockhandling.statistics.application.port.out.UpdateStockStatisticsStatePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateStockStatisticsService implements UpdateStockStatisticsUseCase {
	private final UpdateStockStatisticsStatePort updateStockStatisticsStatePort;

	@Override
	public void update(StockStatisticsData stockStatisticsData) {
		validate(stockStatisticsData);
		updateStockStatisticsStatePort.update(stockStatisticsData);
	}
	
	/*
	 * Experimenting an idea for validation. Needs to improve a lot!
	 */
	private void validate(StockStatisticsData stockStatisticsData) {
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
