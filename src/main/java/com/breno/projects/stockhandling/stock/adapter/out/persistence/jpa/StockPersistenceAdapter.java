package com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa;

import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import com.breno.projects.stockhandling.stock.application.port.out.LoadStockPort;
import com.breno.projects.stockhandling.stock.application.port.out.UpdateStockStatePort;
import com.breno.projects.stockhandling.stock.model.Stock;

import lombok.RequiredArgsConstructor;

/**
 * This class represents a <em>driven adapter</em> responsible
 * for persisting operations using JPA.
 * 
 * @author breno
 *
 */
@Component
@RequiredArgsConstructor
public class StockPersistenceAdapter implements LoadStockPort, UpdateStockStatePort {
	
	private final StockRepository stockRepository;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Stock loadStock(String productId) {
		Objects.requireNonNull(productId);
		
		StockEntity stockEntity = stockRepository.findStockEntityByProductId(productId)
				.orElseThrow(EntityNotFoundException::new);

		return mapToDomain(stockEntity);
	}

	@Override
	public void update(Stock stock) {
		Objects.requireNonNull(stock);
		
		StockEntity stockEntity = mapToEntity(stock);
		stockRepository.save(stockEntity);
	}
	
	private StockEntity mapToEntity(Stock stock) {
		return StockEntity.builder()
				.id(stock.getId())
				.stockId(stock.getStockId())
				.productId(stock.getProductId())
				.timestamp(stock.getTimestamp())
				.quantity(stock.getQuantity())
				.version(stock.getVersion())
				.build();
	}
	
	private Stock mapToDomain(StockEntity stockEntity) {
		return Stock.builder()
				.id(stockEntity.getId())
				.stockId(stockEntity.getStockId())
				.productId(stockEntity.getProductId())
				.timestamp(stockEntity.getTimestamp())
				.quantity(stockEntity.getQuantity())
				.version(stockEntity.getVersion())
				.build();
	}

}
