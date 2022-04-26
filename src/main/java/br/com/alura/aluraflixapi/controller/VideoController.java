package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.form.VideoForm;
import br.com.alura.aluraflixapi.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new video.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<VideoDto> save(@Valid @RequestBody VideoForm videoForm, UriComponentsBuilder uriBuilder) {
        VideoDto videoDto = videoService.save(videoForm);
        return ResponseEntity.created(uriBuilder.path("/videos/{id}").buildAndExpand(videoDto.getId()).toUri()).body(videoDto);
    }

    @GetMapping
    @Operation(summary = "Return a list of all videos or all videos containing the param.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<VideoDto>> get(
            @PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable,
            @RequestParam(required = false) String search) {
        return videoService.list(pageable,search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a video by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOneById(@PathVariable Long id)  {
        return videoService.getById(id);
    }

    @GetMapping("/free")
    @Operation(summary = "Get a list of free videos, does not need authorization.")
    public ResponseEntity<Page<VideoDto>> getFree(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable) {
        return videoService.getFreeOnes(pageable);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more video fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VideoForm videoForm) {
        return videoService.update(id,videoForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete a video by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return videoService.deleteById(id);
    }
}
