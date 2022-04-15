package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VideoDto {

    @NotNull(message = "ID must not be null.")
    @NotEmpty(message = "ID must not be empty.")
    private Long id;

    @NotNull(message = "Title must not be null.")
    @NotEmpty(message = "Title must not be empty.")
    private String title;

    @NotNull(message = "Description must not be null.")
    @NotEmpty(message = "Description must not be empty.")
    private String description;

    @NotNull(message = "URL must not be null.")
    @NotEmpty(message = "URL must not be empty.")
    private String url;

    public VideoDto() {
    }

    public VideoDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
    }

    public static VideoDto convertToDto(Video video) {
        return new VideoDto(video);
    }

    public static ResponseEntity<Page<VideoDto>> convertManyToDto(Page<Video> videos) {
        return ResponseEntity.status(HttpStatus.OK).body(videos.map(VideoDto::new));
    }

    public Long getId() {
        return id;
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
