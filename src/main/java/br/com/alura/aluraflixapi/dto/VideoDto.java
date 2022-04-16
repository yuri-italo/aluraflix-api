package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VideoDto {
    private Long id;

    private String title;

    private String description;

    private String url;

    private Long categoryId;

    public VideoDto() {
    }

    public VideoDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
        this.categoryId = video.getCategory().getId();
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

    public Long getCategoryId() {
        return categoryId;
    }
}
