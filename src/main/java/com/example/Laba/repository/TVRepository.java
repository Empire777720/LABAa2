package com.example.Laba.repository;

import com.example.Laba.model.TVModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TVRepository extends JpaRepository<TVModel, Long> {
}