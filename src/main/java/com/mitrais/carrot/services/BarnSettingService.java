/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.carrot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitrais.carrot.models.BarnSetting;
import com.mitrais.carrot.repositories.BarnSettingRepository;
import com.mitrais.carrot.services.interfaces.IBarnSetting;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

/**
 * Class for implementing service IBarnSetting
 * 
 */
@Service
public class BarnSettingService implements IBarnSetting{

    private BarnSettingRepository barnSettingRepository;
    
    /**
     * The constructor class specifying BarnSetting Repository
     * @param barnSettingRepository	The repository of BarnSetting
     */
    public BarnSettingService(@Autowired BarnSettingRepository barnSettingRepository) {
        this.barnSettingRepository = barnSettingRepository;
    }
    /**
     * findAll will return a Iterable of barnSetting
     *
     * @return all barnSetting
     */
    @Override
    public Iterable<BarnSetting> findAll() {
        return barnSettingRepository.findAllBydeletedIsNull();
    }

    /**
     * find barnSetting by id
     *
     * @param id the barnSetting id
     * @return BarnSetting as result of searching
     */
    @Override
    public BarnSetting findById(Integer id) {
        return barnSettingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
    }

    /**
     * insert new BarnSetting
     *
     * @param barnSetting the entity of BarnSetting
     * @return created barnSetting
     */
    @Override
    public BarnSetting save(BarnSetting barnSetting) {
        return barnSettingRepository.save(barnSetting);
    }

    /**
     * update barnSetting
     *
     * @param barnSetting the updated BarnSetting
     * @return updated BarnSetting
     */
    @Override
    public BarnSetting update(BarnSetting barnSetting) {
        BarnSetting model = barnSettingRepository.findById(barnSetting.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", barnSetting.getId()));
        BeanUtils.copyProperties(barnSetting, model);

        model.setId(barnSetting.getId());
        return barnSettingRepository.save(model);
    }

    /**
     * updating BarnSetting.isdeleted to true
     *
     * @param id	The Barn Setting Identifier
     * @return updated BarnSetting
     */
    @Override
    public BarnSetting delete(Integer id) {
        BarnSetting bN = barnSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        bN.setDeleted(true);
        return barnSettingRepository.save(bN);
    }
}
