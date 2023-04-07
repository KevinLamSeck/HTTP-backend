package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.AddStudentDto;
import fr.aelion.streamer.dto.SimpleStudentDto;
import fr.aelion.streamer.dto.SimpleStudentProjection;
import fr.aelion.streamer.dto.conceptorDtos.StudentDto;
import fr.aelion.streamer.entities.Student;
import fr.aelion.streamer.services.exceptions.EmailAlreadyExistsException;
import fr.aelion.streamer.services.exceptions.LoginAlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import fr.aelion.streamer.services.StudentService;

@RestController
@RequestMapping("api/v1/students") // http://127.0.0.1:8080/api/v1/students
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDto> findAll() {
        return studentService.findAll();
    }
    @GetMapping("{id}") // GET http://127.0.0.1:5000/api/v1/students/1
    public ResponseEntity<?> findOne(@PathVariable int id) {
        try {
            return ResponseEntity.ok(studentService.findOne(id));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>( "Student with " + id + " was not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("simple")
    public List<SimpleStudentProjection> findSimpleStudents() {
        return studentService.fromProjection();
    }

    @GetMapping("dto")
    public List<SimpleStudentDto> simpleStudentDtos() {
        return studentService.findSimpleStudents();
    }

    /**
     * POST a new student
     * uri : POST http://127.0.0.1:5000/api/v1/students
     * @param student
     * @return
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AddStudentDto student) {
        try {
            Student newStudent = studentService.add(student);
            return ResponseEntity.created(null).body(newStudent);
        } catch(EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.reject());
        } catch (LoginAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body((e.reject()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> update(@RequestBody Student student) {
        try {
            studentService.update(student);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> singleDelete(@PathVariable int id) {
        try {
            studentService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> multipleDelete(@RequestBody Set<Integer> ids) {
        return ResponseEntity.ok(studentService.multipleDelete(ids));
    }


    @PostMapping("byLoginAndPassword")
    public ResponseEntity<?> findByLoginAndPassword(@RequestBody Student Student) {
        return this.studentService.findByLoginAndPassword(Student.getLogin(), Student.getPassword())
                .map(u -> {
                    return ResponseEntity.ok(u);
                })
                .orElse(ResponseEntity.notFound().build());
    }



}
