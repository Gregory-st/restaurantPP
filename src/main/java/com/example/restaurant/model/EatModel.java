package com.example.restaurant.model;

import com.example.restaurant.entity.EatEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EatModel {
  private Long id;
  private String title;
  private String description;
  private int price;
  private int weight;
  private int proteins;
  private int fats;
  private int carbon;
  private String image;
  private int count;

  public EatModel(EatEntity eat){
    id = eat.getId();
    title = eat.getName();
    price = eat.getPrice();
    weight = eat.getWeight();
    proteins = eat.getProteins();
    fats = eat.getFats();
    carbon = eat.getCarbon();
    image = eat.getImage();
    description = eat.getDescription();
  }
}
