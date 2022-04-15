package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Category;

public class CategoryByIdDto {
    private final String title;
    private final String color;

    public CategoryByIdDto(Category category) {
        this.title = category.getTitle();
        this.color = category.getColor();
    }

    public static CategoryByIdDto convertToDto(Category category) {
        return new CategoryByIdDto(category);
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
}
