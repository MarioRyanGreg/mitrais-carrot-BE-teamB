package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.Bazaar;

/**
 * Bazaar Service as Interface for accessing JPA Repository of Bazaar
 * 
 * @author Ryan Mario
 *
 */
public interface IBazaarService {
	/**
	 * findAll will return a Iterable of bazaar
	 * 
	 * @return Bazaar Iterable all Bazaar record from database
	 */
	public Iterable<Bazaar> findAll();
	
	/**
	 * find bazaar by id
	 * 
	 * @param id the bazaar id
	 * @return Bazaar matched Bazaar record from database
	 */
	public Bazaar findById(Integer id);
	
	/**
	 * insert new Bazaar
	 * 
	 * @param bazaar the entity of Bazaar
	 * @return bazaar newly created Bazaar
	 */
	public Bazaar save(Bazaar bazaar);
	
	/**
	 * update bazaar
	 * 
	 * @param id        the bazaar id
	 * @param bazaar the updated Bazaar
	 * @return Bazaar the updated Bazaar
	 */
	public Bazaar update(Integer id, Bazaar bazaar);
	
	/**
	 * updating Bazaar.isdeleted to true
	 * 
	 * @param id the Bazaar id to be updated
	 * @return Bazaar the updated Bazaar
	 */
	public Bazaar delete(Integer id);
}