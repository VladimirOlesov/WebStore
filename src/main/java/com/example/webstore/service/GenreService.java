package com.example.webstore.service;

import com.example.webstore.model.dto.GenreDto;
import java.util.List;

public interface GenreService {

  List<GenreDto> getGenres(String genreName);
}