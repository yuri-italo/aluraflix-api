package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.dto.CategoryByIdDto;
import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.model.Category;
import br.com.alura.aluraflixapi.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Page<CategoryDto>> listAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        return CategoryDto.convertManyToDto(categories);
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(CategoryByIdDto.convertToDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");
    }
}
