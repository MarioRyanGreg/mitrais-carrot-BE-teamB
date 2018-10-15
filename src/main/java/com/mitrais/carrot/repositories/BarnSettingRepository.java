package com.mitrais.carrot.repositories;

import com.mitrais.carrot.models.BarnSetting;
import org.springframework.data.repository.CrudRepository;

/**
 * barn setting repository
 */
public interface BarnSettingRepository extends CrudRepository<BarnSetting, Integer> {

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted	The delete status of BarnSetting
     * @return	all data with deleted condition
     */
    public Iterable<BarnSetting> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     * @return  All data with deleted value Null
     */
    public Iterable<BarnSetting> findAllBydeletedIsNull();
    
}
