package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.RoleEntity;
import com.bilgesucakir.flightsearchapi.repository.RoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Role service implementation handling some querying of role entity
 */
@Service
public class RoleEntityServiceImpl implements  RoleEntityService{

    private RoleEntityRepository roleEntityRepository;

    @Autowired
    public RoleEntityServiceImpl(RoleEntityRepository roleEntityRepository) {
        this.roleEntityRepository = roleEntityRepository;
    }

    @Override
    public RoleEntity findByName(String name) {

            Optional<RoleEntity> result = roleEntityRepository.findByName(name);

            RoleEntity roleEntity = null;

            if(result.isPresent()){
                roleEntity = result.get();
            }
            return roleEntity;
    }
}
