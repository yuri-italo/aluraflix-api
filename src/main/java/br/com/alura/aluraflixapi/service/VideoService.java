package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.model.Video;
import br.com.alura.aluraflixapi.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Page<Video> listAll(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    public void save(@Valid Video video) {
        videoRepository.save(video);
    }

    public Video update(Video video, VideoDto videoDto) {
        video.setTitle(videoDto.getTitle());
        video.setDescription(videoDto.getDescription());
        video.setUrl(videoDto.getUrl());

        return video;
    }

    public void delete(Video video) {
        videoRepository.delete(video);
    }

}
