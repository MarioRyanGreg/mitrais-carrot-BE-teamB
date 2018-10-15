package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.Barn;

/**
 * Barn Service as Interface for accessing JPA Repository of Barn
 *
 * @author Deta_M557
 * @author Febri_MW251
 */
public interface IBarn {

    /**
     * find all Barn
     *
     * @return List of barn data
     */
    public Iterable<Barn> findAll();

    /**
     * find barn by id
     *
     * @param id pk of Barn
     * @return Barn Object
     */
    public Barn findById(Integer id);

    /**
     * save barn data into database
     *
     * @param barn Object barn that want you update
     * @return Barn Object
     */
    public Barn save(Barn barn);

    /**
     * update barn data
     *
     * @param barn Barn object that will be update
     * @return Barn Object
     */
    public Barn update(Barn barn);

    /**
     * Delete Barn by id
     *
     * @param id pk of barn
     * @return Barn Object
     */
    public Barn delete(Integer id);
}
