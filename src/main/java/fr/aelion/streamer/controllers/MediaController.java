package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.conceptorDtos.MediaDto;
import fr.aelion.streamer.dto.conceptorDtos.StudentDto;
import fr.aelion.streamer.services.MediaService;
import fr.aelion.streamer.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
