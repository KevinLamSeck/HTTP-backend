package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.CRUDDto.CourseUpdateDto;
import fr.aelion.streamer.dto.CourseAddDto;
import fr.aelion.streamer.dto.FullCourseDto;
import fr.aelion.streamer.dto.simplerDtos.CourseDto;
import fr.aelion.streamer.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {
    @Autowired
    private CourseService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CourseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findOne(@PathVariable() int id) {
        try {
            return ResponseEntity.ok(service.findOne(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable() int id) {
        try {
            service.remove(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FullCourseDto> add(@RequestBody CourseAddDto course) {
        System.out.print(course.getCreator().getId()+" // "+course.getCreator().getFirstName());
        FullCourseDto courseDto = this.service.add(course);
        return ResponseEntity.ok(courseDto);
    }

    @PostMapping("update")
    public ResponseEntity<FullCourseDto> update(@RequestBody CourseUpdateDto course) {
        System.out.print(course.toString());
        FullCourseDto courseDto = this.service.update(course);
        return ResponseEntity.ok(courseDto);
    }
}
