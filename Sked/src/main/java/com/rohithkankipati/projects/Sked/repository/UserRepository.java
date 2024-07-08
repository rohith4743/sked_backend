package com.rohithkankipati.projects.Sked.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohithkankipati.projects.Sked.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
	
	Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    @Query("{'$or': [{'username': ?0}, {'email': ?0}, {'mobileNumber': ?0}]}")
    Optional<UserEntity> findByUsernameOrEmailOrPhoneNumber(String identifier);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

}
