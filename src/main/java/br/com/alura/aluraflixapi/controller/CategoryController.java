package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.form.CategoryForm;
import br.com.alura.aluraflixapi.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    @Transactional
    public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryForm categoryForm, UriComponentsBuilder uriBuilder) {
        CategoryDto categoryDto = categoryService.save(categoryForm);
        return ResponseEntity.created(uriBuilder.path("/categories/{id}").buildAndExpand(categoryDto.getId()).toUri()).body(categoryDto);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAll(@PageableDefault(sort = "id",direction = Sort.Direction.ASC)Pageable pageable) {
        return categoryService.listAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<?> getVideosByCategory (@PathVariable Long id) {
        return categoryService.getVideosByCategory(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid CategoryForm categoryForm) {
        return categoryService.update(id,categoryForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }
}
