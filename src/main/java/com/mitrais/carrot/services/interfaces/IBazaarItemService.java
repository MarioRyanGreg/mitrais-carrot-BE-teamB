package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.BazaarItem;

/**
 * BazaarItem Service as Interface for accessing JPA Repository of BazaarItem
 * 
 * @author Ryan Mario
 *
 */
public interface IBazaarItemService {
	
	/**
	 * findAll will return a Iterable of bazaarItem
	 * 
	 * @return BazaarItem Iterable all BazaarItem record from database
	 */
	public Iterable<BazaarItem> findAll();
	
	/**
	 * find bazaarItem by id
	 * 
	 * @param id the bazaarItem id
	 * @return BazaarItem matched BazaarItem record from database
	 */
	public BazaarItem findById(Integer id);
	
	/**
	 * insert new BazaarItem
	 * 
	 * @param bazaarItem the entity of BazaarItem
	 * @return bazaarItem newly created BazaarItem
	 */
	public BazaarItem save(BazaarItem bazaarItem);
	
	/**
	 * update bazaarItem
	 * 
	 * @param id        the bazaarItem id
	 * @param bazaarItem the updated BazaarItem
	 * @return BazaarItem the updated BazaarItem
	 */
	public BazaarItem update(Integer id, BazaarItem bazaarItem);
	
	/**
	 * updating BazaarItem.isdeleted to true
	 * 
	 * @param id the BazaarItem id to be updated
	 * @return BazaarItem the updated BazaarItem
	 */
    public BazaarItem delete(Integer id);
}