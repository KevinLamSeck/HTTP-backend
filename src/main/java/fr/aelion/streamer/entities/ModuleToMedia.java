package fr.aelion.streamer.entities;

import fr.aelion.streamer.serializable.CompositeKeyMTM;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@IdClass(CompositeKeyMTM.class)
public class ModuleToMedia {
    @Id
    @ManyToOne()
    private Module module;
    @Id
    @ManyToOne()
    private Media media;
    private Integer orderMedia;
}


