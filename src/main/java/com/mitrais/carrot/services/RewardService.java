package com.mitrais.carrot.services;

import com.mitrais.carrot.services.interfaces.IRewardService;
import com.mitrais.carrot.models.Rewards;
import com.mitrais.carrot.repositories.RewardsRepository;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Class for Maintain service of Reward
 *
 * @author Rudy
 *
 */
@Service
public class RewardService implements IRewardService {

    private RewardsRepository rewardsRepository;

    /**
     * RewardService Constructor
     *
     * @param rewardsRepository RewardsRepository
     *
     */
    public RewardService(@Autowired RewardsRepository rewardsRepository) {
        this.rewardsRepository = rewardsRepository;
    }

    /**
     * get all data
     *
     * @return Iterable
     */
    @Override
    public Iterable<Rewards> all() {
        return rewardsRepository.findAllBydeletedIsFalse();
    }

    /**
     * create new data
     *
     * @param body Rewards
     * @return Rewards
     */
    @Override
    public Rewards save(Rewards body) {
        return rewardsRepository.save(body);
    }

    /**
     * get detail by id
     *
     * @param id Integer
     * @return Optional
     */
    @Override
    public Optional<Rewards> findById(Integer id) {
        return rewardsRepository.findById(id);
    }

    /**
     * update data
     *
     * @param oriReward Rewards
     * @param reward Rewards
     * @return Rewards
     */
    @Override
    public Rewards update(Rewards oriReward, Rewards reward) {
        BeanUtils.copyProperties(reward, oriReward);
        if (reward.getDeleted() == null) {
            reward.setDeleted(false);
        }

        return rewardsRepository.save(oriReward);
    }

    /**
     * action delete to set is deleted = 1
     *
     * @param reward Rewards
     */
    @Override
    public Rewards delete(Rewards reward) {
        reward.setDeleted(true);
        return rewardsRepository.save(reward);
    }
}
