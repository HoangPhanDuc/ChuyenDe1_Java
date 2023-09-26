package com.ecommerce.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String username;
    private String email;

    @Size(min = 7, message = "At least 7 characters")
    private String password;

    private String confirmPassword;
}
