package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Video;

public class VideoByIdDto {
    private final String title;

    private final String description;

    private final String url;

    private final Long categoryId;

    public VideoByIdDto(Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
        this.categoryId = video.getCategory().getId();
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

    public Long getCategoryId() {
        return categoryId;
    }
}
