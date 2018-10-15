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

import com.mitrais.carrot.models.BarnSetting;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;
import com.mitrais.carrot.repositories.BarnSettingRepository;
import com.mitrais.carrot.dummy.BarnDummy;

/**
 * Sharing service test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BarnSettingServiceTest {

    /**
     * Mock RoleRepository
     */
    @Mock
    private BarnSettingRepository barnSettingRepositoryMock;

    @InjectMocks
    private BarnSettingService barnService;

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
        when(barnSettingRepositoryMock.findAllBydeletedIsNull()).thenReturn(BarnDummy.listOfBarnSettings());
        Assert.assertNotNull(barnService.findAll());
    }

    /**
     * test find by id and returning data
     */
    @Test
    public void testFindById() {
        BarnSetting st = BarnDummy.barnSettingObject();
        when(barnSettingRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));

        // case 1 : should not null
        Assert.assertNotNull(barnService.findById(11));
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdNotFound() {
        when(barnSettingRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        Assert.assertFalse(barnSettingRepositoryMock.findById(11).isPresent());
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdThrowingException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Data", "id", 11);
        when(barnSettingRepositoryMock.findById(Mockito.anyInt())).thenThrow(exception);
        try {
            barnSettingRepositoryMock.findById(11);
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
        BarnSetting st = BarnDummy.barnSettingObject();
        when(barnSettingRepositoryMock.save(Mockito.any(BarnSetting.class))).thenReturn(st);
        Assert.assertNotNull(barnService.save(st));
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testUpdateShouldSuccess() {
        BarnSetting st = BarnDummy.barnSettingObject();
        when(barnSettingRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));
        when(barnSettingRepositoryMock.save(Mockito.any(BarnSetting.class))).thenReturn(st);

        BarnSetting b = barnService.update(st);
        Assert.assertNotNull(b);
        Assert.assertEquals(b.getName(), st.getName());
    }

    /**
     * test update with not successfully because throwing exception when find
     * data by id
     *
     */
    @Test
    public void testUpdateThrowingException() {
        BarnSetting st = BarnDummy.barnSettingObject();
        when(barnSettingRepositoryMock.findById(Mockito.anyInt()))
                .thenThrow(new ResourceNotFoundException("Data", "id", 111));

        BarnSetting barnU = null;
        try {
            barnU = barnService.update(st);
        } catch (Exception e) {
            Assert.assertNull(barnU);
            Assert.assertEquals("Data not found with id : '111'", e.getMessage());
        }
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testDeleteShouldSuccess() {
        BarnSetting st = BarnDummy.barnSettingObject();
        when(barnSettingRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));
        when(barnSettingRepositoryMock.save(Mockito.any(BarnSetting.class))).thenReturn(st);

        BarnSetting barnU = barnService.delete(111);
        Assert.assertNotNull(barnU);
        Assert.assertEquals(Boolean.TRUE, barnU.getDeleted());
    }

    /**
     * test delete with not successfully because throwing exception when find
     * data by id
     *
     */
    @Test
    public void testDeleteThrowingException() {
        when(barnSettingRepositoryMock.findById(Mockito.anyInt()))
                .thenThrow(new ResourceNotFoundException("Data", "id", 111));

        BarnSetting barnU = null;
        try {
            barnU = barnService.delete(111);
        } catch (Exception e) {
            Assert.assertNull(barnU);
            Assert.assertEquals("Data not found with id : '111'", e.getMessage());
        }
    }
}
