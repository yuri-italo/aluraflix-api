package br.com.alura.aluraflixapi.dto;

import br.com.alura.aluraflixapi.model.Category;

import java.util.HashSet;
import java.util.Set;

public class VideosByCategoryDto {
    private final String category;
    private final Set<VideoWithoutCategoryDto> videos;

    public VideosByCategoryDto(Category ctg) {
        this.category = ctg.getTitle();
        this.videos = VideoWithoutCategoryDto.convertSetOfVideos(ctg.getVideos());
    }

    public static VideosByCategoryDto convertToDto(Category category) {
        return new VideosByCategoryDto(category);
    }

    public String getCategory() {
        return category;
    }

    public Set<VideoWithoutCategoryDto> getVideos() {
        return videos;
    }
}
