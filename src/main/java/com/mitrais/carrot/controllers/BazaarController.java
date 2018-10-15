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

import com.mitrais.carrot.models.Bazaar;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.BazaarService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * BazaarController is a rest full controller for Bazaar entity
 * 
 * @author Ryan Mario
 *
 */

@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "bazaar", description = "Crud service for Bazaar")
public class BazaarController {

	public BazaarService bazaarService;

    public BazaarController(@Autowired BazaarService bazaarService) {
        this.bazaarService = bazaarService;
    }

    /**
	 * get all data
	 * 
	 * @return Bazaar all Bazaar record from database
	 */
    @GetMapping("/bazaars")
    @ResponseBody
    @ApiOperation(value = "Get all bazaars", notes = "Returns list of all bazaars in the database.")
    public Iterable<Bazaar> all() {
        return bazaarService.findAll();
    }

    /**
	 * create new data
	 * 
	 * @param body Bazaar send from front-end
	 * @return ResponseEntity response status created
	 */
    @PostMapping("/bazaars")
    @ResponseBody
    @ApiOperation(value = "Create new bazaar", notes = "Create new bazaar.")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Bazaar body) {
    	bazaarService.save(body);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.CREATED);
    }

    /**
	 * get detail by id
	 * 
	 * @param id id selected from front-end
	 * @return bazaar matched Bazaar record from database with response status
	 */
    @GetMapping("/bazaars/{id}")
    @ResponseBody
    @ApiOperation(value = "Get one bazaar by id", notes = "Get specific bazaar by bazaar id.")
    public ResponseEntity<Bazaar> detail(@PathVariable Integer id) {
        return new ResponseEntity<Bazaar>(bazaarService.findById(id), HttpStatus.OK);
    }

    /**
	 * update data
	 * 
	 * @param id id selected from front-end
	 * @param body udpated Bazaar send from front-end
	 * @return ResponseEntity response status created
	 */
    @PutMapping("/bazaars/{id}")
    @ResponseBody
    @ApiOperation(value = "Update bazaar by id", notes = "Update bazaar data by bazaar id.")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @Valid @RequestBody Bazaar body) {
    	bazaarService.update(id, body);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
    }

    /**
	 * action delete to set is deleted = true
	 * 
	 * @param id id selected from front-end
	 * @return ResponseEntity response status created
	 */
    @DeleteMapping("/bazaars/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete bazaar by id", notes = "Delete bazaar by bazaar id, this system only mark deleted as true.")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
    	bazaarService.delete(id);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
    }
}
