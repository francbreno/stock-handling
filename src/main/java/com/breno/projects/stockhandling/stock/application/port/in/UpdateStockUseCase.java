package com.breno.projects.stockhandling.stock.application.port.in;

public interface UpdateStockUseCase {

	/**
	 * 
	 * 
	 * @param command

	 */
	void updateStock(UpdateStockCommand command);
}
