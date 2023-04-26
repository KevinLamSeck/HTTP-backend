package fr.aelion.streamer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media")
@Getter
@Setter
public class Media {

    public Media(String title, byte[] data) {
        this.title = title;
        this.data = data;
        createdAt = LocalDate.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String summary;

    private Float duration;

    private LocalDate createdAt;

    @Column(nullable = false)
    private String url;

    @ManyToOne()
    private Member creator;

    @ManyToOne
    private TypeMedia typeMedia;

    @Lob
    private byte[] data;

    @OneToMany(mappedBy = "media", cascade = CascadeType.REMOVE)
    private List<ModuleToMedia> modules = new ArrayList<>();

    public Media() {
        createdAt = LocalDate.now();
    }

}