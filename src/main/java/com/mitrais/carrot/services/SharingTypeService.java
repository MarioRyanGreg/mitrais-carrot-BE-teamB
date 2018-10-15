package com.mitrais.carrot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.ShareType;
import com.mitrais.carrot.repositories.ShareTypeRepository;
import com.mitrais.carrot.services.interfaces.ISharingTypeService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

/**
 * Service Implementation Class for maintain Service of SharingType
 * 
 * @author Medianto
 *
 */
@Service
public class SharingTypeService implements ISharingTypeService {
	/**
	 * shareType repository
	 */
	private ShareTypeRepository shareTypeRepository;

	/**
	 * the shareType service constructor
	 * 
	 * @param shareTypeRepository the repository for shareType entity
	 */
	public SharingTypeService(@Autowired ShareTypeRepository shareTypeRepository) {
		this.shareTypeRepository = shareTypeRepository;
	}

	/**
	 * findAll will return a Iterable of shareType
	 * 
	 * @return ShareType Iterable all ShareType record from database
	 */
	public Iterable<ShareType> findAll() {
		return shareTypeRepository.findAllBydeletedIsNull();
	}

	/**
	 * find shareType by id
	 * 
	 * @param id the shareType id
	 * @return ShareType matched ShareType record from database
	 */
	public ShareType findById(Integer id) {
		return shareTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
	}

	/**
	 * insert new ShareType
	 * 
	 * @param shareType the entity of ShareType
	 * @return shareType newly created ShareType
	 */
	public ShareType save(ShareType shareType) {
		return shareTypeRepository.save(shareType);
	}

	/**
	 * update shareType
	 * 
	 * @param shareType the updated ShareType
	 * @return ShareType the updated ShareType
	 */
	public ShareType update(Integer id, ShareType shareType) {

		ShareType model = shareTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		BeanUtils.copyProperties(shareType, model);

		model.setId(id);
		return shareTypeRepository.save(model);
	}

	/**
	 * updating ShareType.isdeleted to true
	 * 
	 * @param id the ShareType id to be updated
	 * @return ShareType the updated ShareType
	 */
	public ShareType delete(Integer id) {
		ShareType sT = shareTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		sT.setDeleted(true);
		return shareTypeRepository.save(sT);
	}
}
