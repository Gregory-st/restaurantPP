package com.example.restaurant.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "eats")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class EatEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.PRIVATE)
  private long id;

  private String name;
  private String description;
  private int price;
  private int weight;
  private int proteins;
  private int fats;
  private int carbon;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "eat")
  private List<BasketEntity> baskets;
}
