package com.breno.projects.stockhandling.stock.adapter.in.api.exception;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.breno.projects.stockhandling.stock.model.exception.FutureStockTimestampException;
import com.breno.projects.stockhandling.stock.model.exception.InvalidStockQuantityException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code NOT_FOUND}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<ErrorResponseBody> handle(EntityNotFoundException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(body);
	}
	
	/**
	 * Exceptions of type @{link IfMatchETagNotPresent IfMatchETagNotPresent}
	 * return response with http status code 403 (FORBIDDEN).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code FORBIDDEN}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({IfMatchETagNotPresent.class})
	public ResponseEntity<ErrorResponseBody> handle(IfMatchETagNotPresent e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(body);
	}
	
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
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code NO_CONTENT}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({InvalidIfMatchEtagException.class})
	public ResponseEntity<ErrorResponseBody> handle(InvalidIfMatchEtagException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body(body);
	}
	
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
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code BAD_REQUEST}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<ErrorResponseBody> handle(ConstraintViolationException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(body);
	}
	
	/**
	 * Exceptions of type @{link HttpMessageNotReadableException HttpMessageNotReadableException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code BAD_REQUEST}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ErrorResponseBody> handle(HttpMessageNotReadableException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(body);
	}
	
	/**
	 * Exceptions of type @{link InvalidStockQuantityException InvalidStockQuantityException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code BAD_REQUEST}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({InvalidStockQuantityException.class})
	public ResponseEntity<ErrorResponseBody> handle(InvalidStockQuantityException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(body);
	}
	
	/**
	 * Exceptions of type @{link FutureStockTimestampException FutureStockTimestampException}
	 * return response with http status code 400 (BAD_REQUEST).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 * @return	a {@link ResponseEntity ResponseEntity} with status {@code BAD_REQUEST}
	 * 			and a body with the instant the error happened and an error message
	 */
	@ExceptionHandler({FutureStockTimestampException.class})
	public ResponseEntity<ErrorResponseBody> handle(FutureStockTimestampException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), e.getMessage());
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(body);
	}
	
	/**
	 * Makes every @{link RuntimeException RuntimeException} to
	 * generate a response with status code 500 (INTERNAL_SERVER_ERROR).
	 * 
	 * @param 	e
	 * 			The exception that was thrown.
	 * @return	a {@link ResponseEntity ResponseEntity} with status
	 * 			{@code INTERNAL_SERVER_ERROR} and a body with the instant
	 * 			the error happened and an error message
	 */
	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<ErrorResponseBody> handle(RuntimeException e) {
		ErrorResponseBody body = new ErrorResponseBody(Instant.now(), "Something went wrong!");
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(body);
	}
	
	@RequiredArgsConstructor
	@Getter
	static class ErrorResponseBody {
		private final Instant timestamp;
		private final String message;
	}
}
