package br.com.alura.aluraflixapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable = false,length = 30)
    private String title;

    @Column(unique=true,nullable = false,length = 30)
    private String color;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Video> videos = new HashSet<>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Video> getVideos() {
        return videos;
    }
}
