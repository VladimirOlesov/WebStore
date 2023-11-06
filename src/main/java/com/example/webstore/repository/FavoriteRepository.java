package com.example.webstore.repository;

import com.example.webstore.model.entity.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

  List<Favorite> findByUserId(Long userId);

  Optional<Favorite> findByUserIdAndBookId (Long userId, Long BookId);
}