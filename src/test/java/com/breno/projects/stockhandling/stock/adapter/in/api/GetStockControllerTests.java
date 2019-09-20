package com.breno.projects.stockhandling.stock.adapter.in.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.breno.projects.stockhandling.stock.adapter.in.api.helpers.ETagStringCreator;
import com.breno.projects.stockhandling.stock.adapter.in.api.response.GetStockResponse;
import com.breno.projects.stockhandling.stock.application.service.GetStockService;
import com.breno.projects.stockhandling.stock.model.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GetStockController.class)
public class GetStockControllerTests {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ETagStringCreator eTagCreator;
	
	@MockBean
	private GetStockService getStockService;
	
	@Test
	public void shouldReturnStockAndOKStatusAndETagWhenProductIdIsValid() throws Exception {
		String productId = "P1";
		Stock aStock = Stock.builder()
				.id(1L)
				.stockId("S1")
				.productId(productId)
				.quantity(10)
				.timestamp(Instant.now())
				.build();
		
		String expectedETag = generateETag(aStock);
		
		when(getStockService.getStock(productId)).thenReturn(aStock);
		
		MvcResult result = mockMvc
				.perform(get("/stock")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.characterEncoding("UTF-8")
						.param("productId", productId))
				.andExpect(status().isOk())
				.andExpect(header().string("etag", expectedETag))
				.andDo(print())
				.andReturn();
		
		GetStockResponse response = mapper.readValue(
				result.getResponse().getContentAsString(),
				GetStockResponse.class);
		
		assertThat(response.getProductId()).isEqualTo(aStock.getProductId());
		assertThat(response.getStock().getId()).isEqualTo(aStock.getStockId());
		assertThat(response.getStock().getQuantity()).isEqualTo(aStock.getQuantity());
		assertThat(response.getStock().getTimestamp()).isEqualTo(aStock.getTimestamp());
	}
	
	@Test
	public void shouldReturnStatusNotFoundWhenProductIdIsInvalid() throws Exception {
		when(getStockService.getStock(Mockito.anyString()))
			.thenThrow(EntityNotFoundException.class);
		
		mockMvc
			.perform(get("/stock")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8")
				.param("productId", "P1"))
			.andExpect(status().isNotFound())
			.andDo(print())
			.andReturn();
	}
	
	@Test
	public void shouldReturnStatusBadRequestWhenProductIdIsNotProvided() throws Exception {	
		when(getStockService.getStock(Mockito.anyString()))
			.thenThrow(EntityNotFoundException.class);
		
		mockMvc
			.perform(get("/stock")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8"))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andReturn();
	}
	
	private String generateETag(Stock aStock) {
		return "\"" + eTagCreator.fromStock(aStock) + "\"";
	}
}
