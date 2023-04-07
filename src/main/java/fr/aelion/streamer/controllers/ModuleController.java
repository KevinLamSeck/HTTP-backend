package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.simplerDtos.ModuleDto;
import fr.aelion.streamer.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/modules") // http://127.0.0.1:8080/api/v1/students
@CrossOrigin
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping
    public List<ModuleDto> findAll() {
        return moduleService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findOne(@PathVariable int id) {
        try {
            return ResponseEntity.ok(moduleService.findOne(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>( "Module with " + id + " was not found", HttpStatus.NOT_FOUND);
        }}

    @PostMapping
    public ResponseEntity<ModuleDto> add(@RequestBody ModuleDto module) {
        ModuleDto moduleDto = this.moduleService.add(module);
        return ResponseEntity.ok(moduleDto);
    }

}
