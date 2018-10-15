package com.mitrais.carrot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mitrais.carrot.models.ShareType;

/**
 * ShareTypeRepository is interface repository layer for ShareType entity
 * 
 * @author Medianto
 *
 */
@Repository("shareTypeRepository")
public interface ShareTypeRepository extends CrudRepository<ShareType, Integer> {

	/**
	 * find all data by isdeleted value
	 *
	 * @param isdeleted the isdeleted condition
	 * @return ShareType Iterable all matched ShareType from database
	 */
	public Iterable<ShareType> findBydeletedIn(Integer isdeleted);

	/**
	 * find all data by isdeleted null
	 *
	 * @return ShareType Iterable all matched ShareType from database
	 */
	public Iterable<ShareType> findAllBydeletedIsNull();
}
