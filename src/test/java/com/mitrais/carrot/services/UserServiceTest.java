package com.mitrais.carrot.services;

import com.mitrais.carrot.dummy.DateDummy;
import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.models.User;
import com.mitrais.carrot.payload.SignUpRequest;
import com.mitrais.carrot.repositories.RoleRepository;
import com.mitrais.carrot.repositories.UserRepository;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User service test
 *
 * @author Febri
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    /**
     * Mock RoleRepository
     */
    @Mock
    private UserRepository userRepositoryMock;

    /**
     * Mock RoleRepository
     */
    @Mock
    private RoleRepository roleRepositoryMock;

    /**
     * Mock passwordEncoder
     */
    @Mock
    private PasswordEncoder passwordEncoderMock;

    /**
     * mock RoleService
     */
    @InjectMocks
    private UserService userServiceMock;

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
     * prepare dummy data for user
     *
     * @return
     */
    private User getDummyUser() {
        // user dummy data
        User user = new User("febri", "cakpep", "cakpep@oaoe.com", "everydayisholiday");
        user.setId(1L);
        user.setActive(true);
        user.setDeleted(false);
        user.setCreatedBy(1);
        user.setCreatedTime(DateDummy.myLocalDateTime(null));
        user.setLastModifiedBy(1);
        user.setLastModifiedTime(DateDummy.myLocalDateTime(null));
        return user;
    }

    /**
     * Test of findByEmail method, of class UserService.
     *
     */
    @Test
    public void testFindByEmail() {
        // case 1 : should be found the data
        when(userRepositoryMock.findByEmail("cakpep@oaoe.com")).thenReturn(this.getDummyUser());
        Assert.assertNotNull(userServiceMock.findByEmail("cakpep@oaoe.com"));

        // case 2 : should be empty because user with email tonggodewe@xxx.com is not
        // exist
        Assert.assertNull(userServiceMock.findByEmail("tonggodewe@xxx.com"));
    }

    /**
     * Test of findByUserNameOrEmail method, of class UserService.
     *
     */
    @Test
    public void testFindByUserNameOrEmail() {
        // case 1 : should be found the data
        when(userRepositoryMock.findByUserNameOrEmail("cakpep", "cakpep@oaoe.com"))
                .thenReturn(Optional.of(this.getDummyUser()));
        Assert.assertEquals(Optional.of(this.getDummyUser()),
                userServiceMock.findByUserNameOrEmail("cakpep", "cakpep@oaoe.com"));

        // case 2 : should be empty because user with email tonggodewe@xxx.com is not
        // exist
        Assert.assertNotNull(userServiceMock.findByUserNameOrEmail("xxx", "cakpep@oaoe.com"));

        // case 3 : should not be empty because user with email tonggodewe@xxx.com is
        // exist
        Assert.assertNotNull(userServiceMock.findByUserNameOrEmail("xxx", "cakpep@oaoe.com"));

        // case 4 : should not be empty because user with username cakpep is exist
        Assert.assertNotNull(userServiceMock.findByUserNameOrEmail("cakpep", "zzzzz@oaoe.com"));

        // case 5 : should not be empty because user with username xxxxx and email
        // tonggodewe@xxx.com is not exist
        Assert.assertEquals(Optional.empty(), userServiceMock.findByUserNameOrEmail("xxxxx", "zzzzz@oaoe.com"));
    }

    /**
     * Test of findByIdIn method, of class UserService.
     */
    @Test
    public void testFindByIdIn() {
        // case 1 : should be found the data
        List<Long> userIds = new ArrayList<>(Arrays.asList(1L, 2L, 3L));

        when(userRepositoryMock.findByIdIn(userIds))
                .thenReturn(Arrays.asList(new User[]{new User(), new User(), new User()}));

        Assert.assertNotNull(userServiceMock.findByIdIn(userIds));
        Assert.assertEquals(3, userServiceMock.findByIdIn(userIds).size());

        // case 2 : find only one
        User user = this.getDummyUser();
        List<Long> userIds2 = new ArrayList<>(Arrays.asList(1L));
        when(userRepositoryMock.findByIdIn(userIds2)).thenReturn(Arrays.asList(new User[]{user}));

        Assert.assertEquals(1, userServiceMock.findByIdIn(userIds2).size());
    }

    /**
     * Test of findByUserName method, of class UserService.
     */
    @Test
    public void testFindByUserName() {
        User user = this.getDummyUser();
        when(userRepositoryMock.findByUserName("xxx")).thenReturn(user);
        Assert.assertNotNull(userServiceMock.findByUserName("xxx"));
        Assert.assertEquals(user.getUserName(), userServiceMock.findByUserName("xxx").getUserName());
    }

    /**
     * Test of existsByUserName method, of class UserService.
     */
    @Test
    public void testExistsByUserName() {
        when(userRepositoryMock.existsByUserName("xxx")).thenReturn(true);
        Assert.assertTrue(userServiceMock.existsByUserName("xxx"));
        Assert.assertFalse(userServiceMock.existsByUserName("aaaaaaaaaa"));
    }

    /**
     * Test of existsByEmail method, of class UserService.
     */
    @Test
    public void testExistsByEmail() {
        when(userRepositoryMock.existsByEmail("chicken@techno.com")).thenReturn(true);
        Assert.assertTrue(userServiceMock.existsByEmail("chicken@techno.com"));
        Assert.assertFalse(userServiceMock.existsByEmail("xyz@abc.com"));
    }

    /**
     * Test of findBydeletedIn method, of class UserService.
     */
    @Test
    public void testFindBydeletedIn() {
        User userDummy = this.getDummyUser();
        when(userRepositoryMock.findBydeletedIn(0))
                .thenReturn(Arrays.asList(new User[]{userDummy, userDummy, userDummy}));
        Assert.assertNotNull(userServiceMock.findBydeletedIn(0));
    }

    /**
     * Test of findAllBydeletedIsNull method, of class UserService.
     */
    @Test
    public void testFindAllBydeletedIsNull() {
        User userDummy = this.getDummyUser();
        when(userRepositoryMock.findAllBydeletedIsNull())
                .thenReturn(Arrays.asList(new User[]{userDummy, userDummy, userDummy}));
        Assert.assertNotNull(userServiceMock.findAllBydeletedIsNull());
    }

    /**
     * Test of findAllBydeletedFalse method, of class UserService.
     */
    @Test
    public void testFindAllBydeletedFalse() {
        User userDummy = this.getDummyUser();
        User userDummy2 = this.getDummyUser();
        userDummy2.setDeleted(true);
        when(userRepositoryMock.findAllBydeletedFalse())
                .thenReturn(Arrays.asList(new User[]{userDummy, userDummy2}));
        Assert.assertNotNull(userServiceMock.findAllBydeletedFalse());

        Assert.assertTrue(userServiceMock.findAllBydeletedFalse().iterator().hasNext());
    }

    /**
     * Test of save method, of class UserService.
     */
    @Test
    public void testSave() {
        User user = this.getDummyUser();
        when(userRepositoryMock.save(user)).thenReturn(user);
        User save = userServiceMock.save(user);
        Assert.assertNotNull(save);
        Assert.assertEquals(user, save);
        Assert.assertEquals(user.getEmail(), save.getEmail());
    }

    /**
     * Test of findById method, of class UserService.
     */
    @Test
    public void testFindById() {
        User user = this.getDummyUser();
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userServiceMock.findById(1L);
        Assert.assertNotNull(result.get());
        Assert.assertNotNull(result.get().getId());
        Assert.assertEquals(Optional.of(user).get(), result.get());
        Assert.assertEquals(Optional.of(user).get().getEmail(), result.get().getEmail());
    }

    /**
     * Test of deleted method, of class UserService.
     */
    @Test
    public void testDeleted() {
        User user = this.getDummyUser();
        userServiceMock.deleted(user);
        Assert.assertTrue(user.getDeleted());
    }

    /**
     * Test of deletedUserById method, of class UserService.
     */
    @Test
    public void testDeletedUserById() {
        User user = this.getDummyUser();
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
        userServiceMock.deletedUserById(1L);
        Assert.assertTrue(user.getDeleted());
    }

    /**
     * Test of registerUser method, of class UserService.
     */
    @Test
    public void testRegisterUser() {
        SignUpRequest signUp = new SignUpRequest();
        signUp.setEmail("cakpep@oaoe.com");
        signUp.setName("febri");
        signUp.setPassword("everydayisholiday");
        signUp.setUserName("cakpep");

        Role role = new Role();
        role.setId(1);
        role.setRoleName("ROLE_STAFF");
        role.setDeleted(false);
        role.setCreatedBy(1);

        User user = this.getDummyUser();
        user.setUserName("xxx");
        user.setRoles(Collections.singleton(role));

        when(passwordEncoderMock.encode(user.getPassword())).thenReturn("xxxxx");
        when(roleRepositoryMock.findByRoleName("ROLE_STAFF")).thenReturn(role);
        // when(userRepositoryMock.save(user)).thenReturn(user);

        Boolean error = false;
        User signOk = null;
        try {
            signOk = userServiceMock.registerUser(signUp);
        } catch (Exception e) {
            error = true;
        }

        Assert.assertFalse(error);
        Assert.assertNull(signOk);
    }

    /**
     * Test of isUserAvailable method, of class UserService.
     */
    @Test
    public void testIsUserAvailable() {

        when(userRepositoryMock.existsByEmail("java@bootcamp.com")).thenReturn(false);
        when(userRepositoryMock.existsByEmail("notfound@bootcamp.com")).thenReturn(true);
        when(userRepositoryMock.existsByUserName("cakpep")).thenReturn(false);

        // case 1 : should be true
        Assert.assertTrue(userServiceMock.isUserAvailable("username", "cakpep"));
        Assert.assertTrue(userServiceMock.isUserAvailable("email", "java@bootcamp.com"));

        // case 2 : should be false
        Assert.assertFalse(userServiceMock.isUserAvailable("xxx", "java@bootcamp.com"));
        Assert.assertFalse(userServiceMock.isUserAvailable("email", "notfound@bootcamp.com"));
    }

}
