package com.clubes.repository.Encuestas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubes.model.Encuestas.Encuesta;

public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
}
