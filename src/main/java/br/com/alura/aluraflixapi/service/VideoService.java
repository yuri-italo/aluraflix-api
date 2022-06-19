package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.form.VideoForm;
import br.com.alura.aluraflixapi.model.Video;
import br.com.alura.aluraflixapi.repository.VideoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final CategoryService categoryService;

    public VideoService(VideoRepository videoRepository, CategoryService categoryService) {
        this.videoRepository = videoRepository;
        this.categoryService = categoryService;
    }

    public Video save(VideoForm videoForm) {
        Video video = new Video();
        BeanUtils.copyProperties(videoForm,video);

        video.setCategory(categoryService.getCategoryById(videoForm.getCategoryId()));

        videoRepository.save(video);

        return  video;
    }

    public Page<Video> list(Pageable pageable, String search) {
        Page<Video> videos;
        if (search == null)
            videos = videoRepository.findAll(pageable);
        else
            videos = videoRepository.findByTitleContainingIgnoreCase(search,pageable);

        return videos;
    }

    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    public Page<Video> getFreeOnes(Pageable pageable) {
        return videoRepository.getFree(pageable);
    }

    public Video update(Long id, VideoForm videoForm) {
        return updateFields(videoForm,videoRepository.getById(id));
    }

    private Video updateFields(VideoForm videoForm, Video video) {
        video.setTitle(videoForm.getTitle());
        video.setDescription(videoForm.getDescription());
        video.setUrl(videoForm.getUrl());
        video.setCategory(categoryService.getCategoryById(videoForm.getCategoryId()));

        return video;
    }

    public void delete(Video video) {
        videoRepository.delete(video);
    }
}
