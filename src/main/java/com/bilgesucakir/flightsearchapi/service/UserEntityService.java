package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.Airport;
import com.bilgesucakir.flightsearchapi.entity.Flight;
import com.bilgesucakir.flightsearchapi.entity.UserEntity;
import com.bilgesucakir.flightsearchapi.repository.UserEntityRepository;

public interface UserEntityService {

    boolean isUserEntityExists(String username);

    boolean isUsernameValid(String username);

    UserEntity save(UserEntity userEntity);

}
