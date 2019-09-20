package com.breno.projects.stockhandling;

import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa.StockEntity;
import com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa.StockRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	
	private final StockRepository stockRepository;

	@Override
	public void run(String... args) throws Exception {
		stockRepository.save(StockEntity.builder()
				.stockId("S1")
				.productId("P1")
				.quantity(10)
				.timestamp(Instant.now())
				.build());
	}
	
}
