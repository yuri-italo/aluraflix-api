package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.form.LoginForm;
import br.com.alura.aluraflixapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    @ApiOperation(value = "Create a new user.")
    public ResponseEntity<?> save(@RequestBody @Valid LoginForm loginForm, UriComponentsBuilder uriBuilder) {
        return userService.save(loginForm,uriBuilder);
    }

    @GetMapping
    @ApiOperation(value = "Return a list of all users.")
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id",size = 5,direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.listAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an user by ID.")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Update one or more user fields.")
    public ResponseEntity<?> update(@RequestBody @Valid LoginForm loginForm, @PathVariable Long id) {
        return userService.updateById(loginForm,id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Delete an user by ID.")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
