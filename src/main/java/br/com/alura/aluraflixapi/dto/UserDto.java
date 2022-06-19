package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserDto {
    private final Long id;
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public static Page<UserDto> convertManyToDto(Page<User> users) {
        return users.map(UserDto::new);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
