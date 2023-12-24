package com.bilgesucakir.flightsearchapi.service;

import com.bilgesucakir.flightsearchapi.entity.UserEntity;
import com.bilgesucakir.flightsearchapi.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service implementation handling some querying of user entity
 */
@Service
public class UserEntityServiceImpl implements UserEntityService{

    private UserEntityRepository userEntityRepository;

    @Autowired
    public UserEntityServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public boolean isUserEntityExists(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    public boolean isUsernameValid(String username) {
        return !username.equals("") && !username.trim().isEmpty();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }
}
