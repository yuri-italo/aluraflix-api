package br.com.alura.aluraflixapi.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryForm {
    @NotBlank(message = "Title must not be empty.")
    @Size(max = 30, message = "Title size must be within 30.")
    private String title;

    @NotBlank(message = "Color must not be empty.")
    @Size(max = 30, message = "Color size must be within 30.")
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
