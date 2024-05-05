package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.UserService;
import edu.spring2024.app.dto.user.CreateUserRequest;
import edu.spring2024.app.dto.user.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("#createUserRequest.userId() == authentication.name")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.createUser(createUserRequest.userId(), createUserRequest.username()));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.name")
    public ResponseEntity<UserDto> delete(@NotNull @Size(min = 10, max = 50) @PathVariable String userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.name")
    public ResponseEntity<UserDto> one(@NotNull @Size(min = 10, max = 50) @PathVariable String userId) {
        return ResponseEntity.ok(userService.findBy(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> all(@Min(1) @RequestParam(required = false) Long chatId) {
        return ResponseEntity.ok(userService.findAll(chatId));
    }
}
