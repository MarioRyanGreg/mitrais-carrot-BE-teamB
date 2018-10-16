package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.SharingLevelMongo;

public interface ISharingLevelMongoService {

	SharingLevelMongo save(SharingLevelMongo sharingLevelMongo);

	void delete(SharingLevelMongo sharingLevelMongo);

	SharingLevelMongo findBySharingLevel(Integer SharingLevel);
}
