package com.mitrais.carrot.controllers;

import com.mitrais.carrot.models.Rewards;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.RewardService;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * RewardController is rest full controller for Reward entity
 * 
 * @author Rudy
 *
 */
@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "reward", description = "Crud service for reward")
public class RewardsController {

	/**
	 * Reward Service
	 * 
	 */
    private RewardService rewardService;

    /**
	 * RewardController Constructor
	 *
	 * @param rewardService
	 *            RewardService bean
	 *            
	 */
    public RewardsController(@Autowired RewardService rewardService) {
		this.rewardService = rewardService;
	}
    
    /**
     * get all non-deleted reward data
     *
     * @return Iterable
     * 
     */
    @RequestMapping(value = "/rewards", method = RequestMethod.GET)
    @ApiOperation(value = "Get all reward", notes = "Returns list of all Reward in the database.", response = Rewards.class)
    public Iterable<Rewards> all() {
        return rewardService.all();
    }

    /**
     * POST API for create new reward data
     *
     * @param body Rewards
     * @return ResponseEntity
     * 
     */
    @RequestMapping(value = "/rewards", method = RequestMethod.POST)
    @ApiOperation(value = "Add reward", notes = "Add Reward to database.", response = Rewards.class)
    public ResponseEntity<Rewards> save(
    		@ApiParam("Example Request body of reward. Cannot be empty.") @Valid @RequestBody Rewards body) {
        return new ResponseEntity<>(rewardService.save(body), HttpStatus.CREATED);
    }

    /**
     * get reward detail by id
     *
     * @param id Integer
     * @return ResponseEntity
     * 
     */
    @RequestMapping(value = "/rewards/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "get detail reward", notes = "get Reward by id.", response = Rewards.class)
    public ResponseEntity<?> detail(
    		@ApiParam("Id of the reward to be get. Cannot be empty.") @PathVariable Integer id) {
    	Optional<Rewards> reward = rewardService.findById(id);
        if(reward.isPresent()) {
        	return new ResponseEntity<>(reward, HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(new ApiResponse(false, "Reward with id" + id + "not found"), HttpStatus.NOT_FOUND);
        }
    	
    }

    /**
     * PUT API for update reward data by id
     *
     * @param id Integer
     * @param body Rewards
     * @return ResponseEntity
     * 
     */
    @RequestMapping(value = "/rewards/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update reward", notes = "Update Reward in database.", response = Rewards.class)
    public ResponseEntity<?> update(
    		@ApiParam("Id of the reward to be update. Cannot be empty.") @PathVariable Integer id, 
    		@ApiParam("Example Request body of reward. Cannot be empty.") @Valid @RequestBody Rewards body) {
    	Optional<Rewards> reward = rewardService.findById(id);

        if(reward.isPresent()) {
        	if(body.getDeleted() == null) {
	      		body.setDeleted(false);
	      	}
        	
        	rewardService.update(reward.get(), body);
            return new ResponseEntity<>(reward, HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(new ApiResponse(false, "Reward with id" + id + "not found"), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * DELETE API for action delete to set is deleted = 1
     *
     * @param id Integer
     * @return ResponseEntity
     * 
     */
    @RequestMapping(value = "/rewards/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete reward", notes = "Delete Reward in database.", response = Rewards.class)
    public ResponseEntity<?> delete(
    		@ApiParam("Id of the reward to be delete. Cannot be empty.") @PathVariable Integer id) {
    	Optional<Rewards> reward = rewardService.findById(id);
    	
    	if(reward.isPresent()) {
        	rewardService.delete(reward.get());
            return new ResponseEntity<>(new ApiResponse(true, "Data deleted successfully"), HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(new ApiResponse(false, "Reward with id" + id + "not found"), HttpStatus.NOT_FOUND);
        }
    	
    }
}
