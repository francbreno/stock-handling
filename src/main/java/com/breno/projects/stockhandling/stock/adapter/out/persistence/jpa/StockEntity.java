package com.breno.projects.stockhandling.stock.adapter.out.persistence.jpa;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "stock")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
public class StockEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private final Long id;
	
	private final String stockId;
	private final String productId;
	private final Integer quantity;
	private final Instant timestamp;
	
	@Version
	private Integer version;
}
