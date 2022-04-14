package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.model.Video;
import br.com.alura.aluraflixapi.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody VideoDto videoDto, UriComponentsBuilder uriBuilder) {
        Video video = new Video();
        BeanUtils.copyProperties(videoDto,video);
        videoService.save(video);

        URI uri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();

        return ResponseEntity.created(uri).body(video);
    }

    @GetMapping
    public ResponseEntity<Page<Video>> getAll(@PageableDefault(sort = "id",direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.listAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id) {
        Optional<Video> optional = videoService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(VideoDto.convertToDto(optional.get()));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found!");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VideoDto videoDto) {
        Optional<Video> optional = videoService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(videoService.update(optional.get(),videoDto));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Video> optional = videoService.findById(id);

        if (optional.isPresent()) {
            videoService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Video deleted!");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found!");
    }
}
