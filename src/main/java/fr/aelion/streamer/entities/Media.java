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
    private Student creator;

    @ManyToOne
    private TypeMedia typeMedia;

    @OneToMany(mappedBy = "media")
    private List<ModuleToMedia> modules = new ArrayList<>();

    public Media() {
        createdAt = LocalDate.now();
    }

}