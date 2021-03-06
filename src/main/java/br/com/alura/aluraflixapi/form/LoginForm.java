package br.com.alura.aluraflixapi.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginForm {
    @NotBlank(message = "Email must not be empty.")
    @Size(max = 254, message = "Email size must be within 254.")
    @Email(message = "Invalid Email.")
    private String email;

    @NotBlank(message = "Password must not be empty.")
    @Size(max = 8, message = "Password size must be within 8.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(this.email,this.password);
    }
}
