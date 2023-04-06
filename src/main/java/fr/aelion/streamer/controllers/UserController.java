package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.UserDto;
import fr.aelion.streamer.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    StudentService service;
    @PostMapping("byLoginAndPassword")
    public ResponseEntity<?> findByLoginAndPassword(@RequestBody UserDto user) {
        return this.service.findByLoginAndPassword(user.getLogin(), user.getPassword())
                .map(u -> {
                    return ResponseEntity.ok(u);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
