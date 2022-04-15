package br.com.alura.aluraflixapi.form;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VideoForm {
    @NotBlank(message = "Title must not be empty.")
    @Size(max = 70, message = "Title size must be within 70.")
    private String title;

    @NotBlank(message = "Description must not be empty.")
    @Size(max = 5000, message = "Description size must be within 5000.")
    private String description;

    @NotBlank(message = "URL must not be empty.")
    @Size(max = 2048, message = "URL size must be within 2048.")
    @URL(message = "Invalid URL.")
    private String url;

    public VideoForm() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
