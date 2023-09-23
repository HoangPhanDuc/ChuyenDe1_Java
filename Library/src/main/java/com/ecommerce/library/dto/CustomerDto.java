package com.ecommerce.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;

    @Size(min = 7, message = "At least 7 characters")
    private String password;

    private String confirmPassword;
}
