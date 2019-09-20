package com.breno.projects.stockhandling.statistics.adapter.in.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class ProductSellsDto {
	private final String productId;
	private final Integer itemsSold;
}
