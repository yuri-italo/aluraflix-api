package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.CategoryByIdDto;
import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.dto.VideosByCategoryDto;
import br.com.alura.aluraflixapi.form.CategoryForm;
import br.com.alura.aluraflixapi.model.Category;
import br.com.alura.aluraflixapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

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
        Category category = categoryService.save(categoryForm);
        return ResponseEntity.created(uriBuilder.path("/categories/{id}").buildAndExpand(category.getId()).toUri()).body(CategoryDto.convertToDto(category));
    }

    @GetMapping
    @Operation(summary = "Return a list of all categories or all categories containing the param.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<CategoryDto>> getAll(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5)Pageable pageable) {
        Page<Category> categories = categoryService.listAll(pageable);

        if (categories.hasContent())
            return ResponseEntity.status(HttpStatus.OK).body(CategoryDto.convertManyToDto(categories));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        Optional<Category> optional = categoryService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(CategoryByIdDto.convertToDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");
    }

    @GetMapping("/{id}/videos")
    @Operation(summary = "Get a list of videos grouped by category.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getVideosByCategory (@PathVariable Long id) {
        Optional<Category> optional = categoryService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(VideosByCategoryDto.convertToDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more category fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid CategoryForm categoryForm) {
        Optional<Category> optional = categoryService.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID not found.");

        Category updatedCategory = categoryService.update(id, categoryForm);

        return ResponseEntity.status(HttpStatus.OK).body(CategoryByIdDto.convertToDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Category> optional = categoryService.findById(id);

        if (optional.isPresent()) {
            categoryService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category ID does not exist.");
    }
}
