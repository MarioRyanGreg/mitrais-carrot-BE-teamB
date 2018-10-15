package com.mitrais.carrot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.SharingLevel;
import com.mitrais.carrot.repositories.SharingLevelRepository;
import com.mitrais.carrot.services.interfaces.ISharingLevelService;

/**
 * Service Implementation Class for maintain Service of SharingLevel
 * 
 * @author Medianto
 *
 */
@Service
public class SharingLevelService implements ISharingLevelService {
	/**
	 * SharingLevel repository
	 */
	private SharingLevelRepository sharingLevelRepository;

	/**
	 * the sharingLevelService service constructor
	 * 
	 * @param sharingLevelRepository the repository for sharingLevel entity
	 */
	public SharingLevelService(@Autowired SharingLevelRepository sharingLevelRepository) {
		this.sharingLevelRepository = sharingLevelRepository;
	}

	/**
	 * Save or create new Sharing Level
	 * 
	 * @param sharingLevel the entity of SharingLevel
	 * @return SharingLevel newly created SharingLevel
	 * 
	 */
	public SharingLevel save(SharingLevel sharingLevel) {
		return sharingLevelRepository.save(sharingLevel);
	}

	/**
	 * Delete Sharing Level entity
	 * 
	 * 
	 * @param sharingLevel the SharingLevel entity to be updated
	 */
	public void delete(SharingLevel sharingLevel) {
		sharingLevelRepository.delete(sharingLevel);

	}

	/**
	 * Search Sharing Level by Id
	 * 
	 * 
	 * @param id the SharingLevel id
	 * @return SharingLevel Optional matched SharingLevel record from database
	 */
	public Optional<SharingLevel> findById(Integer id) {
		return sharingLevelRepository.findById(id);
	}

	/**
	 * Retrieve All Sharing Level
	 * 
	 * 
	 * @return SharingLevel Iterable all SharingLevel record from database
	 */
	public Iterable<SharingLevel> findAll() {
		Iterable<SharingLevel> sharingLevels = sharingLevelRepository.findAll();
		return sharingLevels;
	}

	/**
	 * 
	 * find all data by isdeleted value
	 *
	 * @param isdeleted the isdeleted condition
	 * @return SharingLevel Iterable all matched SharingLevel from database
	 */
	public Iterable<SharingLevel> findBydeletedIn(Integer isdeleted) {
		Iterable<SharingLevel> sharingLevels = sharingLevelRepository.findBydeletedIn(isdeleted);
		return sharingLevels;
	}

	/**
	 * find all data by isdeleted null
	 *
	 * @return SharingLevel Iterable all matched SharingLevel from database
	 */
	public Iterable<SharingLevel> findAllBydeletedIsNull() {
		return sharingLevelRepository.findAllBydeletedIsNull();

	}

}
