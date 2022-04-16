package br.com.alura.aluraflixapi.model;

import javax.persistence.*;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true,nullable = false,length = 70)
    private String title;

    @Column(nullable = false,length = 5000)
    private String description;

    @Column(unique=true,nullable = false,length = 2048)
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
