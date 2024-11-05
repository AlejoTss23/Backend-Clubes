package com.clubes.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubes.model.Noticia;
import com.clubes.repository.NoticiaRepository;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    public List<Noticia> findAll() {
        return noticiaRepository.findAll();
    }

    public Optional<Noticia> findById(Long id) {
        return noticiaRepository.findById(id);
    }

    public Noticia save(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

    public void deleteById(Long id) {
        noticiaRepository.deleteById(id);
    }
}