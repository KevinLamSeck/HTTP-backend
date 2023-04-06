package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.UserDto;
import fr.aelion.streamer.entities.User;
import fr.aelion.streamer.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    UserService service;
    @PostMapping("byEmailAndPassword")
    public ResponseEntity<?> findByLoginAndEmail(@RequestBody UserDto user) {
        return this.service.findByLoginAndPassword(user.getLogin(), user.getPassword())
                .map(u -> {
                    return ResponseEntity.ok(u);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
