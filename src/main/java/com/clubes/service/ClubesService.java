package com.clubes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clubes.model.ClubDTO;
import com.clubes.model.clubes;

public interface ClubesService {
  List<clubes> listar();
   clubes registrar(clubes clubes);
   clubes actualizar(clubes clubes);
   void eliminar(Integer codigo);
   clubes ListarPorId(Integer codigo); 
   Page<clubes> listPageable(Pageable pageable);
    List<ClubDTO> listarNombresYFechas();
    clubes save(clubes club); 
}
