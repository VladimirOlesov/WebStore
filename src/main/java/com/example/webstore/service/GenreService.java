package com.example.webstore.service;

import com.example.webstore.model.dto.GenreDto;
import com.example.webstore.model.enums.SortDirection;
import java.util.List;

public interface GenreService {

  List<GenreDto> getGenres(String genreName, SortDirection sortDirection);
}