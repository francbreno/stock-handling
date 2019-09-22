package com.breno.projects.stockhandling;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockCommand;
import com.breno.projects.stockhandling.stock.application.port.in.UpdateStockUseCase;
import com.breno.projects.stockhandling.stock.application.port.out.UpdateStockStatePort;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

/**
 * Responsible for putting data into the databases during the bootstrap
 * of the applcaition.
 * 
 * @author breno
 *
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	
	private final UpdateStockUseCase updateStockUseCase;
	private final UpdateStockStatePort updateStockStatePort;

	@Override
	public void run(String... args) throws Exception {
		IntStream.range(0, 20)
			.forEach(i -> {
				String productId = "P" + i;
				String stockId = "S" + i;
				
				updateStockStatePort.update(Stock.builder()
						.productId(productId)
						.stockId(stockId)
						.build());
				
				IntStream.range(1, 10)
					.mapToObj(j -> {
						int quantity = ThreadLocalRandom.current().nextInt(0, 60);
						Instant timestamp = Instant.now().minus(quantity, ChronoUnit.DAYS);
	
						return UpdateStockCommand.builder()
								.id(stockId)
								.productId(productId)
								.quantity(quantity)
								.timestamp(timestamp)
								.build();
					})
					.forEach(updateStockUseCase::updateStock);
			});
	}
	
}
