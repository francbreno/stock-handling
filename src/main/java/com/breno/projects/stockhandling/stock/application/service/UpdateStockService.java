package com.breno.projects.stockhandling.stock.application.service;

import org.springframework.stereotype.Service;

import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockCommand;
import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockUseCase;
import com.breno.projects.stockhandling.stock.application.port.out.LoadStockPort;
import com.breno.projects.stockhandling.stock.application.port.out.NotifyStatisticsMediatorPort;
import com.breno.projects.stockhandling.stock.application.port.out.UpdateStockStatePort;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateStockService implements UpdateStockUseCase {

	private final UpdateStockStatePort updateStockStatePort;
	private final LoadStockPort loadStockPort;
	private final NotifyStatisticsMediatorPort notifyStatisticsMediatorPort;
	
	@Override
	public void updateStock(UpdateStockCommand command) {
		Stock stock = loadStockPort.loadStock(command.getProductId());
		stock.updateStock(command.getQuantity(), command.getTimestamp());	
		updateStockStatePort.update(stock);
		notifyStatisticsMediatorPort.notify(stock);
	}
}
