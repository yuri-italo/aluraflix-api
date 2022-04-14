package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Video;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VideoByIdDto {
    @NotNull(message = "Title must not be null.")
    @NotEmpty(message = "Title must not be empty.")
    private final String title;

    @NotNull(message = "Description must not be null.")
    @NotEmpty(message = "Description must not be empty.")
    private final String description;

    @NotNull(message = "URL must not be null.")
    @NotEmpty(message = "URL must not be empty.")
    private final String url;

    public VideoByIdDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
    }

    public static VideoByIdDto convertToDto(Video video) {
        return new VideoByIdDto(video);
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
