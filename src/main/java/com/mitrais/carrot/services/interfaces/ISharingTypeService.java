package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.ShareType;

/**
 * SharingType Service as Interface for accessing JPA Repository of SharingType
 * 
 * @author Medianto
 *
 */
public interface ISharingTypeService {
	/**
	 * findAll will return a Iterable of shareType
	 * 
	 * @return ShareType Iterable all ShareType record from database
	 */
	public Iterable<ShareType> findAll();

	/**
	 * find shareType by id
	 * 
	 * @param id the shareType id
	 * @return ShareType matched ShareType record from database
	 */
	public ShareType findById(Integer id);

	/**
	 * insert new ShareType
	 * 
	 * @param shareType the entity of ShareType
	 * @return shareType newly created ShareType
	 */
	public ShareType save(ShareType shareType);

	/**
	 * update shareType
	 * 
	 * @param id        the shareType id
	 * @param shareType the updated ShareType
	 * @return ShareType the updated ShareType
	 */
	public ShareType update(Integer id, ShareType shareType);

	/**
	 * updating ShareType.isdeleted to true
	 * 
	 * @param id the ShareType id to be updated
	 * @return ShareType the updated ShareType
	 */
	public ShareType delete(Integer id);
}
