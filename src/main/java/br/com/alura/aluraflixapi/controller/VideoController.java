package br.com.alura.aluraflixapi.controller;

import br.com.alura.aluraflixapi.dto.VideoDto;
import br.com.alura.aluraflixapi.form.VideoForm;
import br.com.alura.aluraflixapi.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @Transactional
    @ApiOperation(value = "Create a new video.")
    public ResponseEntity<VideoDto> save(@Valid @RequestBody VideoForm videoForm, UriComponentsBuilder uriBuilder) {
        VideoDto videoDto = videoService.save(videoForm);
        return ResponseEntity.created(uriBuilder.path("/videos/{id}").buildAndExpand(videoDto.getId()).toUri()).body(videoDto);
    }

    @GetMapping
    @ApiOperation(value = "Return a list of all videos or all videos containing the param.")
    public ResponseEntity<Page<VideoDto>> get(
            @PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable,
            @RequestParam(required = false) String search) {
        return videoService.list(pageable,search);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a video by ID")
    public ResponseEntity<?> getOneById(@PathVariable Long id)  {
        return videoService.getById(id);
    }

    @GetMapping("/free")
    @ApiOperation(value = "Get a list of free videos, does not need authorization.")
    public ResponseEntity<Page<VideoDto>> getFree(@PageableDefault(sort = "id",direction = Sort.Direction.ASC,size = 5) Pageable pageable) {
        return videoService.getFreeOnes(pageable);
    }

    @PutMapping("/{id}")
    @Transactional
    @ApiOperation("Update one or more video fields.")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VideoForm videoForm) {
        return videoService.update(id,videoForm);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ApiOperation("Delete a video by ID.")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return videoService.deleteById(id);
    }
}
