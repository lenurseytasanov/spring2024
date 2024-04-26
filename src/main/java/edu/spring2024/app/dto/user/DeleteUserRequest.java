package edu.spring2024.app.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DeleteUserRequest(@NotNull @Size(min = 10, max = 50) String userId) { }
