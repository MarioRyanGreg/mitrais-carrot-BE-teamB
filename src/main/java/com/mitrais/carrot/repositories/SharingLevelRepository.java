package com.mitrais.carrot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mitrais.carrot.models.SharingLevel;

/**
 * SharingLevelRepository is interface repository layer for SharingLevel entity
 * 
 * @author Medianto
 *
 */
public interface SharingLevelRepository extends CrudRepository<SharingLevel, Integer> {

	/**
	 * find all data by isdeleted value
	 *
	 * @param isdeleted the isdeleted condition
	 * @return SharingLevel Iterable all matched SharingLevel from database
	 */
	public Iterable<SharingLevel> findBydeletedIn(Integer isdeleted);

	/**
	 * find all data by isdeleted null
	 *
	 * @return SharingLevel Iterable all matched SharingLevel from database
	 */
	public Iterable<SharingLevel> findAllBydeletedIsNull();

}
