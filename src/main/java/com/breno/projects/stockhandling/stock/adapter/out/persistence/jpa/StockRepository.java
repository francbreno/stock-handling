package com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A repository for persisting operations related to the
 * <em>Aggregate</em> Stock.
 * 
 * <p>Consider that there's only one Stock per Product.
 * 
 * @author breno
 *
 */
public interface StockRepository extends JpaRepository<StockEntity, String> {

	Optional<StockEntity> findStockEntityByProductId(String productId);
}
