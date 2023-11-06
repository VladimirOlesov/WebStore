package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.FavoriteIdDto;
import com.example.webstore.model.entity.FavoriteId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

  FavoriteIdDto favoriteIdToDto(FavoriteId favoriteId);

}