package com.mitrais.carrot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.BazaarItem;
import com.mitrais.carrot.repositories.BazaarItemRepository;
import com.mitrais.carrot.services.interfaces.IBazaarItemService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

/**
 * Service Implementation Class for maintain Service of BazaarItem
 * 
 * @author Ryan Mario
 *
 */
@Service
public class BazaarItemService implements IBazaarItemService {

	/**
	 * bazaarItem repository
	 */
	private BazaarItemRepository bazaarItemsRepository;

	/**
	 * the bazaarItem service constructor
	 * 
	 * @param bazaarItemsRepository the repository for BazaarItem entity
	 */
	public BazaarItemService(@Autowired BazaarItemRepository bazaarItemsRepository) {
		this.bazaarItemsRepository = bazaarItemsRepository;
	}

	/**
	 * findAll will return a Iterable of bazaarItem
	 * 
	 * @return BazaarItem Iterable all BazaarItem record from database
	 */
	public Iterable<BazaarItem> findAll() {
		return bazaarItemsRepository.findAllBydeletedIsNull();
	}
	
	/**
	 * find bazaarItem by id
	 * 
	 * @param id the bazaarItem id
	 * @return BazaarItem matched BazaarItem record from database
	 */
	public BazaarItem findById(Integer id) {
		return bazaarItemsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
	}

	/**
	 * insert new BazaarItem
	 * 
	 * @param bazaarItem the entity of BazaarItem
	 * @return bazaarItem newly created BazaarItem
	 */
	public BazaarItem save(BazaarItem bazaarItem) {
		return bazaarItemsRepository.save(bazaarItem);
	}
	
	/**
	 * update bazaarItem
	 * 
	 * @param bazaarItem the updated BazaarItem
	 * @return BazaarItem the updated BazaarItem
	 */
	public BazaarItem update(Integer id, BazaarItem bazaarItem) {

		BazaarItem model = bazaarItemsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		BeanUtils.copyProperties(bazaarItem, model);

		model.setId(id);
		return bazaarItemsRepository.save(model);
	}
	
	/**
	 * updating BazaarItem.isdeleted to true
	 * 
	 * @param id the BazaarItem id to be updated
	 * @return BazaarItem the updated BazaarItem
	 */
	public BazaarItem delete(Integer id) {
		BazaarItem bI = bazaarItemsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		bI.setDeleted(true);
		return bazaarItemsRepository.save(bI);
	}
}
