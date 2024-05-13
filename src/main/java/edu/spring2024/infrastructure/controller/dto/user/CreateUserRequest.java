package edu.spring2024.infrastructure.controller.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotNull @Size(min = 10, max = 50) String userId,
        @NotNull @Size(min = 5, max = 10) String username) { }
