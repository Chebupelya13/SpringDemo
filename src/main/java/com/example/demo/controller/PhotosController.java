package com.example.demo.controller;

import com.example.demo.entity.Photo;
import com.example.demo.service.MinioService;
import com.example.demo.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/api/photos")
@Tag(description = "Операции с фотографиями", name = "Документы")
public class PhotosController {

    private final PhotoService photoService;
    private final MinioService minioService;

    @Autowired
    public PhotosController(PhotoService photoService, MinioService minioService) {
        this.photoService = photoService;
        this.minioService = minioService;
    }

    @GetMapping("/findById/{photoId}")
    @Operation(summary = "Поиск фотографии документа по ID")
    public ResponseEntity<byte[]> findPhotoById( @PathVariable int photoId ) {
        Photo photo = photoService.getPhotoById(photoId);

        try (InputStream stream = minioService.getFile(photo.getPath())) {
            byte[] response = stream.readAllBytes();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
