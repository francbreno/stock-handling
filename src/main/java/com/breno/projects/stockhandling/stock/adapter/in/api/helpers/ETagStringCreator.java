package com.breno.projects.stockhandling.stock.adapter.in.api.helpers;

import java.util.Objects;

import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

/**
 * This class creates <strong>Strings</strong>
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
public class ETagStringCreator {
	private final HashGenerator hashGenerator;
	
	/**
	 * Returns a hash value from the provided {@link Stock Stock}.
	 * 
	 * <p>This method will helpful to generate eTags during tests.
	 * 
	 * @param 	stringtoHash
	 * 			A String objecto to be 'hashed'.
	 * @return	A {@link String String} representing the hash value.
	 */
	public String fromStock(Stock stock) {
		Objects.requireNonNull(stock);
		Objects.requireNonNull(stock.getTimestamp());
		
		return hashGenerator.generate(
				stock.getTimestamp().toString() + stock.getQuantity());
	}
	
	/**
	 * Returns a hash value from the provided string.
	 * 
	 * This method will help during tests.
	 * 
	 * @param 	stringtoHash
	 * 			A String objecto to be 'hashed'.
	 * @return	A {@link String String} representing the hash value.
	 */
	public String fromString(String stringtoHash) {
		Objects.requireNonNull(stringtoHash);
		
		return hashGenerator.generate(stringtoHash);
	}
}
