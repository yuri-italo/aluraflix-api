package br.com.alura.aluraflixapi.repository;

import br.com.alura.aluraflixapi.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {

}
