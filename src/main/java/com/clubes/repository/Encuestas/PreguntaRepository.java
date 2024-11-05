package com.clubes.repository.Encuestas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubes.model.Encuestas.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}

