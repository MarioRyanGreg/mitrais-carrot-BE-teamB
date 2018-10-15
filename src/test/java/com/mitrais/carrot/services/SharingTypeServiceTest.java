package com.mitrais.carrot.services;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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

import com.mitrais.carrot.models.ShareType;
import com.mitrais.carrot.services.SharingTypeService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;
import com.mitrais.carrot.repositories.ShareTypeRepository;

import com.mitrais.carrot.dummy.SharingTypeDummy;

/**
 * Sharing service test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(MockitoJUnitRunner.class)
public class SharingTypeServiceTest {

    /**
     * Mock RoleRepository
     */
    @Mock
    private ShareTypeRepository shareTypeRepositoryMock;

    @InjectMocks
    private SharingTypeService shareTypeService;

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
        ShareType st = SharingTypeDummy.shareTypeObject();
        when(shareTypeRepositoryMock.findAllBydeletedIsNull())
                .thenReturn(Arrays.asList(new ShareType[]{st, st, st}));
        Assert.assertNotNull(shareTypeService.findAll());
    }

    /**
     * test find by id and returning data
     */
    @Test
    public void testFindById() {
        ShareType st = SharingTypeDummy.shareTypeObject();
        when(shareTypeRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));

        // case 1 : should not null
        Assert.assertNotNull(shareTypeService.findById(11));

        // case 2 : should be has equal sharing type value
        Assert.assertEquals(shareTypeService.findById(11).getSharingType(), st.getSharingType());
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdNotFound() {
        when(shareTypeRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        Assert.assertFalse(shareTypeRepositoryMock.findById(11).isPresent());
    }

    /**
     * test find by id should be null
     *
     */
    @Test
    public void testFindByIdThrowingException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Data", "id", 11);
        when(shareTypeRepositoryMock.findById(Mockito.anyInt())).thenThrow(exception);
        try {
            shareTypeRepositoryMock.findById(11);
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
        ShareType st = SharingTypeDummy.shareTypeObject();
        when(shareTypeRepositoryMock.save(Mockito.any(ShareType.class))).thenReturn(st);
        Assert.assertNotNull(shareTypeService.save(st));
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testUpdateShouldSuccess() {
        ShareType st = SharingTypeDummy.shareTypeObject();
        when(shareTypeRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));
        when(shareTypeRepositoryMock.save(Mockito.any(ShareType.class))).thenReturn(st);

        ShareType stupdate = shareTypeService.update(111, st);
        Assert.assertNotNull(stupdate);
        Assert.assertEquals(stupdate.getSharingType(), st.getSharingType());
    }

    /**
     * test update with not successfully because throwing exception when find
     * data by id
     *
     */
    @Test
    public void testUpdateThrowingException() {
        ShareType st = SharingTypeDummy.shareTypeObject();
        when(shareTypeRepositoryMock.findById(Mockito.anyInt()))
                .thenThrow(new ResourceNotFoundException("Data", "id", 111));

        ShareType stupdate = null;
        try {
            stupdate = shareTypeService.update(111, st);
        } catch (Exception e) {
        	Assert.assertNull(stupdate);
            Assert.assertThat(e.getMessage(), is("Data not found with id : '111'"));
        }
    }

    /**
     * test save with successfully case
     *
     */
    @Test
    public void testDeleteShouldSuccess() {
        ShareType st = SharingTypeDummy.shareTypeObject();
        when(shareTypeRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(st));
        when(shareTypeRepositoryMock.save(Mockito.any(ShareType.class))).thenReturn(st);

        ShareType stupdate = shareTypeService.delete(111);
        Assert.assertNotNull(stupdate);
        Assert.assertEquals(Boolean.TRUE, stupdate.getDeleted());
    }

    /**
     * test delete with not successfully because throwing exception when find
     * data by id
     *
     */
    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteThrowingException() {
        when(shareTypeRepositoryMock.findById(Mockito.anyInt()))
                .thenThrow(new ResourceNotFoundException("Data", "id", 111));
        shareTypeService.delete(111);
    }
}
