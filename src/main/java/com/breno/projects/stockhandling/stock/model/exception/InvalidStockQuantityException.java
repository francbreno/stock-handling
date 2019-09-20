package com.breno.projects.stockhandling.stock.model.exception;

public class InvalidStockQuantityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InvalidStockQuantityException(String message) {
		super(message);
	}
}
