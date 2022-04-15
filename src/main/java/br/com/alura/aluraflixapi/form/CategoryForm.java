package br.com.alura.aluraflixapi.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryForm {
    @NotNull(message = "Title must not be null.")
    @NotEmpty(message = "Title must not be empty.")
    private String title;

    @NotNull(message = "Color must not be null.")
    @NotEmpty(message = "Color must not be empty.")
    private String color;

    public CategoryForm() {
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
}
