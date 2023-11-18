package com.example.webstore.service;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.enums.SortDirection;
import java.util.List;

public interface AuthorService {

  List<AuthorDto> getAuthors(String authorName, SortDirection sortDirection);
}