package fr.aelion.streamer.serializable;

import fr.aelion.streamer.entities.Media;
import fr.aelion.streamer.entities.Module;

import java.io.Serializable;

public class CompositeKeyMTM implements Serializable {
    private Media media;
    private Module module;
}

