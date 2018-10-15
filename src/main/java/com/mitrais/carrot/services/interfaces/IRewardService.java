package com.mitrais.carrot.services.interfaces;

import java.util.Optional;

import com.mitrais.carrot.models.Rewards;

/**
 * Reward Service as Interface for accessing JPA Repository of Reward
 *
 * @author Rudy
 *
 */
public interface IRewardService {

    /**
     *
     * @return Iterable
     */
    public Iterable<Rewards> all();

    /**
     * create new data
     *
     * @param body Rewards
     * @return Rewards
     */
    public Rewards save(Rewards body);

    /**
     * get detail by id
     *
     * @param id Integer
     * @return Optional
     */
    public Optional<Rewards> findById(Integer id);

    /**
     * update data
     *
     * @param oriReward Rewards
     * @param reward Rewards
     * @return Rewards
     */
    public Rewards update(Rewards oriReward, Rewards reward);

    /**
     * action delete to set is deleted = 1
     *
     * @param reward Rewards
     * @return
     */
    public Rewards delete(Rewards reward);
}
