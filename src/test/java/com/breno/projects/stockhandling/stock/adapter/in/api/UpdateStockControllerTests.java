package com.breno.projects.stockhandling.stock.adapter.in.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.breno.projects.stockhandling.stock.adapter.in.api.helpers.ETagStringCreator;
import com.breno.projects.stockhandling.stock.adapter.in.api.request.UpdateStockRequest;
import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockCommand;
import com.breno.projects.stockhandling.stock.application.service.GetStockService;
import com.breno.projects.stockhandling.stock.application.service.UpdateStockService;
import com.breno.projects.stockhandling.stock.model.Stock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UpdateStockController.class)
public class UpdateStockControllerTests {
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ETagStringCreator eTagStringCreator;
	
	@MockBean
	UpdateStockService updateStockService;
	
	@MockBean
	GetStockService getStockService;
	
	@Test
	public void shouldReturnOkAndUpdatedETagWhenUpdatingWithValidStockDataAndValidIfMatchHeader()
			throws JsonProcessingException, Exception {

		String productId = "P1";
		
		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.productId(productId)
					.quantity(10)
					.timestamp(Instant.now()).build();
		
		UpdateStockCommand updateStockCommand =
				UpdateStockCommand.builder()
					.id(updateStockRequest.getId())
					.productId(updateStockRequest.getProductId())
					.timestamp(updateStockRequest.getTimestamp())
					.quantity(updateStockRequest.getQuantity())
					.build();
		
		Stock updatedStock =
				Stock.builder()
					.id(99L)
					.version(1)
					.stockId(updateStockCommand.getId())
					.productId(updateStockCommand.getProductId())
					.timestamp(updateStockCommand.getTimestamp())
					.quantity(updateStockCommand.getQuantity())
					.build();

		String ifMatchHeader = eTagStringCreator.fromString(
				updateStockRequest.getTimestamp().toString()
					+ updateStockRequest.getQuantity());

		String expectedETag = eTagStringCreator.fromStock(updatedStock);
		
		when(getStockService.getStock(productId))
			.thenReturn(updatedStock);
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", ifMatchHeader))
			.andExpect(status().isOk())
			.andExpect(header().string("etag", expectedETag))
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnNoContentWhenUpdatingStockWithOutdatedETag() throws Exception {
		String productId = "P1";
		
		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.productId(productId)
					.quantity(10)
					.timestamp(Instant.now()).build();
		
		Stock aStock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId(productId)
				.quantity(15)
				.timestamp(Instant.now()).build();
		
		String outdateETag = eTagStringCreator.fromString(
				updateStockRequest.getTimestamp().toString()
					+ updateStockRequest.getQuantity());
		
		when(getStockService.getStock(productId))
			.thenReturn(aStock);
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", outdateETag))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnForbiddentWhenUpdatingStockWithoutETag() throws Exception {
		String productId = "P1";
		
		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.productId(productId)
					.quantity(10)
					.timestamp(Instant.now()).build();
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest)))
			.andExpect(status().isForbidden())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnBadRequestWhenUpdatingStockWithoutProductId()
			throws JsonProcessingException, Exception {
		
		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.quantity(10)
					.timestamp(Instant.now()).build();
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", "aeiou123"))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnBadRequestWhenUpdatingStockWithoutStockId()
			throws JsonProcessingException, Exception {

		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.productId("P1")
					.quantity(22)
					.timestamp(Instant.now()).build();
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", "aeiou123"))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnBadRequestWhenUpdatingStockWithoutTimestamp()
			throws JsonProcessingException, Exception {

		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.productId("P1")
					.quantity(22)
					.build();
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", "aeiou123"))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnBadRequestWhenUpdatingStockWithoutQuantity()
			throws JsonProcessingException, Exception {

		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.productId("P1")
					.timestamp(Instant.now())
					.build();
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", "aeiou123"))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnBadRequestWhenUpdatingStockWithNegativeQuantity()
			throws JsonProcessingException, Exception {

		UpdateStockRequest updateStockRequest =
				UpdateStockRequest.builder()
					.id("S1")
					.productId("P1")
					.quantity(-1)
					.timestamp(Instant.now())
					.build();
		
		mockMvc
			.perform(post("/stock/update")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.content(mapper.writeValueAsString(updateStockRequest))
				.header("If-Match", "aeiou123"))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
	}
}
