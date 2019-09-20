package com.breno.projects.stockhandling;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.breno.projects.stockhandling.shared.adapter.out.events.StockUpdatedEventHandler;
import com.breno.projects.stockhandling.shared.application.out.StockUpdatedEvent;
import com.breno.projects.stockhandling.shared.application.out.StockUpdatedEventObservable;
import com.breno.projects.stockhandling.stock.adapter.in.api.helpers.ETagStringCreator;
import com.breno.projects.stockhandling.stock.adapter.in.api.helpers.HashGenerator;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ETagStringCreator eTagStringCreator() throws NoSuchAlgorithmException {
		return new ETagStringCreator(new HashGenerator(HashGenerator.SHA_256));
	}
	
	@Bean
	public StockUpdatedEventObservable stockUpdateEventObservable() {
		return new StockUpdatedEventHandler(new HashMap<String, Function<StockUpdatedEvent, Void>>());
	}
}
