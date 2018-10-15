package com.mitrais.carrot.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.mitrais.carrot.models.SharingLevel;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.interfaces.ISharingLevelService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * SharingLevelController is a rest full controller for SharingLevel entity
 * 
 * @author Medianto
 *
 */
@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "sharinglevel", description = "Crud service for SharingLevel")
public class SharingLevelController {

	@Autowired
	public ISharingLevelService sharingLevelService;

	/**
	 * get all data
	 *
	 * @return SharingLevel all SharingLevel record from database
	 */
	@GetMapping("/sharing-levels")
	@ResponseBody
	@ApiOperation(value = "Get all SharingLevel", notes = "Returns list of all SharingLevel in the database.")
	public Iterable<SharingLevel> all() {
		return sharingLevelService.findAllBydeletedIsNull();
	}

	/**
	 * create new data
	 *
	 * @param body SharingLevel send from front-end
	 * @return ResponseEntity response status created
	 */
	@PostMapping("/sharing-levels")
	@ResponseBody
	@ApiOperation(value = "Create new SharingLevel", notes = "Create new SharingLevel.")
	public ResponseEntity<ApiResponse> save(
			@ApiParam("Example Request body of SharingLevel. Cannot be empty.") @Valid @RequestBody SharingLevel body) {
		sharingLevelService.save(body);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.CREATED);
	}

	/**
	 * get detail by id
	 * 
	 * @param id id selected from front-end
	 * @return SharingLevel matched SharingLevel record from database with response
	 *         status
	 */
	@GetMapping("/sharing-levels/{id}")
	@ResponseBody
	@ApiOperation(value = "Get one SharingLevel by id", notes = "Get specific SharingLevel by SharingLevel id.")
	public ResponseEntity<SharingLevel> show(
			@ApiParam("Id of the SharingLevel to be get. Cannot be empty.") @PathVariable Integer id) {
		SharingLevel sharingLevel = sharingLevelService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		return new ResponseEntity<SharingLevel>(sharingLevel, HttpStatus.OK);
	}

	/**
	 * update data
	 * 
	 * @param id   id selected from front-end
	 * @param body udpated SharingLevel send from front-end
	 * @return ResponseEntity response status created
	 */
	@PutMapping("/sharing-levels/{id}")
	@ResponseBody
	@ApiOperation(value = "Update SharingLevel by id", notes = "Update SharingLevel data by SharingLevel id.")
	public ResponseEntity<ApiResponse> update(
			@ApiParam("Id of the SharingLevel to be update. Cannot be empty.") @PathVariable Integer id,
			@ApiParam("Example Request body of SharingLevel. Cannot be empty.") @Valid @RequestBody SharingLevel body) {
		SharingLevel shareLevel = sharingLevelService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		BeanUtils.copyProperties(body, shareLevel);
		shareLevel.setId(id);
		sharingLevelService.save(shareLevel);

		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.OK);
	}

	/**
	 * action delete to set is deleted = true
	 * 
	 * @param id id selected from front-end
	 * @return ResponseEntity response status created
	 */
	@DeleteMapping("/sharing-levels/{id}")
	@ResponseBody
	@ApiOperation(value = "Delete SharingLevel by id", notes = "Delete SharingLevel by SharingLevel id, this system only mark deleted as true.")
	public ResponseEntity<ApiResponse> delete(
			@ApiParam("Id of the SharingLevel to be update. Cannot be empty.") @PathVariable Integer id) {
		SharingLevel sl = sharingLevelService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
		sl.setDeleted(true);
		sharingLevelService.save(sl);
		return new ResponseEntity<>(new ApiResponse(true, "Data is Deleted!"), HttpStatus.OK);
	}
}
