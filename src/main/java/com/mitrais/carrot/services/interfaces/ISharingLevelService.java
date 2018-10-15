package com.mitrais.carrot.services.interfaces;

import java.util.Optional;

import com.mitrais.carrot.models.SharingLevel;

/**
 * SharingLevel Service as Interface for accessing JPA Repository of
 * SharingLevel
 * 
 * @author Medianto
 *
 */
public interface ISharingLevelService {

	/**
	 * Save or create new Sharing Level
	 * 
	 * @param sharingLevel the entity of SharingLevel
	 * @return SharingLevel newly created SharingLevel
	 * 
	 */
	SharingLevel save(SharingLevel sharingLevel);

	/**
	 * Search Sharing Level by Id
	 * 
	 * 
	 * @param id the SharingLevel id
	 * @return SharingLevel Optional matched SharingLevel record from database
	 */
	Optional<SharingLevel> findById(Integer id);

	/**
	 * Delete Sharing Level entity
	 * 
	 * 
	 * @param sharingLevel the SharingLevel entity to be updated
	 */
	void delete(SharingLevel sharingLevel);

	/**
	 * Retrieve All Sharing Level
	 * 
	 * 
	 * @return SharingLevel Iterable all SharingLevel record from database
	 */
	Iterable<SharingLevel> findAll();

	/**
	 * 
	 * find all data by isdeleted value
	 *
	 * @param isdeleted the isdeleted condition
	 * @return SharingLevel Iterable all matched SharingLevel from database
	 */
	Iterable<SharingLevel> findBydeletedIn(Integer isdeleted);

	/**
	 * find all data by isdeleted null
	 *
	 * @return SharingLevel Iterable all matched SharingLevel from database
	 */
	Iterable<SharingLevel> findAllBydeletedIsNull();

}
