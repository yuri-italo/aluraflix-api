package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CategoryDto {
    private final Long id;
    private final String title;
    private final String color;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.color = category.getColor();
    }

    public static ResponseEntity<Page<CategoryDto>> convertManyToDto(Page<Category> categories) {
        return ResponseEntity.status(HttpStatus.OK).body(categories.map(CategoryDto::new));
    }

    public static CategoryDto convertToDto(Category category) {
        return new CategoryDto(category);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
}
