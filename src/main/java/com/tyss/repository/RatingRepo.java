package com.tyss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.entity.Rating;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer>{
	
}
