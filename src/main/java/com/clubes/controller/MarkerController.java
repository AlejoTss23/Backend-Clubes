package com.clubes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubes.model.Marker;
import com.clubes.service.imple.MarkerService;

@RestController
@RequestMapping("/api/markers")
public class MarkerController {

    @Autowired
    private MarkerService markerService;

    @GetMapping
    public List<Marker> getAllMarkers() {
        return markerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marker> getMarkerById(@PathVariable Long id) {
        Optional<Marker> marker = markerService.findById(id);
        return marker.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Marker createMarker(@RequestBody Marker marker) {
        return markerService.save(marker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marker> updateMarker(@PathVariable Long id, @RequestBody Marker markerDetails) {
        Optional<Marker> marker = markerService.findById(id);
        if (marker.isPresent()) {
            Marker updatedMarker = marker.get();
            updatedMarker.setNombre(markerDetails.getNombre());
            updatedMarker.setDireccion(markerDetails.getDireccion());
            updatedMarker.setLatitud(markerDetails.getLatitud());
            updatedMarker.setLongitud(markerDetails.getLongitud());
            updatedMarker.setInfo(markerDetails.getInfo());
            return ResponseEntity.ok(markerService.save(updatedMarker));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        if (markerService.findById(id).isPresent()) {
            markerService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
