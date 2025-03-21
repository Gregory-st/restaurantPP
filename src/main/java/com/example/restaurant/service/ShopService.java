package com.example.restaurant.service;

import com.example.restaurant.dto.AddBasketDto;
import com.example.restaurant.entity.BasketEntity;
import com.example.restaurant.entity.EatEntity;
import com.example.restaurant.entity.UserEntity;
import com.example.restaurant.exception.UndefinedEatByIdException;
import com.example.restaurant.model.EatModel;
import com.example.restaurant.repository.BasketRepository;
import com.example.restaurant.repository.ShopRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository repository;
  private final BasketRepository basketRepository;
  private final AuthenticationService authService;
  private final ShopRepository shopRepository;

  public List<EatModel> getEats(){
    return repository.findAll()
        .stream()
        .map(EatModel::new)
        .toList();
  }

  public void addProductInBasket(AddBasketDto basketDto, String token) {
    UserEntity user = authService.getByToken(token);

    Long idProduct = basketDto.id();

    BasketEntity basketEntity = basketRepository
        .findByEat_IdAndUser_Id(idProduct, user.getId())
        .orElseGet(BasketEntity::new);

    int count = basketEntity.getCount();
    basketEntity.setCount(count + 1);
    if(count == 0) {
      EatEntity eatEntity = shopRepository
          .findById(idProduct)
          .orElseThrow(UndefinedEatByIdException::new);

      basketEntity.setUser(user);
      basketEntity.setEat(eatEntity);
    }

    basketRepository.save(basketEntity);
  }
}
