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

import com.mitrais.carrot.models.Barn;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;
import com.mitrais.carrot.repositories.BarnRepository;
import com.mitrais.carrot.dummy.BarnDummy;

/**
 * Barn Service test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(MockitoJUnitRunner.class)
public class BarnServiceTest {

    /**
     * Mock RoleRepository
     */
    @Mock
    private BarnRepository barnRepositoryMock;

    @InjectMocks
    private BarnService barnService;

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
        when(barnRepositoryMock.findAllBydeletedIsNull()).thenReturn(BarnDummy.listOfBarns());
        Assert.assertNotNull(barnService.findAll());
    }

    /**
     * test find by id and returning data
     */
    @Test
    public void testFindById() {
        Barn st = BarnDummy.barnObject();
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));

        // case 1 : should not null
        Assert.assertNotNull(barnService.findById(11));
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdNotFound() {
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        Assert.assertFalse(barnRepositoryMock.findById(11).isPresent());
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdThrowingException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Data", "id", 11);
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenThrow(exception);
        try {
            barnRepositoryMock.findById(11);
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
        Barn st = BarnDummy.barnObject();
        when(barnRepositoryMock.save(Mockito.any(Barn.class))).thenReturn(st);
        Assert.assertNotNull(barnService.save(st));
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testUpdateShouldSuccess() {
        Barn st = BarnDummy.barnObject();
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));
        when(barnRepositoryMock.save(Mockito.any(Barn.class))).thenReturn(st);

        Barn b = barnService.update(st);
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
        Barn st = BarnDummy.barnObject();
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenThrow(new ResourceNotFoundException("Data", "id", 111));

        Barn barnU = null;
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
        Barn st = BarnDummy.barnObject();
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));
        when(barnRepositoryMock.save(Mockito.any(Barn.class))).thenReturn(st);

        Barn barnU = barnService.delete(111);
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
        when(barnRepositoryMock.findById(Mockito.anyInt())).thenThrow(new ResourceNotFoundException("Data", "id", 111));

        Barn barnU = null;
        try {
            barnU = barnService.delete(111);
        } catch (Exception e) {
            Assert.assertNull(barnU);
            Assert.assertEquals("Data not found with id : '111'", e.getMessage());
        }
    }
}
