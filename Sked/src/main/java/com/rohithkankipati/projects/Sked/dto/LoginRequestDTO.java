package com.rohithkankipati.projects.Sked.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
	
	@NotBlank(message = "username.required")
    @Size(min = 3, max = 255, message = "username.size")
	@Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "username.pattern")
    private String username;

    @NotBlank(message = "password.required")
    @Size(min = 8, max = 100, message = "password.size")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).*$", message = "password.pattern")
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
