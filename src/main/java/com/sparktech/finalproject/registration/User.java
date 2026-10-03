package com.sparktech.finalproject.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;


    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message="phone is required")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;

    @NotBlank(message="Role is required")
    private String role;

    private String address;

}
