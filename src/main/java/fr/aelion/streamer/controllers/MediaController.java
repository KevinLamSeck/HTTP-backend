package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.conceptorDtos.MediaDto;
import fr.aelion.streamer.dto.conceptorDtos.StudentDto;
import fr.aelion.streamer.services.MediaService;
import fr.aelion.streamer.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/medias") // http://127.0.0.1:8080/api/v1/students
@CrossOrigin
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping
    public List<MediaDto> findAll() {
        return mediaService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findOne(@PathVariable int id) {
        try {
            return ResponseEntity.ok(mediaService.findOne(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>( "Media with " + id + " was not found", HttpStatus.NOT_FOUND);
        }
    }
}
