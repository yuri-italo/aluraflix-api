package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.form.LoginForm;
import br.com.alura.aluraflixapi.model.User;
import br.com.alura.aluraflixapi.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(LoginForm loginForm) {
        loginForm.setPassword(new BCryptPasswordEncoder().encode(loginForm.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(loginForm,user);

        userRepository.save(user);

        return user;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(LoginForm loginForm, Long id) {
        return updateFields(loginForm,userRepository.getById(id));
    }

    private User updateFields(LoginForm loginForm, User user) {
        loginForm.setPassword(new BCryptPasswordEncoder().encode(loginForm.getPassword()));
        BeanUtils.copyProperties(loginForm,user);

        userRepository.save(user);

        return user;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
