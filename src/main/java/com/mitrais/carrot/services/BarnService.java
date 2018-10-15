/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.carrot.services;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.Barn;
import com.mitrais.carrot.repositories.BarnRepository;
import com.mitrais.carrot.services.interfaces.IBarn;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

/**
 *
 * Class BarnService
 */
@Service
public class BarnService implements IBarn {

    /**
     * barn repository
     */
    private BarnRepository barnRepository;

    /**
     * the barn service constructor
     *
     * @param barnRepository	the repository for Barn model
     */
    public BarnService(@Autowired BarnRepository barnRepository) {
        this.barnRepository = barnRepository;
    }

    /**
     * findAll will return a Iterable of barn
     *
     * @return all Barn
     */
    @Override
    public Iterable<Barn> findAll() {
        return barnRepository.findAllBydeletedIsNull();
    }

    /**
     * find barn by id
     *
     * @param id the barn id
     * @return Barn	The Barn data as result
     */
    @Override
    public Barn findById(Integer id) {
        return barnRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
    }

    /**
     * insert new Barn
     *
     * @param	barn the entity of Barn
     * @return	Barn	The barn after created the entity
     */
    @Override
    public Barn save(Barn barn) {
    	barn.setDeleted(false);
    	barn.setCreatedTime(LocalDateTime.now());
    	barn.setLastModifiedTime(LocalDateTime.now());
        return barnRepository.save(barn);
    }

    /**
     * update barn
     *
     * @param barn the updated Barn
     * @return Updated Barn
     */
    @Override
    public Barn update(Barn barn) {
        Barn model = barnRepository.findById(barn.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", barn.getId()));
        BeanUtils.copyProperties(barn, model);

        model.setId(barn.getId());
        return barnRepository.save(model);
    }

    /**
     * To do soft delete by set isDelete=1
     *
     * @param	id	The Barn identifier
     * @return	Updated Barn
     */
    @Override
    public Barn delete(Integer id) {
        Barn bN = barnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        bN.setDeleted(true);
        return barnRepository.save(bN);
    }
}
