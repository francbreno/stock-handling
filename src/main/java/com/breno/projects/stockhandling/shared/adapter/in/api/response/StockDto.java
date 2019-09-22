package com.breno.projects.stockhandling.shared.adapter.in.api.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the Stock Json data returned by the API.
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class StockDto {
	private final String id;
	private final Instant timestamp;
	private final Integer quantity;
}
