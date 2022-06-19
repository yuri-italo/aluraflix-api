package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.UserDto;
import br.com.alura.aluraflixapi.form.LoginForm;
import br.com.alura.aluraflixapi.model.User;
import br.com.alura.aluraflixapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new user.")
    public ResponseEntity<?> save(@RequestBody @Valid LoginForm loginForm, UriComponentsBuilder uriBuilder) {
        Optional<User> optional = userService.findByEmail(loginForm.getEmail());

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");

        User user = userService.save(loginForm);

        return ResponseEntity.created(uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri()).body(new UserDto(user));
    }

    @GetMapping
    @Operation(summary = "Return a list of all users.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id",size = 5,direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> list = userService.listAll(pageable);

        if (list.hasContent())
            return ResponseEntity.status(HttpStatus.OK).body(UserDto.convertManyToDto(list));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an user by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new UserDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found.");
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more user fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@RequestBody @Valid LoginForm loginForm, @PathVariable Long id) {
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent()) {
            Optional<User> userByEmail = userService.findByEmail(loginForm.getEmail());

            if (userByEmail.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exist.");

            User updatedUser = userService.update(loginForm, id);

            return ResponseEntity.status(HttpStatus.OK).body(new UserDto(updatedUser));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete an user by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<User> optional = userService.findById(id);

        if (optional.isPresent()) {
            userService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User ID not found.");
    }
}
