package com.mitrais.carrot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.RoleService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * role rest api controller to handle Role Crud controller
 *
 * @author Febri
 */
@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "roles", description = "Crud service for roles")
public class RoleController {

    /**
     * variable of RoleService service
     */
    public RoleService roleService;

    /**
     * RoleController constructor to set dependency injection that this class
     * needs
     *
     * @param roleService RoleService object dependency injection
     */
    public RoleController(@Autowired RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * get all data of role that status deleted is 0
     *
     * @return will return ResponseEntity Iterable Role object 
     */
    @GetMapping("/roles")
    @ResponseBody
    @ApiOperation(value = "Get all roles", notes = "Returns list of all roles in the database.")
    public ResponseEntity<Iterable<Role>> all() {
        return new ResponseEntity<>(roleService.findAllBydeletedFalse(), HttpStatus.OK);
    }

    /**
     * create new data of role
     *
     * @param body Request body of role. Cannot be empty
     * @return this method will return ResponseEntity Role obejct
     */
    @PostMapping("/roles")
    @ResponseBody
    @ApiOperation(value = "Create new role", notes = "Create new role.")
    public ResponseEntity<Role> save(
            @ApiParam("Example Request body of role. Cannot be empty.") @Valid @RequestBody Role body
    ) {
        return new ResponseEntity<>(roleService.save(body), HttpStatus.CREATED);
    }

    /**
     * Get specific role by role id
     *
     * @param id Id of the role to be get. Cannot be empty
     * @return this method will return ResponseEntity Role object with Http Status 200 if there is no errors
     */
    @GetMapping("/roles/{id}")
    @ResponseBody
    @ApiOperation(value = "Get one role by id", notes = "Get specific role by role id.")
    public ResponseEntity<Role> detail(
            @ApiParam("Id of the role to be get. Cannot be empty.") @PathVariable Integer id
    ) {
        Role model = roleService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Update role data by role id.
     *
     * @param id this variable is Integer type and this is a primary key of Role that you want to update 
     * @param body Request body of role to create the role data. Cannot be empty
     * @return this method will return ResponseEntity Role object with Http Status 200 if there is no errors
     */
    @PutMapping("/roles/{id}")
    @ResponseBody
    @ApiOperation(value = "Update role by id", notes = "Update role data by role id.")
    public ResponseEntity<Role> update(
            @ApiParam("Id of the role to be update. Cannot be empty.") @PathVariable Integer id,
            @ApiParam("Example Request body of role. Cannot be empty.") @Valid @RequestBody Role body
    ) {
        Role model = roleService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        model.setRoleName(body.getRoleName());
        return new ResponseEntity<>(roleService.save(model), HttpStatus.OK);
    }

    /**
     * Delete role by role id, this system only mark deleted entity as true
     *
     * @param id his variable is Integer type and this is a primary key of Role that you want to mark as delete
     * @return this method will return ResponseEntity ApiResponse object with Http Status 200 if there is no errors
     */
    @DeleteMapping("/roles/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete role by id", notes = "Delete role by role id, this system only mark deleted as true.")
    public ResponseEntity<ApiResponse> delete(
            @ApiParam("Id of the role to be delete. Cannot be empty.") @PathVariable Integer id
    ) {
    	Role role = roleService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        roleService.deleted(role);
        return new ResponseEntity<>(new ApiResponse(true, "Data id : " + id + " deleted successfully"), HttpStatus.OK);
    }
}
