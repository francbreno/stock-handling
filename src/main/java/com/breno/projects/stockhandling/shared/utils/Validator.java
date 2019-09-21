package com.breno.projects.stockhandling.shared.utils;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class 
 * 
 * @author breno
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validator {

	/**
	 * 
	 * 
	 * @param <T>
	 * @param value
	 * @param predicate
	 * @param errorMessage
	 */
	public static <T, U> BiConsumer<T, U> createValidator(Predicate<T> predicate) {
		return (value, errorMessage) -> {
			if(!predicate.test(value)) {
				throw new IllegalArgumentException((String) errorMessage);
			}
		};
	}
}
