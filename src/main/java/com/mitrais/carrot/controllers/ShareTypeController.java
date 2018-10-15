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

import com.mitrais.carrot.models.ShareType;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.SharingTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * ShareTypeController is a rest full controller for ShareType entity
 * 
 * @author Medianto
 *
 */

@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "sharetype", description = "Crud service for shareType")
public class ShareTypeController {

	/**
	 * SharingTypeService variable
	 */
	public SharingTypeService shareTypeService;

	/**
	 * ShareTypeController controller constructor
	 * 
	 * @param shareTypeService Dependency variable of ShareTypeService class
	 */
	public ShareTypeController(@Autowired SharingTypeService shareTypeService) {
		this.shareTypeService = shareTypeService;
	}

	/**
	 * get all data
	 * 
	 * @return ShareType all ShareType record from database
	 */
	@GetMapping("/sharing-types")
	@ResponseBody
	@ApiOperation(value = "Get all ShareType", notes = "Returns list of all ShareType in the database.")
	public Iterable<ShareType> all() {
		return shareTypeService.findAll();
	}

	/**
	 * create new data
	 * 
	 * @param body ShareType send from front-end
	 * @return ResponseEntity response status created
	 */
	@PostMapping("/sharing-types")
	@ResponseBody
	@ApiOperation(value = "Create new ShareType", notes = "Create new ShareType.")
	public ResponseEntity<ApiResponse> save(
			@ApiParam("Example Request body of ShareType. Cannot be empty.") @Valid @RequestBody ShareType body) {
		shareTypeService.save(body);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.CREATED);
	}

	/**
	 * get detail by id
	 * 
	 * @param id id selected from front-end
	 * @return shareType matched ShareType record from database with response status
	 */
	@GetMapping("/sharing-types/{id}")
	@ResponseBody
	@ApiOperation(value = "Get one ShareType by id", notes = "Get specific ShareType by ShareType id.")
	public ResponseEntity<ShareType> detail(
			@ApiParam("Id of the ShareType to be get. Cannot be empty.") @PathVariable Integer id) {
		return new ResponseEntity<ShareType>(shareTypeService.findById(id), HttpStatus.OK);
	}

	/**
	 * update data
	 * 
	 * @param id   id selected from front-end
	 * @param body updated ShareType send from front-end
	 * @return ResponseEntity response status created
	 */
	@PutMapping("/sharing-types/{id}")
	@ResponseBody
	@ApiOperation(value = "Update ShareType by id", notes = "Update ShareType data by ShareType id.")
	public ResponseEntity<ApiResponse> update(
			@ApiParam("Id of the ShareType to be update. Cannot be empty.") @PathVariable Integer id,
			@ApiParam("Example Request body of ShareType. Cannot be empty.") @Valid @RequestBody ShareType body) {
		shareTypeService.update(id, body);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
	}

	/**
	 * action delete to set is deleted = true
	 * 
	 * @param id id selected from front-end
	 * @return ResponseEntity response status created
	 */
	@DeleteMapping("/sharing-types/{id}")
	@ResponseBody
	@ApiOperation(value = "Delete ShareType by id", notes = "Delete ShareType by ShareType id, this system only mark deleted as true.")
	public ResponseEntity<ApiResponse> delete(
			@ApiParam("Id of the ShareType to be update. Cannot be empty.") @PathVariable Integer id) {
		shareTypeService.delete(id);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
	}
}
