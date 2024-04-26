package edu.spring2024.app.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull @Size(min = 10, max = 50)
    private String userId;

    @NotNull @Size(min = 5, max = 10)
    private String username;
}
