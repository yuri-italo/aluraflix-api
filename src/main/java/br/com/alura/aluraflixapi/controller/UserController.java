package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.form.LoginForm;
import br.com.alura.aluraflixapi.service.UserService;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid LoginForm loginForm, UriComponentsBuilder uriBuilder) {
        return userService.save(loginForm,uriBuilder);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id",size = 5,direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.listAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@RequestBody @Valid LoginForm loginForm, @PathVariable Long id) {
        return userService.updateById(loginForm,id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
