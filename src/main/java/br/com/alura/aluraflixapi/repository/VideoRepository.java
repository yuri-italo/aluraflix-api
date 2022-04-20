package br.com.alura.aluraflixapi.repository;

import br.com.alura.aluraflixapi.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    Page<Video> findByTitleContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT v FROM Video v WHERE v.id <= 10")
    Page<Video> getFree(Pageable pageable);
}
