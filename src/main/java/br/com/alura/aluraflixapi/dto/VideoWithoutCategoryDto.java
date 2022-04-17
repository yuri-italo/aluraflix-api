package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Video;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class VideoWithoutCategoryDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String url;

    public VideoWithoutCategoryDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
    }

    public static Set<VideoWithoutCategoryDto> convertSetOfVideos(Set<Video> videos) {
        Set<VideoWithoutCategoryDto> videosDto = new HashSet<>();

        videos.forEach(video -> {
            var videoWithoutCategoryDto = new VideoWithoutCategoryDto(video);
            videosDto.add(videoWithoutCategoryDto);
        });

        return videosDto;
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
