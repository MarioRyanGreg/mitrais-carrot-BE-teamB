package com.mitrais.carrot.controllers;

import com.mitrais.carrot.models.BarnSetting;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.services.BarnSettingService;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 
 * Class BarnSettingController controlling API request
 *
 */
@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
public class BarnSettingController {

	/**
	 * The service of BarnSettings controller
	 */	
    public BarnSettingService barnSettingService;
    
    /**
     * The class constructor specifying barnSettingService
     * @param barnSettingService	The service class to setup BarnSettingController
     */
    public BarnSettingController(@Autowired(required = false) BarnSettingService barnSettingService) {
    	this.barnSettingService = barnSettingService;
    }
    

    /**
     * get all data
     *
     * @return all Barn Setting Data 
     */
    @GetMapping("/barns-settings")
    @ResponseBody
    public Iterable<BarnSetting> all() {
        return barnSettingService.findAll();
    }

    /**
     * create new data
     *
     * @param 	body	The barnsetting to save
     * @return	the API response with HTTP CREATED status
     */
    @PostMapping("/barns-settings")
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody BarnSetting body) {
        barnSettingService.save(body);
        return new ResponseEntity<>(new ApiResponse(true, "Barn setting save successfully"), HttpStatus.CREATED);
    }

    /**
     * get detail by id
     *
     * @param 	id	The BarnSetting Identifier
     * @return	The Barn Data
     */
    @GetMapping("/barns-settings/{id}")
    @ResponseBody
    public BarnSetting detail(@PathVariable Integer id) {
        return barnSettingService.findById(id);
    }

    /**
     * update data
     *
     * @param id	The BarnSetting Identifier
     * @param body	The BarnSetting to update
     * @return	the Barn Model with HTTPStatus OK
     */
    @PutMapping("/barns-settings/{id}")
    @ResponseBody
    public ResponseEntity<BarnSetting> update(@PathVariable Integer id, @Valid @RequestBody BarnSetting body) {
        BarnSetting model = barnSettingService.findById(id);
        BeanUtils.copyProperties(body, model);
        model.setId(id);
        barnSettingService.save(model);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * action to do soft delete, set is deleted = 1
     *
     * @param id	The BarnSetting identifier
     * @return 	Api response with HTTP Status OK
     */
    @DeleteMapping("/barns-settings/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        BarnSetting sl = barnSettingService.findById(id);
        sl.setDeleted(true);
        barnSettingService.save(sl);
//        barnSettingRepository.delete(sl.get());
        return new ResponseEntity<>(
                new ApiResponse(true, "Data id : " + id + "deleted successfully"),
                HttpStatus.OK
        );
    }
}
