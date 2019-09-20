package com.breno.projects.stockhandling.shared.adapter.out.events;

import java.util.Map;
import java.util.function.Function;

import com.breno.projects.stockhandling.shared.application.out.StockUpdatedEvent;
import com.breno.projects.stockhandling.shared.application.out.StockUpdatedEventObservable;

import lombok.RequiredArgsConstructor;

/**
 * This class implements an Observer interface which defines how to notify
 * different bounded contexts about stock updates.
 * 
 * <p>The observable value isn't keep here. So, this class also acts as a
 * Mediator between the event source (@{UpdateStockService UpdateStockService}) and
 * others services from different bounded contexts.
 * 
 * <p>It also uses a function as listener to simplify the implementation.
 * 
 * @author breno
 *
 */
@RequiredArgsConstructor
public class StockUpdatedEventHandler implements StockUpdatedEventObservable {
	private final Map<String, Function<StockUpdatedEvent, Void>> listeners;

	@Override
	public boolean register(String id, Function<StockUpdatedEvent, Void> handler) {
		return listeners.putIfAbsent(id, handler) != null;
	}
	
	@Override
	public void update(StockUpdatedEvent event) {
		notifyAll(event);
	}
	
	@Override
	public void notifyAll(StockUpdatedEvent event) {
		this.listeners.values().stream().forEach(handler -> handler.apply(event));
	}
}
