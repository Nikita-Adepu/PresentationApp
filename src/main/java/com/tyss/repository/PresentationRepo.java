package com.tyss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.entity.Presentation;

@Repository
public interface PresentationRepo extends JpaRepository<Presentation, Integer> {

}
