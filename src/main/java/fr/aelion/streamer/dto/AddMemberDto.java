package fr.aelion.streamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberDto {
    @NotBlank(message = "Lastname cannot be null")
    private String lastName;

    private String firstName;

    @NotNull(message = "Email cannot be null")
    @Email(message = "email passed is not a valid email")
    private String email;

    private String phoneNumber;

    @NotBlank(message = "login cannot be null")
    @Size(min = 8, message="Login must have at least 8 chars")
    private String login;

    @NotBlank(message = "password cannot be null")
    @Size(min = 8, message="Password must have at least 8 chars")
    //@Pattern(regexp = "/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/")
    private String password;
}
