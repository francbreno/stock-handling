package com.breno.projects.stockhandling.statistics.adapter.in.api;

import java.time.Instant;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breno.projects.stockhandling.statistics.adapter.in.api.response.GetStatisticsResponse;
import com.breno.projects.stockhandling.statistics.application.port.in.LoadStatisticsQuery;
import com.breno.projects.stockhandling.statistics.application.port.in.StatisticsRangeFilterType;
import com.breno.projects.stockhandling.statistics.application.port.in.TopStatistics;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class GetStatisticsController {
	private final LoadStatisticsQuery loadStatisticsQuery;

	@GetMapping
	public ResponseEntity<GetStatisticsResponse> getStatistics(
			@Valid @RequestParam("time") String range) {

		Instant requestTimestamp = Instant.now();
		StatisticsRangeFilterType rangeFilter = StatisticsRangeFilterType.of(range);
		
		TopStatistics topStatistics =
			loadStatisticsQuery.loadStatistics(rangeFilter, requestTimestamp);
			
		GetStatisticsResponse getStatisticsResponse =
				mapToResponse(range, requestTimestamp, topStatistics);

		return ResponseEntity.ok()
				.body(getStatisticsResponse);
	}

	private GetStatisticsResponse mapToResponse(
			String range, Instant requestTimestamp, TopStatistics topStatistics) {

		return GetStatisticsResponse.builder()
				.range(range)
				.requestTimestamp(requestTimestamp)
				.topAvailableProducts(topStatistics.getTopAvailableProducts())
				.topSellingProducts(topStatistics.getTopSellingProducts())
				.build();
	}
}
