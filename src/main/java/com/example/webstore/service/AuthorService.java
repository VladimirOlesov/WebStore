package com.example.webstore.service;

import com.example.webstore.model.dto.AuthorDto;
import java.util.List;

public interface AuthorService {


  List<AuthorDto> getAuthors(String name);
}