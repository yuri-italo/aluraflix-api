package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.CategoryDto;
import br.com.alura.aluraflixapi.form.CategoryForm;
import br.com.alura.aluraflixapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    @Transactional
    @ApiOperation(value = "Create a new category.")
    public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryForm categoryForm, UriComponentsBuilder uriBuilder) {
        CategoryDto categoryDto = categoryService.save(categoryForm);
        return ResponseEntity.created(uriBuilder.path("/categories/{id}").buildAndExpand(categoryDto.getId()).toUri()).body(categoryDto);
    }

    @GetMapping
    @ApiOperation(value = "Return a list of all categories or all categories containing the param.")
    public ResponseEntity<Page<CategoryDto>> getAll(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5)Pageable pageable) {
        return categoryService.listAll(pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a category by ID.")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping("/{id}/videos")
    @ApiOperation(value = "Get a list of videos grouped by category.")
    public ResponseEntity<?> getVideosByCategory (@PathVariable Long id) {
        return categoryService.getVideosByCategory(id);
    }

    @PutMapping("/{id}")
    @Transactional
    @ApiOperation(value = "Update one or more category fields.")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid CategoryForm categoryForm) {
        return categoryService.update(id,categoryForm);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete category by ID.")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return categoryService.deleteById(id);
    }
}
