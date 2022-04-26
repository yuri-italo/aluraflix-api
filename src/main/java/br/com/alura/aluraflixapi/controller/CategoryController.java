package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.form.CategoryForm;
import br.com.alura.aluraflixapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    @Transactional
    @Operation(summary = "Create a new category.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryForm categoryForm, UriComponentsBuilder uriBuilder) {
        CategoryDto categoryDto = categoryService.save(categoryForm);
        return ResponseEntity.created(uriBuilder.path("/categories/{id}").buildAndExpand(categoryDto.getId()).toUri()).body(categoryDto);
    }

    @GetMapping
    @Operation(summary = "Return a list of all categories or all categories containing the param.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<CategoryDto>> getAll(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5)Pageable pageable) {
        return categoryService.listAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/{id}/videos")
    @Operation(summary = "Get a list of videos grouped by category.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getVideosByCategory (@PathVariable Long id) {
        return categoryService.getVideosByCategory(id);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more category fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid CategoryForm categoryForm) {
        return categoryService.update(id,categoryForm);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }
}
