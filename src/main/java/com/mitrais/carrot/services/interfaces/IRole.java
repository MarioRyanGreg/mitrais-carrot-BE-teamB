package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.Role;
import java.util.Optional;

/**
 * Role interface class
 *
 * @author Febri
 */
public interface IRole {

    /**
     * save role into database
     * 
     * @param role object role that to be save
     * @return will return Role object that successfully saved in database
     */
    public Role save(Role role);

    /**
     * find role by id
     *
     * @param id pk of role
     * @return Optional object of Role
     */
    public Optional<Role> findById(Integer id);

    /**
     * delete role by Role object
     *
     * @param role Role object that will mark as deleted
     */
    public void deleted(Role role);

    /**
     * find role by role name
     *
     * @param roleName name of role
     * @return Role object
     */
    public Role findByRoleName(String roleName);

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted deleted value
     * @return Array object of Role
     */
    public Iterable<Role> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     *
     * @return Array object of Role with deleted is false
     */
    public Iterable<Role> findAllBydeletedFalse();

    /**
     * custom delete by id
     *
     * @param id pk of role
     */
    public void deleteRoleById(Integer id);
}
