package com.clubes.repository.Encuestas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubes.model.Encuestas.Opcion;

public interface OpcionRepository extends JpaRepository<Opcion, Long> {
}

