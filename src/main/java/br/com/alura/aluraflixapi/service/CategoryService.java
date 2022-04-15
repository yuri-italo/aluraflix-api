package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.model.Category;
import br.com.alura.aluraflixapi.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
