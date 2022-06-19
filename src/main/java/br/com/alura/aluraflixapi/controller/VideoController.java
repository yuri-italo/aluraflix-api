package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.VideoByIdDto;
import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.form.VideoForm;
import br.com.alura.aluraflixapi.model.Video;
import br.com.alura.aluraflixapi.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Optional;

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
        Video video = videoService.save(videoForm);
        return ResponseEntity.created(uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri()).body(new VideoDto(video));
    }

    @GetMapping
    @Operation(summary = "Return a list of all videos or all videos containing the param.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<VideoDto>> get(
            @PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable,
            @RequestParam(required = false) String search) {

        Page<Video> list = videoService.list(pageable, search);

        if (list.hasContent())
            return ResponseEntity.status(HttpStatus.OK).body(VideoDto.convertManyToDto(list));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a video by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getOneById(@PathVariable Long id)  {

        Optional<Video> optional = videoService.findById(id);

        if (optional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(VideoByIdDto.convertToDto(optional.get()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video ID does not exist.");
    }

    @GetMapping("/free")
    @Operation(summary = "Get a list of free videos, does not need authorization.")
    public ResponseEntity<Page<VideoDto>> getFree(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable) {

        Page<Video> list = videoService.getFreeOnes(pageable);

        if (list.hasContent())
            return ResponseEntity.status(HttpStatus.OK).body(VideoDto.convertManyToDto(list));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update one or more video fields.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VideoForm videoForm) {
        Optional<Video> optional = videoService.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video ID does not exist.");

        Video updatedVideo = videoService.update(id, videoForm);

        return ResponseEntity.status(HttpStatus.OK).body(new VideoByIdDto(updatedVideo));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete a video by ID.")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Video> optional = videoService.findById(id);

        if (optional.isPresent()) {
            videoService.delete(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Video deleted.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video ID does not exist.");
    }
}
