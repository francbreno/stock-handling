package com.breno.projects.stockhandling.shared.utils;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class represents a generic Validator Factory.
 * 
 * <p>WIP (not for production)
 * 
 * @author breno
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validator {

	/**
	 * A higher order method to return a lambda that execute a validation.
	 * 
	 * @param 	<T>
	 * 			The Type of the value to be checked
	 * @param 	value
	 * 			The value to be cnhecked
	 * @param 	predicate
	 * 			The predicate to test against
	 * @param 	errorMessage
	 * 			An error message to be used as a description of the validation error. 
	 */
	public static <T, U> BiConsumer<T, U> createValidator(Predicate<T> predicate) {
		return (value, errorMessage) -> {
			if(!predicate.test(value)) {
				throw new IllegalArgumentException((String) errorMessage);
			}
		};
	}
}
