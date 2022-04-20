package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.form.VideoForm;
import br.com.alura.aluraflixapi.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VideoDto> save(@Valid @RequestBody VideoForm videoForm, UriComponentsBuilder uriBuilder) {
        VideoDto videoDto = videoService.save(videoForm);
        return ResponseEntity.created(uriBuilder.path("/videos/{id}").buildAndExpand(videoDto.getId()).toUri()).body(videoDto);
    }

    @GetMapping
    public ResponseEntity<Page<VideoDto>> getAll(
            @PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable,
            @RequestParam(required = false) String search) {
        return videoService.listAll(pageable,search);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id)  {
        return videoService.getById(id);
    }

    @GetMapping("/free")
    public ResponseEntity<Page<VideoDto>> getFree(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable) {
        return videoService.getFreeOnes(pageable);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VideoForm videoForm) {
        return videoService.update(id,videoForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return videoService.deleteById(id);
    }
}
