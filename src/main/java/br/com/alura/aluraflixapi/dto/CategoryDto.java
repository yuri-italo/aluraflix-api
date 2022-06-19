package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Category;
import org.springframework.data.domain.Page;

public class CategoryDto {
    private final Long id;
    private final String title;
    private final String color;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.color = category.getColor();
    }

    public static Page<CategoryDto> convertManyToDto(Page<Category> categories) {
        return categories.map(CategoryDto::new);
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
