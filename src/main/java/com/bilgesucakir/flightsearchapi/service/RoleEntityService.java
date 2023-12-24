package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.RoleEntity;

/**
 * Role service
 */
public interface RoleEntityService {

    RoleEntity findByName(String name);
}
