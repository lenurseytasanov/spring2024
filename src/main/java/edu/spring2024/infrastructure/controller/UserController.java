package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.UserService;
import edu.spring2024.domain.User;
import edu.spring2024.infrastructure.assembler.mapper.dto.UserDtoMapper;
import edu.spring2024.infrastructure.controller.dto.user.CreateUserRequest;
import edu.spring2024.infrastructure.controller.dto.user.UserDto;
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

    private final UserDtoMapper userDtoMapper;

    @PostMapping
    @PreAuthorize("#createUserRequest.userId() == authentication.name")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest.userId(), createUserRequest.username());
        return ResponseEntity.ok(userDtoMapper.toDto(user));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("#userId == authentication.name")
    public ResponseEntity<UserDto> delete(@NotNull @Size(min = 10, max = 50) @PathVariable String userId) {
        User user = userService.deleteUser(userId);
        return ResponseEntity.ok(userDtoMapper.toDto(user));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.name")
    public ResponseEntity<UserDto> one(@NotNull @Size(min = 10, max = 50) @PathVariable String userId) {
        User user = userService.findBy(userId);
        return ResponseEntity.ok(userDtoMapper.toDto(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> all(@Min(1) @RequestParam(required = false) Long chatId) {
        List<User> users = userService.findAll(chatId);
        return ResponseEntity.ok(users.stream().map(userDtoMapper::toDto).toList());
    }
}
