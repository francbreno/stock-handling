package com.breno.projects.stockhandling.statistics.adapter.in.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Represents the selling data per product to be returned after
 * an API call.
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class ProductSellsDto {
	private final String productId;
	private final Integer itemsSold;
}
