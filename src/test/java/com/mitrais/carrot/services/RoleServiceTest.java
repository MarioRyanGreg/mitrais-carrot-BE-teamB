package com.mitrais.carrot.services;

import com.mitrais.carrot.dummy.DateDummy;
import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.repositories.RoleRepository;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Before;
import org.junit.Test;

/**
 * Role service test
 *
 * @author Febri_MW251
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    /**
     * Mock RoleRepository
     */
    @Mock
    private RoleRepository roleRepositoryMock;

    /**
     * mock RoleService
     */
    @InjectMocks
    private RoleService roleServiceMock;

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
     * dummy data for role test
     *
     * @return
     */
    private Role getDummyRole() {
        Role role = new Role();
        role.setId(1);
        role.setRoleName("CAPTAIN");
        role.setDeleted(false);
        role.setCreatedBy(1);
        role.setCreatedTime(DateDummy.myLocalDateTime(null));

        return role;
    }

    /**
     * Test of findByRoleName method, of class RoleService.
     */
    @Test
    public void testFindByRoleName() {
        when(roleRepositoryMock.findByRoleName("MIMIN")).thenReturn(this.getDummyRole());
        Assert.assertNotNull(roleServiceMock.findByRoleName("MIMIN"));

        // case 2 : should be empty because role name XXX is not exist
        Assert.assertNull(roleServiceMock.findByRoleName("XXX"));
    }

    /**
     * Test of findBydeletedIn method, of class RoleService.
     *
     */
    @Test
    public void testFindBydeletedIn() {
        Role role = this.getDummyRole();

        when(roleRepositoryMock.findBydeletedIn(0)).thenReturn(Arrays.asList(new Role[]{role, role, role}));
        Assert.assertNotNull(roleServiceMock.findBydeletedIn(0));
    }

    /**
     * Test of findAllBydeletedFalse method, of class RoleService.
     *
     */
    @Test
    public void testFindAllBydeletedFalse() {
        // case 1 : will return data is null
        when(roleRepositoryMock.findAllBydeletedFalse()).thenReturn(Arrays.asList(new Role[]{}));
        Assert.assertEquals(new ArrayList<Role>(), roleServiceMock.findAllBydeletedFalse());

        // case 2 : will return data is not null
        when(roleRepositoryMock.findAllBydeletedFalse()).thenReturn(Arrays.asList(new Role[]{this.getDummyRole()}));
        Assert.assertNotNull(roleServiceMock.findAllBydeletedFalse());
    }

    /**
     * Test of save method, of class RoleService.
     *
     */
    @Test
    public void testSave() {
        Role role = this.getDummyRole();
        when(roleRepositoryMock.save(role)).thenReturn(role);

        // case 1 : should be equal
        Role save = roleServiceMock.save(role);
        Assert.assertEquals(role, save);

        // case 2 : should be not equal because the object is different
        Role roleNE = new Role();
        roleNE.setId(2);
        Assert.assertNotEquals(roleNE, save);
    }

    /**
     * Test of findById method, of class RoleService. should only found the role
     * with id 1 because there is no role with id 2
     *
     */
    @Test
    public void testFindById() {
        Role role = this.getDummyRole();

        // case 1 : not null
        when(roleRepositoryMock.findById(1)).thenReturn(Optional.of(role));
        Assert.assertNotNull(roleServiceMock.findById(1));

        // case 2 : should be empty because id 2 is not exist
        Assert.assertEquals(Optional.empty(), roleServiceMock.findById(2));
    }

    /**
     * Test of deleted method, of class RoleService. deleted is false before
     * deleted process
     *
     */
    @Test
    public void testDeleted() {
        Role role = this.getDummyRole();
        roleServiceMock.deleted(role);
        Assert.assertTrue(role.getDeleted());
    }

    /**
     * Test of deleteRoleById method, of class RoleService. deleted is false
     * before deleted process
     *
     */
    @Test
    public void testDeleteRoleById() {
        Role role = this.getDummyRole();
        when(roleRepositoryMock.findById(1)).thenReturn(Optional.of(role));
        roleServiceMock.deleteRoleById(1);
        Assert.assertTrue(role.getDeleted());
    }
}
