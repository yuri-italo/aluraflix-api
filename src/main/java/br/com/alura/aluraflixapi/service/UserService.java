package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.dto.UserDto;
import br.com.alura.aluraflixapi.form.LoginForm;
import br.com.alura.aluraflixapi.model.User;
import br.com.alura.aluraflixapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> save(LoginForm loginForm, UriComponentsBuilder uriBuilder) {
        Optional<User> optional = userRepository.findByEmail(loginForm.getEmail());

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");

        loginForm.setPassword(new BCryptPasswordEncoder().encode(loginForm.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(loginForm,user);

        userRepository.save(user);

        return ResponseEntity.created(uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri()).body(new UserDto(user));
    }

    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return UserDto.convertManyToDto(users);
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new UserDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found.");
    }

    public ResponseEntity<?> updateById(LoginForm loginForm, Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            Optional<User> userByEmail = userRepository.findByEmail(loginForm.getEmail());

            if (userByEmail.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exist.");

            return updateFields(loginForm,optional.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found!");
    }

    private ResponseEntity<?> updateFields(LoginForm loginForm, User user) {
        loginForm.setPassword(new BCryptPasswordEncoder().encode(loginForm.getPassword()));
        BeanUtils.copyProperties(loginForm,user);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(user));
    }

    public ResponseEntity<?> deleteById(Long id) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found.");
    }
}
