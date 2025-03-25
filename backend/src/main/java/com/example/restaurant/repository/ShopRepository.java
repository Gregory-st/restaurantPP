package com.example.restaurant.repository;

import com.example.restaurant.entity.EatEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<EatEntity, Long> {
  Optional<EatEntity> findById(long id);
}
