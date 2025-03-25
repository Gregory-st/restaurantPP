package com.example.restaurant.repository;

import com.example.restaurant.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findUserEntitiesByLogin(String login);
  Optional<UserEntity> findUserEntitiesByEmail(String email);
  Optional<UserEntity> findUserEntitiesById(long id);
}