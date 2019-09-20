package com.breno.projects.stockhandling.stock.application.port.out;

import com.breno.projects.stockhandling.stock.model.Stock;

/**
 * This interface represents a 
 * 
 * @author breno
 *
 */
public interface UpdateStockStatePort {

	void update(Stock stock);
}
