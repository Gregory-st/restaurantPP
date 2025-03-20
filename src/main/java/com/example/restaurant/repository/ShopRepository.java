package com.example.restaurant.repository;

import com.example.restaurant.entity.EatEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<EatEntity, Long> {
    List<EatEntity> getAll();
}
