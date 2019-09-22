package com.breno.projects.stockhandling.statistics.adapter.out.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface for operations related to persistence of Statistics data of products on a 
 * MongoDB.
 * 
 * @author breno
 *
 */
public interface ProductStatisticsRepository extends MongoRepository<ProductStatisticsDocument, String> {

}
