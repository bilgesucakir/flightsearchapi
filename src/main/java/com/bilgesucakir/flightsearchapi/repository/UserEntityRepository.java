package com.bilgesucakir.flightsearchapi.repository;

import com.bilgesucakir.flightsearchapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User repository extending JPA repository for db access and db operations
 * Used in auth related operations
 */
public interface UserEntityRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
