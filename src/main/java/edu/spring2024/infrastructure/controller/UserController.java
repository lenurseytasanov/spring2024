package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.UserService;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto){
        User user = new User(userDto.getId(), userDto.getUsername());
        userService.save(user);
        return ResponseEntity.ok("User added successfully");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDto userDto){
        try {
            User user = userService.deleteUser(userDto.getId());
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUser(@Valid @RequestBody UserDto userDto){
        try {
            User user = userService.getUser(userDto.getId());
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
