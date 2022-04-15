package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAll(@PageableDefault(sort = "id",direction = Sort.Direction.ASC)Pageable pageable) {
        return categoryService.listAll(pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return categoryService.getById(id);
    }
}
