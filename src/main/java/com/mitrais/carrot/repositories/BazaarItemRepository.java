package com.mitrais.carrot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mitrais.carrot.models.BazaarItem;

/**
 * BazaarItemRepository is interface repository layer for BazaarItem entity
 * 
 * @author Ryan Mario
 *
 */
public interface BazaarItemRepository extends CrudRepository<BazaarItem, Integer> {

	/**
	 * find all data by isdeleted value
	 *
	 * @param isdeleted the isdeleted condition
	 * @return BazaarItem Iterable all matched BazaarItem from database
	 */
    public Iterable<BazaarItem> findBydeletedIn(Integer isdeleted);

    /**
	 * find all data by isdeleted null
	 *
	 * @return BazaarItem Iterable all matched BazaarItem from database
	 */
    public Iterable<BazaarItem> findAllBydeletedIsNull();
}
