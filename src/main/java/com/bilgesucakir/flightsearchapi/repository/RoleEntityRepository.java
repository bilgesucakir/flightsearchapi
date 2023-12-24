package com.bilgesucakir.flightsearchapi.repository;

import com.bilgesucakir.flightsearchapi.entity.RoleEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Role repository extending JPA repository for db access and db operations
 * Used in auth related operations
 */
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);
}
