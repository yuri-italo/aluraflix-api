package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.dto.VideoByIdDto;
import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.form.VideoForm;
import br.com.alura.aluraflixapi.model.Video;
import br.com.alura.aluraflixapi.repository.VideoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public VideoDto save(VideoForm videoForm) {
        Video video = new Video();
        BeanUtils.copyProperties(videoForm,video);

        video.setCategory(categoryService.getCategoryById(videoForm.getCategoryId()));

        videoRepository.save(video);

        return  VideoDto.convertToDto(video);
    }

    public ResponseEntity<Page<VideoDto>> listAll(Pageable pageable, String search) {
        Page<Video> videos;
        if (search == null)
            videos = videoRepository.findAll(pageable);
        else
            videos = videoRepository.findByTitleContainingIgnoreCase(search,pageable);

        return VideoDto.convertManyToDto(videos);
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Video> optional = videoRepository.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(VideoByIdDto.convertToDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video ID does not exist.");
    }

    public ResponseEntity<Page<VideoDto>> getFreeOnes(Pageable pageable) {
        return VideoDto.convertManyToDto(videoRepository.getFree(pageable));
    }

    public ResponseEntity<?> update(Long id, VideoForm videoForm) {
        Optional<Video> optional = videoRepository.findById(id);

        if (optional.isPresent())
            return updateFields(videoForm,optional.get());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video ID does not exist.");
    }

    private ResponseEntity<VideoByIdDto> updateFields(VideoForm videoForm, Video video) {
        video.setTitle(videoForm.getTitle());
        video.setDescription(videoForm.getDescription());
        video.setUrl(videoForm.getUrl());
        video.setCategory(categoryService.getCategoryById(videoForm.getCategoryId()));

        return ResponseEntity.status(HttpStatus.OK).body(new VideoByIdDto(video));
    }

    public ResponseEntity<?> deleteById(Long id) {
        Optional<Video> optional = videoRepository.findById(id);

        if (optional.isPresent()) {
            videoRepository.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Video deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video ID does not exist.");
    }
}
