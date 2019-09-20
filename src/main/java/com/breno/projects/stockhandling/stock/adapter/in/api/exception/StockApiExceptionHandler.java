package com.breno.projects.stockhandling.stock.adapter.in.api.exception;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.breno.projects.stockhandling.stock.model.exception.FutureStockTimestampException;
import com.breno.projects.stockhandling.stock.model.exception.InvalidStockQuantityException;

/**
 * This class is responsible for handling all exceptions thrown
 * during executions of the Stock API operations.
 * 
 * @author breno
 *
 */
@ControllerAdvice
public class StockApiExceptionHandler {

	/**
	 * Exceptions of type @{link EntityNotFoundException EntityNotFoundException}
	 * return response with http status code 404 (NOT FOUND).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({EntityNotFoundException.class})
	public void handle(EntityNotFoundException e) {}
	
	/**
	 * Exceptions of type @{link IfMatchETagNotPresent IfMatchETagNotPresent}
	 * return response with http status code 403 (FORBIDDEN).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({IfMatchETagNotPresent.class})
	public void handle(IfMatchETagNotPresent e) {}
	
	/**
	 * Exceptions of type @{link InvalidIfMatchEtagException InvalidIfMatchEtagException}
	 * return response with http status code 204 (NO_CONTENT).
	 * 
	 * <p>Obs.: Mozilla recommends the status code 412 (Precondition Failed).
	 * 
	 * <p>Obs.: 2xx codes usually are used for success operations.
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler({InvalidIfMatchEtagException.class})
	public void handle(InvalidIfMatchEtagException e) {}
	
	/**
	 * Exceptions of type @{link ConstraintViolationException ConstraintViolationException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * <p>By default,
	 * {@link ConstraintViolationException ConstraintViolationException}
	 * exceptions are not specially handled by Spring. The response
	 * is returned with status
	 * {@link HttpStatus.INTERNAL_SERVER_ERROR HttpStatus.INTERNAL_SERVER_ERROR}.
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ConstraintViolationException.class})
	public void handle(ConstraintViolationException e) {}
	
	/**
	 * Exceptions of type @{link HttpMessageNotReadableException HttpMessageNotReadableException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public void handle(HttpMessageNotReadableException e) {}
	
	/**
	 * Exceptions of type @{link InvalidStockQuantityException InvalidStockQuantityException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({InvalidStockQuantityException.class})
	public void handle(InvalidStockQuantityException e) {}
	
	/**
	 * Exceptions of type @{link FutureStockTimestampException FutureStockTimestampException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({FutureStockTimestampException.class})
	public void handle(FutureStockTimestampException e) {}
	
	/**
	 * Makes every @{link RuntimeException RuntimeException} to
	 * generate a response with status code 500 (INTERNAL_SERVER_ERROR).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 */
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler({RuntimeException.class})
//	public void handle(RuntimeException e) {}
}
