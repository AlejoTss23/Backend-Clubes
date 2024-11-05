package com.clubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubes.model.Marker;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
}
