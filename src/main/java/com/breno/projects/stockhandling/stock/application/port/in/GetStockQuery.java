package com.breno.projects.stockhandling.stock.application.port.in;

import com.breno.projects.stockhandling.stock.model.Stock;

/**
 * This interface represents an entry <em>Port</em> or a <em>Use Case</em>
 * for retrieving a {@link Stock Stock} item.
 * 
 * @author breno
 *
 */
public interface GetStockQuery {

	/**
	 * Returns a {@link Stock Stock} with the provided product id, if exists.
	 * 
	 * @param 	id
	 * 			The id of a product.
	 * @return
	 * 			A Stock item.
	 */
	Stock getStock(String productId);
}
