package com.mitrais.carrot.repositories;

import com.mitrais.carrot.models.Bazaar;
import org.springframework.data.repository.CrudRepository;

/**
 * BazaarRepository is interface repository layer for Bazaar entity
 * 
 * @author Ryan Mario
 *
 */
public interface BazaarRepository extends CrudRepository<Bazaar, Integer> {

	/**
	 * find all data by isdeleted value
	 *
	 * @param isdeleted the isdeleted condition
	 * @return Bazaar Iterable all matched Bazaar from database
	 */
    public Iterable<Bazaar> findBydeletedIn(Integer isdeleted);

    /**
	 * find all data by isdeleted null
	 *
	 * @return Bazaar Iterable all matched Bazaar from database
	 */
    public Iterable<Bazaar> findAllBydeletedIsNull();
}
