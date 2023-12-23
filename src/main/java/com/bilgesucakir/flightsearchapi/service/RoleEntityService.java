package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.RoleEntity;

public interface RoleEntityService {


    RoleEntity findByName(String name);
}
