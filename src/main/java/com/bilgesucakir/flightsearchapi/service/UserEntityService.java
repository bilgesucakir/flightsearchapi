package com.bilgesucakir.flightsearchapi.service;


import com.bilgesucakir.flightsearchapi.entity.UserEntity;

/**
 * User service
 */
public interface UserEntityService {

    boolean isUserEntityExists(String username);

    boolean isUsernameValid(String username);

    UserEntity save(UserEntity userEntity);

}
