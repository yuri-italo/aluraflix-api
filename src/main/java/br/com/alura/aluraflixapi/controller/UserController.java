package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.form.LoginForm;
import br.com.alura.aluraflixapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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
        return userService.save(loginForm,uriBuilder);
    }

    @GetMapping
    @Operation(summary = "Return a list of all users.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id",size = 5,direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.listAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an user by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more user fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@RequestBody @Valid LoginForm loginForm, @PathVariable Long id) {
        return userService.updateById(loginForm,id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete an user by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
