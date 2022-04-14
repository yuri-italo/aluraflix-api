package br.com.alura.aluraflixapi.repository;

import br.com.alura.aluraflixapi.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Video v WHERE lower(v.title)  = lower(:title)")
    boolean existsByTitle(@Param("title") String title);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Video v WHERE lower(v.description) = lower(:description)")
    boolean existsByDescription(@Param("description") String description);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Video v WHERE lower(v.url) = lower(:url)")
    boolean existsByUrl(@Param("url") String url);
}
