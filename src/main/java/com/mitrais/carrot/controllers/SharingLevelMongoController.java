package com.mitrais.carrot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mitrais.carrot.models.SharingLevelMongo;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.interfaces.ISharingLevelMongoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public class SharingLevelMongoController {

	@Autowired
	public ISharingLevelMongoService sharingLevelMongoService;

	@PostMapping("/sharing-levels-mongo")
	@ResponseBody
	@ApiOperation(value = "Create new SharingLevelMongo", notes = "Create new SharingLevelMongo.")
	public ResponseEntity<ApiResponse> save(
			@ApiParam("Example Request body of SharingLevelMongos. Cannot be empty.") @Valid @RequestBody SharingLevelMongo body) {
		sharingLevelMongoService.save(body);
		return new ResponseEntity<>(new ApiResponse(true, ""), HttpStatus.CREATED);
	}

	@GetMapping("/sharing-levels-mongo/{sharingLevel}")
	@ResponseBody
	@ApiOperation(value = "Get one SharingLevelMongo by sharingLevel", notes = "Get specific SharingLevelMongo by sharingLevel.")
	public ResponseEntity<SharingLevelMongo> show(
			@ApiParam("The SharingLevel to be gotten. Cannot be empty.") @PathVariable Integer sharingLevel) {
		SharingLevelMongo sharingLevelMongo = sharingLevelMongoService.findBySharingLevel(sharingLevel);
		return new ResponseEntity<SharingLevelMongo>(sharingLevelMongo, HttpStatus.OK);
	}

	@DeleteMapping("/sharing-levels-mongo/{id}")
	@ResponseBody
	@ApiOperation(value = "Delete SharingLevelMongo by the object itself", notes = "Delete SharingLevelMongo by the object itself")
	public ResponseEntity<ApiResponse> delete(
			@ApiParam("The SharingLevelMongo to be deleted. Cannot be empty.") @PathVariable SharingLevelMongo sharingLevelMongo) {
		sharingLevelMongoService.delete(sharingLevelMongo);
		return new ResponseEntity<>(new ApiResponse(true, "Data is Deleted!"), HttpStatus.OK);
	}
}
