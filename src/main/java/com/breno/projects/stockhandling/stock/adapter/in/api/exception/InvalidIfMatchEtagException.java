package com.breno.projects.stockhandling.stock.adapter.in.api.exception;

/**
 * A special {@link RuntimeException RuntimeException} for
 * indicating that the ETAG value which comes on the header
 * <em>If-Match</em> is invalid.
 * 
 * @author breno
 *
 */
public class InvalidIfMatchEtagException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
