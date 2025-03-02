package com.example.springboot.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueCheckStrategy;

@org.mapstruct.MapperConfig(
  componentModel = "spring",
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)

public interface MapperConfig {
}
