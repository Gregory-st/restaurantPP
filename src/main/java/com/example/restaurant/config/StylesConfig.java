package com.example.restaurant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StylesConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
      registry.addResourceHandler("/restaurant/styles/**")
          .addResourceLocations("classpath:/templates/styles/");
      registry.addResourceHandler("restaurant/examples/**")
          .addResourceLocations("classpath:/templates/examples");
  }
}
