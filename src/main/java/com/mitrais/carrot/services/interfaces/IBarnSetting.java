package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.BarnSetting;

/**
 * Barn Setting interface for Barn Setting Service
 *
 * @author Deta_M557
 * @author Febri_MW251
 */
public interface IBarnSetting {

    /**
     * barn setting find all
     *
     * @return List of BarnSetting object
     */
    public Iterable<BarnSetting> findAll();

    /**
     * find barn setting by id pk
     *
     * @param id pk of barn setting
     * @return BarnSetting Object
     */
    public BarnSetting findById(Integer id);

    /**
     * Save new barn data into database
     *
     * @param barnSetting The BarnSetting object to be save
     * @return returning BarnSetting object that successfully save
     */
    public BarnSetting save(BarnSetting barnSetting);

    /**
     * update barn setting data
     *
     * @param barnSetting The Barn Setting object to be save
     * @return returning Barn Setting object data that already update
     */
    public BarnSetting update(BarnSetting barnSetting);

    /**
     * set delete by id
     *
     * @param id pk of Barn Setting object
     * @return Object of BarnSetting
     */
    public BarnSetting delete(Integer id);
}
