package com.example.restaurant.service;

import com.example.restaurant.model.EatModel;
import com.example.restaurant.repository.ShopRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository repository;

  public List<EatModel> getEats(){
    return repository.getAll()
        .stream()
        .map(EatModel::new)
        .toList();
  }
}
