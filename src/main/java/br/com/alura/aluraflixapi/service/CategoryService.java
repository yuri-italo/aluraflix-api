package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.dto.CategoryByIdDto;
import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.dto.VideosByCategoryDto;
import br.com.alura.aluraflixapi.form.CategoryForm;
import br.com.alura.aluraflixapi.model.Category;
import br.com.alura.aluraflixapi.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
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

    public CategoryDto save(CategoryForm categoryForm) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryForm,category);

        categoryRepository.save(category);

        return CategoryDto.convertToDto(category);
    }

    public ResponseEntity<?> update(Long id, CategoryForm categoryForm) {
        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent())
            return updateFields(categoryForm,optional.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");
    }

    private ResponseEntity<?> updateFields(CategoryForm categoryForm, Category category) {
        category.setTitle(categoryForm.getTitle());
        category.setColor(categoryForm.getColor());

        return ResponseEntity.status(HttpStatus.OK).body(CategoryByIdDto.convertToDto(category));
    }

    public ResponseEntity<?> deleteById(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent()) {
            categoryRepository.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID does not exist.");
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.getById(id);
    }

    public ResponseEntity<?> getVideosByCategory(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(VideosByCategoryDto.convertToDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");
    }
}
