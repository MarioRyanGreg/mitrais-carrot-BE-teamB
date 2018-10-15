package com.mitrais.carrot.repositories;

import com.mitrais.carrot.models.Rewards;
import org.springframework.data.repository.CrudRepository;

/**
 * Reward Repository
 * 
 * @author Rudy
 *
 */
public interface RewardsRepository extends CrudRepository<Rewards, Integer> {

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted Integer
     * @return Iterable
     */
    public Iterable<Rewards> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     *
     * @return Iterable
     */
    public Iterable<Rewards> findAllBydeletedIsFalse();
}
