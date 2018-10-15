package com.mitrais.carrot.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mitrais.carrot.models.Barn;

/**
 * barn repository
 */
public interface BarnRepository extends CrudRepository<Barn, Integer> {

    /**
     * find all data by is deleted value condition
     * @param isdeleted	The delete status of Barn
     * @return  all barn data with criteria isDelete
     */
    public Iterable<Barn> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     * @return all data without deleted
     */
    public Iterable<Barn> findAllBydeletedIsNull();
}
