package com.clubes.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clubes.model.Noticia;
import com.clubes.service.imple.NoticiaService;


@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;
      private static final String UPLOAD_DIR = "uploads/";

      @PostMapping("/upload")
      public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
          try {
              String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
              String uploadDir = "uploads/";
              File directory = new File(uploadDir);
              if (!directory.exists()) {
                  directory.mkdirs();
              }
              
              Files.copy(file.getInputStream(), Paths.get(uploadDir + filename));
      
              // Crear la URL completa de la imagen
              String imageUrl = "http://localhost:8080/uploads/" + filename;
      
              // Imprimir la URL para verificar
              System.out.println("Generated Image URL: " + imageUrl);
      
              Map<String, String> response = new HashMap<>();
              response.put("imageUrl", imageUrl);
      
              return new ResponseEntity<>(response, HttpStatus.OK);
          } catch (IOException e) {
              return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }
      
      
      

    @GetMapping
    public List<Noticia> getAllNoticias() {
        return noticiaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Noticia> getNoticiaById(@PathVariable Long id) {
        Optional<Noticia> noticia = noticiaService.findById(id);
        if (noticia.isPresent()) {
            return ResponseEntity.ok(noticia.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Noticia createNoticia(@RequestBody Noticia noticia) {
        return noticiaService.save(noticia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Noticia> updateNoticia(@PathVariable Long id, @RequestBody Noticia noticiaDetails) {
        Optional<Noticia> noticia = noticiaService.findById(id);
        if (noticia.isPresent()) {
            Noticia updatedNoticia = noticia.get();
            updatedNoticia.setTitulo(noticiaDetails.getTitulo());
            updatedNoticia.setImagenUrl(noticiaDetails.getImagenUrl());
            updatedNoticia.setContenido(noticiaDetails.getContenido());
            return ResponseEntity.ok(noticiaService.save(updatedNoticia));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticia(@PathVariable Long id) {
        Optional<Noticia> noticia = noticiaService.findById(id);
        if (noticia.isPresent()) {
            noticiaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}