package com.mitrais.carrot.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mitrais.carrot.models.SharingLevelMongo;

public interface SharingLevelMongoRepository extends MongoRepository<SharingLevelMongo, Integer> {

	public SharingLevelMongo findBySharingLevel(Integer SharingLevel);
}
