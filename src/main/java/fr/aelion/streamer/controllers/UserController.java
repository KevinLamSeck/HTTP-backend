package fr.aelion.streamer.controllers;

import fr.aelion.streamer.dto.UserDto;
import fr.aelion.streamer.entities.User;
import fr.aelion.streamer.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping("byEmailAndPassword")
    public ResponseEntity<?> findByLoginAndEmail(@RequestBody UserDto user) {
        try {
            Optional<User> optionalUser = this.service.findByLoginAndPassword(user.getLogin(), user.getPassword());
            System.out.println(optionalUser.isPresent());
            if (optionalUser.isPresent()) {
                UserDto userDto = this.modelMapper.map(optionalUser.get(), UserDto.class);
                return ResponseEntity.ok(userDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while finding user");
        }
    }
}
