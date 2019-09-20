package com.breno.projects.stockhandling.stock.model.exception;

public class FutureStockTimestampException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FutureStockTimestampException(String message) {
		super(message);
	}
}
