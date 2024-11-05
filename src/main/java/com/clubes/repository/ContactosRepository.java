package com.clubes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubes.model.contactos;

@Repository
public interface ContactosRepository extends JpaRepository<contactos, Long> {
    List<contactos> findAll();
}