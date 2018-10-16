package com.mitrais.carrot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.SharingLevelMongo;
import com.mitrais.carrot.repositories.SharingLevelMongoRepository;
import com.mitrais.carrot.services.interfaces.ISharingLevelMongoService;

@Service
public class SharingLevelMongoService implements ISharingLevelMongoService {

	private SharingLevelMongoRepository sharingLevelMongoRepository;

	public SharingLevelMongoService(@Autowired SharingLevelMongoRepository sharingLevelMongoRepository) {
		this.sharingLevelMongoRepository = sharingLevelMongoRepository;
	}

	public SharingLevelMongo save(SharingLevelMongo sharingLevelMongo) {
		return sharingLevelMongoRepository.save(sharingLevelMongo);
	}

	public void delete(SharingLevelMongo sharingLevelMongo) {
		sharingLevelMongoRepository.delete(sharingLevelMongo);
	}

	public Iterable<SharingLevelMongo> findAll() {
		Iterable<SharingLevelMongo> sharingLevelMongos = sharingLevelMongoRepository.findAll();
		return sharingLevelMongos;
	}

	public SharingLevelMongo findBySharingLevel(Integer SharingLevel) {
		
		return sharingLevelMongoRepository.findBySharingLevel(SharingLevel);
	}
}
