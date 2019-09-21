package com.breno.projects.stockhandling.statistics.adapter.in.api.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class AvailableProductDto {
	private final String id;
	private final String productId;
	private final Instant timestamp;	
	private final Integer quantity;
}