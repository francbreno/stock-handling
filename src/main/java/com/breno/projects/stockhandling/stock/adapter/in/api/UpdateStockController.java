package com.breno.projects.stockhandling.stock.adapter.in.api;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breno.projects.stockhandling.stock.adapter.in.api.exception.IfMatchETagNotPresent;
import com.breno.projects.stockhandling.stock.adapter.in.api.exception.InvalidIfMatchEtagException;
import com.breno.projects.stockhandling.stock.adapter.in.api.helpers.ETagStringCreator;
import com.breno.projects.stockhandling.stock.adapter.in.api.request.UpdateStockRequest;
import com.breno.projects.stockhandling.stock.application.port.in.GetStockQuery;
import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockCommand;
import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockUseCase;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

/**
 * This controller is responsible for updating a product stock
 * data.
 * 
 * <p>An ETAG is returned to help controlling concurrent requests
 * against the update endpoint.
 * 
 * @author breno
 *
 */
@RestController
@RequestMapping("/stock/update")
@RequiredArgsConstructor
public class UpdateStockController {
	private static final String ETAG_HEADER = "ETag";

	private final ETagStringCreator eTagStringCreator;
	private final UpdateStockUseCase updateStockUseCase;
	private final GetStockQuery getStockQuery;
	
	@PostMapping
	public void updateStock(
			@RequestHeader(value="If-Match", required=false) String etag,
			@Valid @RequestBody UpdateStockRequest updateRequest,
			HttpServletResponse response) {
		
		checkETag(updateRequest.getProductId(), etag);
		
		UpdateStockCommand updateStockCommand = UpdateStockCommand.builder()
				.id(updateRequest.getId())
				.productId(updateRequest.getProductId())
				.quantity(updateRequest.getQuantity())
				.timestamp(updateRequest.getTimestamp())
				.build();
		
		updateStockUseCase.updateStock(updateStockCommand);
		
		String eTag = eTagStringCreator.fromString(
				updateStockCommand.getTimestamp().toString()
					+ updateStockCommand.getQuantity());

		response.setHeader(ETAG_HEADER, eTag);
	}
	
	private void checkETag(String productId, String eTag) {
		if (StringUtils.isEmpty(eTag)) {
			throw new IfMatchETagNotPresent();
		}
		
		Stock stock = getStockQuery.getStock(productId);
		String currentStockETag = eTagStringCreator.fromStock(stock);
		
		if (!currentStockETag.equals(eTag)) {
			throw new InvalidIfMatchEtagException();
		}
	}
}
