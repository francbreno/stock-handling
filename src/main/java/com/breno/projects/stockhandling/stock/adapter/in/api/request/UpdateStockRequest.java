package com.breno.projects.stockhandling.stock.adapter.in.api.request;

import java.time.Instant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class UpdateStockRequest {
	@NotNull
	private final String id;
	
	@NotNull
	private final String productId;
	
	@NotNull
	private final Instant timestamp;
	
	@NotNull
	@Min(0)
	private final Integer quantity;
}
