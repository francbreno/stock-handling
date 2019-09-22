package com.breno.projects.stockhandling.shared.application.out;

import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsCommand;
import com.breno.projects.stockhandling.statistics.application.port.in.UpdateStockStatisticsUseCase;
import com.breno.projects.stockhandling.stock.application.port.out.NotifyStatisticsMediatorPort;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

/**
 * This class is the <em>Mediator</em> that implements the port
 * {@link NotifyStatisticsMediatorPort NotifyStatisticsMediatorPort}
 * responsible for communicate to the statistics context each stock update 
 * that happens on the stock context.
 * 
 * @author breno
 *
 */
@Component
@RequiredArgsConstructor
public class NotifyStatisticsMediator implements NotifyStatisticsMediatorPort {

	private final UpdateStockStatisticsUseCase updateStockStatisticsUseCase;

	@Override
	public void notify(Stock stock) {
		UpdateStockStatisticsCommand updateCommand = 
				UpdateStockStatisticsCommand.builder()
					.stockId(stock.getStockId())
					.productId(stock.getProductId())
					.timestamp(stock.getTimestamp())
					.quantity(stock.getQuantity())
					.build();

		updateStockStatisticsUseCase.update(updateCommand);
	}
	
	
}
