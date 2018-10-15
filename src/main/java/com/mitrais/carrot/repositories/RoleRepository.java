package com.mitrais.carrot.repositories;

import com.mitrais.carrot.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is JPA repository for Role
 * https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.query-creation
 * 
 * @author Febri_MW251
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * find by role name
     *
     * @param roleName name of role
     * @return Role object
     */
    public Role findByRoleName(String roleName);

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted is deleted value
     * @return Iterable of Role object
     */
    public Iterable<Role> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     *
     * @return Iterable of Role object
     */
    public Iterable<Role> findAllBydeletedFalse();
}
