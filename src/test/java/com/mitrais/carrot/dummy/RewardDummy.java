package com.mitrais.carrot.dummy;

import java.util.Arrays;

import com.mitrais.carrot.models.Rewards;
import com.mitrais.carrot.models.RewardsStatusEnum;

/**
 * dummy class of Rewards data dummy
 *
 * @author Febri_MW251
 *
 */
public class RewardDummy {

    /**
     * dummy Reward data
     *
     * @return Rewards object
     */
    public static Rewards rewardObject() {
        Rewards reward = new Rewards();
        reward.setId(1);
        reward.setTypeName("Bazaar");
        reward.setRewardTypeName("OK");
        reward.setStatus(RewardsStatusEnum.OPEN);
        reward.setSharingLevel(1);
        reward.setMaxClaim(10);
        reward.setEvent("End Year");
        reward.setCarrot(20);
        reward.setExpiredDate(DateDummy.myLocalDateTime(null));
        reward.setDeleted(Boolean.FALSE);
        reward.setCreatedBy(1);
        reward.setCreatedTime(DateDummy.myLocalDateTime(null));
        return reward;
    }

    /**
     * list of Rewards dummy object
     *
     * @return Iterable Rewards object
     */
    public static Iterable<Rewards> listOfRewards() {
        Rewards reward = RewardDummy.rewardObject();
        return Arrays.asList(new Rewards[]{reward, reward, reward, reward});
    }

    /**
     * request body with Rewards
     *
     * @return Rewards object
     */
    public static Rewards rewardRequestBody() {
    	Rewards reward = new Rewards();
        reward.setTypeName("Bazaar");
        reward.setRewardTypeName("OK");
        reward.setStatus(RewardsStatusEnum.OPEN);
        reward.setMaxClaim(10);
        reward.setEvent("End Year");
        reward.setCarrot(20);
        reward.setSharingLevel(1);
        return reward;
    }
}
