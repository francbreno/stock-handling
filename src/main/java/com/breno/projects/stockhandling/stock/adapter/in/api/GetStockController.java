package com.breno.projects.stockhandling.stock.adapter.in.api;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breno.projects.stockhandling.shared.adapter.in.api.response.StockDto;
import com.breno.projects.stockhandling.stock.adapter.in.api.helpers.ETagStringCreator;
import com.breno.projects.stockhandling.stock.adapter.in.api.response.GetStockResponse;
import com.breno.projects.stockhandling.stock.application.port.in.GetStockQuery;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class GetStockController {
	private final ETagStringCreator eTagStringCreator;
	private final GetStockQuery getStockQuery;

	@GetMapping
	public ResponseEntity<GetStockResponse> getStock(@RequestParam("productId") String id) {
		Instant requestTimestamp = Instant.now();
		Stock stock = getStockQuery.getStock(id);
		
		String eTag = eTagStringCreator.fromStock(stock);
		
		GetStockResponse responseBody =
				assembleResponseBody(stock, requestTimestamp);
		
		return ResponseEntity.ok()
				.eTag(eTag)
				.body(responseBody);
	}

	private GetStockResponse assembleResponseBody(
			Stock stock, Instant requestTimestamp) {

		StockDto stockDto = StockDto.builder()
				.id(stock.getStockId())
				.timestamp(stock.getTimestamp())
				.quantity(stock.getQuantity())
				.build();

 
		return GetStockResponse.builder()
				.productId(stock.getProductId())
				.requestTimestamp(requestTimestamp)
				.stock(stockDto)
				.build();
	}
}
