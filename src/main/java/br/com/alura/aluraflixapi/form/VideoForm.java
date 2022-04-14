package br.com.alura.aluraflixapi.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VideoForm {
    @NotNull(message = "Title must not be null.")
    @NotEmpty(message = "Title must not be empty.")
    private String title;

    @NotNull(message = "Description must not be null.")
    @NotEmpty(message = "Description must not be empty.")
    private String description;

    @NotNull(message = "URL must not be null.")
    @NotEmpty(message = "URL must not be empty.")
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
