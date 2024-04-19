package edu.spring2024.infrastructure.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotNull
    @Size(min = 4, max = 40)
    private String id;

    @NotNull
    @Size(min = 4, max = 10)
    private String username;
}
