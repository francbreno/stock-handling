package com.breno.projects.stockhandling.stock.application.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.breno.projects.stockhandling.stock.application.port.in.GetStockQuery;
import com.breno.projects.stockhandling.stock.application.port.out.LoadStockPort;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

/**
 * This class represents an implementations for the <em>Port</em>,
 * or <em>Use Case</em>, <strong>Load Stock</Strong>
 * ({@link LoadStockPort LoadStockPort}).
 * 
 * @author breno
 *
 */
@Service
@RequiredArgsConstructor
public class GetStockService implements GetStockQuery {
	
	private final LoadStockPort loadStockPort;

	@Override
	public Stock getStock(String productId) {
		if (StringUtils.isEmpty(productId)) {
			throw new RuntimeException();
		}
		
		return loadStockPort.loadStock(productId);
	}
}
