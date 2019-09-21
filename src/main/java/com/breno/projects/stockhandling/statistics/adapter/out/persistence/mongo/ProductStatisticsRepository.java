package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductStatisticsRepository extends MongoRepository<ProductStatisticsDocument, String> {

}
