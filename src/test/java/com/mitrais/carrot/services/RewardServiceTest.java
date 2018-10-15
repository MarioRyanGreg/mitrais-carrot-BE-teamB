package com.mitrais.carrot.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mitrais.carrot.models.Rewards;
import com.mitrais.carrot.models.RewardsStatusEnum;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;
import com.mitrais.carrot.repositories.RewardsRepository;
import com.mitrais.carrot.dummy.RewardDummy;

/**
 * Reward Service test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(MockitoJUnitRunner.class)
public class RewardServiceTest {

    /**
     * Mock RewardsRepository
     */
    @Mock
    private RewardsRepository rewardRepositoryMock;

    @InjectMocks
    private RewardService rewardService;

    /**
     * run setup method to set the initial value
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * test find all
     */
    @Test
    public void testFindAllShouldReturnValueAndNotEmpty() {
        when(rewardRepositoryMock.findAllBydeletedIsFalse()).thenReturn(RewardDummy.listOfRewards());
        Assert.assertNotNull(rewardService.all());
    }

    /**
     * test find by id and returning data
     */
    @Test
    public void testFindById() {
        Rewards st = RewardDummy.rewardObject();
        when(rewardRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));

        // case 1 : should not null
        Assert.assertNotNull(rewardService.findById(11));
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdNotFound() {
        when(rewardRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        Assert.assertFalse(rewardRepositoryMock.findById(11).isPresent());
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdThrowingException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Data", "id", 11);
        when(rewardRepositoryMock.findById(Mockito.anyInt())).thenThrow(exception);
        try {
            rewardRepositoryMock.findById(11);
        } catch (Exception e) {
            Assert.assertEquals("Data not found with id : '11'", e.getMessage());
            Assert.assertEquals(exception, e);
        }
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testSaveShouldSuccess() {
        Rewards st = RewardDummy.rewardObject();
        when(rewardRepositoryMock.save(Mockito.any(Rewards.class))).thenReturn(st);
        Assert.assertNotNull(rewardService.save(st));
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testUpdateShouldSuccess() {
        Rewards rwOri = RewardDummy.rewardObject();
        Rewards rwUpd = RewardDummy.rewardObject();
        rwUpd.setStatus(RewardsStatusEnum.CLOSE);

        when(rewardRepositoryMock.save(Mockito.any(Rewards.class))).thenReturn(rwUpd);

        Rewards rwRslt = rewardService.update(rwOri, rwUpd);
        Assert.assertNotNull(rwRslt);
        Assert.assertEquals(RewardsStatusEnum.CLOSE, rwRslt.getStatus());
        Assert.assertEquals(rwUpd.getDeleted(), rwRslt.getDeleted());
    }

    /**
     * test update with not successfully because throwing exception when find
     * data by id
     *
     */
    @Test
    public void testUpdateThrowingException() {
        Rewards rwd = RewardDummy.rewardObject();
        when(rewardRepositoryMock.save(Mockito.any(Rewards.class)))
                .thenThrow(new ResourceNotFoundException("Data", "id", 111));

        Rewards rwRslt = null;
        try {
            rwRslt = rewardService.update(rwd, rwd);
        } catch (Exception e) {
            Assert.assertNull(rwRslt);
            Assert.assertEquals("Data not found with id : '111'", e.getMessage());
        }
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testDeleteShouldSuccess() {
        Rewards rwd = RewardDummy.rewardObject();
        when(rewardRepositoryMock.save(Mockito.any(Rewards.class))).thenReturn(rwd);

        Rewards rwRslt = rewardService.delete(rwd);
        Assert.assertNotNull(rwRslt);
        Assert.assertEquals(Boolean.TRUE, rwRslt.getDeleted());
    }

    /**
     * test delete with not successfully because throwing exception when save
     *
     */
    @Test
    public void testDeleteThrowingException() {
        Rewards rwd = RewardDummy.rewardObject();
        when(rewardRepositoryMock.save(Mockito.any(Rewards.class)))
                .thenThrow(new ResourceNotFoundException("Data", "id", 111));

        Rewards rwRslt = null;
        try {
            rwRslt = rewardService.delete(rwd);
        } catch (Exception e) {
            Assert.assertNull(rwRslt);
            Assert.assertEquals("Data not found with id : '111'", e.getMessage());
        }
    }
}
