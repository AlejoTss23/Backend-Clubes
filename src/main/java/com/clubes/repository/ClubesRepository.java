package com.clubes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubes.model.clubes;


public interface ClubesRepository extends JpaRepository<clubes, Integer> {

	List<clubes> findAll();

}
