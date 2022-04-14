package br.com.alura.aluraflixapi.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title must not be null.")
    @NotEmpty(message = "Title must not be empty.")
    @Length(max = 70, message = "Title size must be within 70.")
    @Column(unique=true)
    private String title;

    @NotNull(message = "Description must not be null.")
    @NotEmpty(message = "Description must not be empty.")
    @Length(max = 5000, message = "Description size must be within 5000.")
    private String description;

    @NotNull(message = "URL must not be null.")
    @NotEmpty(message = "URL must not be empty.")
    @Length(max = 2048, message = "URL size must be within 2048.")
    @Column(unique=true)
    @URL(message = "Invalid URL.")
    private String url;

    public Video() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
