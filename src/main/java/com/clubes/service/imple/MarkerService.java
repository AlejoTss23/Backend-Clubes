package com.clubes.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubes.model.Marker;
import com.clubes.repository.MarkerRepository;

@Service
public class MarkerService {

    @Autowired
    private MarkerRepository markerRepository;

    public List<Marker> findAll() {
        return markerRepository.findAll();
    }

    public Optional<Marker> findById(Long id) {
        return markerRepository.findById(id);
    }

    public Marker save(Marker marker) {
        return markerRepository.save(marker);
    }

    public void deleteById(Long id) {
        markerRepository.deleteById(id);
    }
}
