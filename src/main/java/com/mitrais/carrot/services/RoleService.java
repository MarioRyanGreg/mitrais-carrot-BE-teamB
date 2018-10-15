package com.mitrais.carrot.services;

import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.repositories.RoleRepository;
import com.mitrais.carrot.services.interfaces.IRole;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Role service implement IRole
 *
 * @author Febri_MW251@mitrais.com
 */
@Service
public class RoleService implements IRole {

    /**
     * user repository
     */
    private RoleRepository roleRepository;

    /**
     * User Service Constructor
     *
     * @param roleRepository dependency injection of RoleRepository object
     */
    public RoleService(@Autowired RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * find role by role name
     *
     * @param roleName name of role
     * @return Role object
     */
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    /**
     * find role by deleted in
     *
     * @param isdeleted deleted value
     * @return Array object of Role
     */
    @Override
    public Iterable<Role> findBydeletedIn(Integer isdeleted) {
        return roleRepository.findBydeletedIn(isdeleted);
    }

    /**
     * find all role by deleted false
     *
     * @return Array object of Role with deleted is false
     */
    @Override
    public Iterable<Role> findAllBydeletedFalse() {
        return roleRepository.findAllBydeletedFalse();
    }

    /**
     * save user data into database
     *
     * @param role Role object to be save into database
     * @return will return Role object that successfully saved in database
     */
    @Override
    public Role save(Role role) {
    	role.setDeleted(false);
    	role.setCreatedTime(LocalDateTime.now());
    	role.setLastModifiedTime(LocalDateTime.now());
        return roleRepository.save(role);
    }

    /**
     * find role by id
     *
     * @param id pk of role
     * @return Optional object of Role
     */
    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    /**
     * deleted role with only set the deleted to true
     *
     * @param role Role object that will mark as deleted 
     */
    @Override
    public void deleted(Role role) {
        role.setDeleted(true);
        role.setLastModifiedTime(LocalDateTime.now());
        roleRepository.save(role);
    }

    /**
     * deleted role with only set the deleted to true
     * 
     * @param id pk of role
     */
    @Override
    public void deleteRoleById(Integer id) {
        Role role = this.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        role.setDeleted(true);
        role.setLastModifiedTime(LocalDateTime.now());
        roleRepository.save(role);
    }

}
