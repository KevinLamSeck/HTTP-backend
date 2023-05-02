package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.MediaUploadDto;
import fr.aelion.streamer.services.MediaService;
import fr.aelion.streamer.services.interfaces.MediaStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class MediaStorageController {

    @Autowired
    private MediaStorageService mediaStorageService;

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "_" + file.getOriginalFilename();

        try {
            mediaStorageService.save(fileName, file);

            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/")
                    .path(fileName)
                    .toUriString();

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("url", url);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            message = "Error while uploading the file: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<MediaUploadDto>> getListFiles() {
        List<MediaUploadDto> fileInfos = mediaStorageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            System.out.println(fileName);
            String url = MvcUriComponentsBuilder
                    .fromMethodName(MediaStorageController.class, "getFile", path.getFileName().toString()).build().toString();

            MediaUploadDto mediaUploadDto = new MediaUploadDto(fileName, url);
            System.out.println(mediaUploadDto.getName());

            return mediaUploadDto;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = mediaStorageService.load(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
        return ResponseEntity.ok().headers(headers).body(file);
    }

}