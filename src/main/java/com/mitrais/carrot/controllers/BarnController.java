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

import com.mitrais.carrot.models.Barn;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.BarnService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 
 * Class BarnController controlling incoming API request
 *
 */
@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "Barn", description = "Crud service for Barn")
public class BarnController {

    public BarnService barnService;

    /**
     *  Class Constructor specifying the barn service.
     * @param barnService Service class to get the data based on business logic
     */
    public BarnController(@Autowired BarnService barnService){
        this.barnService = barnService;
    }
    
    /**
     * Get all barn data
     * @return 		the barn data
     */
    @GetMapping("/barns")
    @ResponseBody
    @ApiOperation(value = "Get all barns", notes = "Returns list of all barns in the database.")
    public Iterable<Barn> all() {
        return barnService.findAll();
    }

    /**
     * Create new data
     * @param 	body 	the object of barn to save
     * @return	the API response with HTTP CREATED status 			
     */
    @PostMapping("/barns")
    @ResponseBody
    @ApiOperation(value = "Operation to save", notes = "Create new ")
    public ResponseEntity<Barn> save(
            @ApiParam("Example Request body of Barn. Cannot be empty.") @Valid @RequestBody Barn body
    ) {
        return new ResponseEntity<>(barnService.save(body), HttpStatus.CREATED);
    }

    /**
     * Get data by id
     * @param 	id	the barn identifier
     * @return 	the barn data 
     */
    @GetMapping("/barns/{id}")
    @ResponseBody
    public Barn detail(@PathVariable Integer id) {
        return barnService.findById(id);
    }

    /**
     * Update data by id
     * @param id	the barn identifier
     * @param body	the barn with updated data
     * @return The model with HTTP status OK
     */
    @PutMapping("/barns/{id}")
    @ResponseBody
    public ResponseEntity<Barn> update(@PathVariable Integer id, @Valid @RequestBody Barn body) {
        Barn model = barnService.findById(id);
        BeanUtils.copyProperties(model, body);
        model.setId(id);
        barnService.save(model);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * delete data by id
     * @param id	the barn identifier
     * @return Api response with status OK
     */
    @DeleteMapping("/barns/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        Barn sl = barnService.findById(id);
        sl.setDeleted(true);
        barnService.save(sl);
//        barnRepository.delete(sl.get());
        return new ResponseEntity<>(
                new ApiResponse(true, "Data id : " + id + " deleted successfully"),
                HttpStatus.OK
        );
    }
}
