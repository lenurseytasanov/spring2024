package edu.spring2024.infrastructure.controller;

import edu.spring2024.app.UserService;
import edu.spring2024.app.dto.user.CreateUserRequest;
import edu.spring2024.app.dto.user.DeleteUserRequest;
import edu.spring2024.app.dto.user.GetUserRequest;
import edu.spring2024.app.dto.user.UserDto;
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
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        UserDto userDto = userService.createUser(createUserRequest.userId(), createUserRequest.username());
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest deleteUserRequest){
        UserDto userDto = userService.deleteUser(deleteUserRequest.userId());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getUser(@Valid @RequestBody GetUserRequest getUserRequest){
        UserDto userDto = userService.getUser(getUserRequest.userId());
        return ResponseEntity.ok(userDto);
    }
}
