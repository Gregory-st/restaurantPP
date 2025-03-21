package com.example.restaurant.response;

import com.example.restaurant.model.EatModel;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopResponse extends BaseResponse{
  @Setter(AccessLevel.PRIVATE)
  private List<EatModel> eats;
}
