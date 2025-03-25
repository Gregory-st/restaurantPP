package com.example.restaurant.repository;

import com.example.restaurant.entity.BasketEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
  Optional<BasketEntity> findByEat_IdAndUser_Id(long eatId, long userId);
  List<BasketEntity> findAllByUser_Id(long userId);
}
