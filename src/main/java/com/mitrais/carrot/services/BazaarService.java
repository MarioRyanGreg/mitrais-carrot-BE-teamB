package com.mitrais.carrot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.Bazaar;
import com.mitrais.carrot.repositories.BazaarRepository;
import com.mitrais.carrot.services.interfaces.IBazaarService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

/**
 * Service Implementation Class for maintain Service of Bazaar
 * 
 * @author Ryan Mario
 *
 */
@Service
public class BazaarService implements IBazaarService {
	/**
	 * bazaar repository
	 */
	private BazaarRepository bazaarRepository;
	
	/**
	 * the bazaar service constructor
	 * 
	 * @param bazaarRepository the repository for Bazaar entity
	 */
	public BazaarService(@Autowired BazaarRepository bazaarRepository) {
		this.bazaarRepository = bazaarRepository;
	}

	/**
	 * findAll will return a Iterable of bazaar
	 * 
	 * @return Bazaar Iterable all Bazaar record from database
	 */
	public Iterable<Bazaar> findAll() {
		return bazaarRepository.findAllBydeletedIsNull();
	}

	/**
	 * find bazaar by id
	 * 
	 * @param id the bazaar id
	 * @return Bazaar matched Bazaar record from database
	 */
	public Bazaar findById(Integer id) {
		return bazaarRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
	}

	/**
	 * insert new Bazaar
	 * 
	 * @param bazaar the entity of Bazaar
	 * @return bazaar newly created Bazaar
	 */
	public Bazaar save(Bazaar bazaar) {
		return bazaarRepository.save(bazaar);
	}

	/**
	 * update bazaar
	 * 
	 * @param bazaar the updated Bazaar
	 * @return Bazaar the updated Bazaar
	 */
	public Bazaar update(Integer id, Bazaar bazaar) {

		Bazaar model = bazaarRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		BeanUtils.copyProperties(bazaar, model);

		model.setId(id);
		return bazaarRepository.save(model);
	}

	/**
	 * updating Bazaar.isdeleted to true
	 * 
	 * @param id the Bazaar id to be updated
	 * @return Bazaar the updated Bazaar
	 */
	public Bazaar delete(Integer id) {
		Bazaar b = bazaarRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		b.setDeleted(true);
		return bazaarRepository.save(b);
	}
}
