package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.UserService;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.dto.AddUserRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody AddUserRequest addUserRequest){
        User user = new User(addUserRequest.getId(), addUserRequest.getUsername());
        userService.save(user);
        return ResponseEntity.ok("User added successfully");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(String userId){
        try {
            User user = userService.deleteUser(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUser(String userId){
        try {
            User user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
