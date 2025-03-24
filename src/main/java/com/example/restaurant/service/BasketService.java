package com.example.restaurant.service;

import com.example.restaurant.entity.BasketEntity;
import com.example.restaurant.entity.EatEntity;
import com.example.restaurant.entity.UserEntity;
import com.example.restaurant.exception.ExpiredJwtTokenException;
import com.example.restaurant.model.EatModel;
import com.example.restaurant.repository.BasketRepository;
import com.example.restaurant.repository.ShopRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {

  private static final Logger log = LogManager.getLogger(BasketService.class);
  private final BasketRepository repository;
  private final ShopRepository shopRepository;
  private final AuthenticationService service;

  public List<EatModel> getBasket(String token){
    UserEntity user;

    try{
      user = service.getByToken(token);
    }
    catch (ExpiredJwtTokenException exception){
      throw new RuntimeException(exception);
    }

    List<BasketEntity> basketEntities = repository.findAllByUser_Id(user.getId());

    return basketEntities
        .stream()
        .map(basketEntity -> {
          EatEntity entity = shopRepository.findById(
              basketEntity
                  .getEat()
                  .getId()
          ).orElseThrow();

          EatModel model = new EatModel(entity);
          model.setCount(basketEntity.getCount());
          return model;
        })
        .toList();
  }

}
