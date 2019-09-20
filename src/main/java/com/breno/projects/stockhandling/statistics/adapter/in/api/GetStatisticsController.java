package com.breno.projects.stockhandling.statistics.adapter.in.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.breno.projects.stockhandling.statistics.adapter.in.api.response.GetStatisticsResponse;

@RestController
@RequestMapping("/statistics")
public class GetStatisticsController {

	public ResponseEntity<GetStatisticsResponse> getStatistics(
			@Valid @RequestParam("time") String range) {

		return null;
	}
}
