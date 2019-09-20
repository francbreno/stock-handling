package com.breno.projects.stockhandling.stock.adapter.in.api.exception;

/**
 * A special {@link RuntimeException RuntimeException} for
 * indicating that the ETAG value was not provided for the
 * header <em>If-Match</em>.
 * 
 * @author breno
 *
 */
public class IfMatchETagNotPresent extends RuntimeException {
	private static final long serialVersionUID = 1L;

}
