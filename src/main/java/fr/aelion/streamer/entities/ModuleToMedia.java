package fr.aelion.streamer.entities;

import fr.aelion.streamer.serializable.CompositeKeyMTM;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

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


