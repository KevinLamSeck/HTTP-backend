package fr.aelion.streamer.serializable;

import fr.aelion.streamer.entities.Course;
import fr.aelion.streamer.entities.Module;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private Course course;
    private Module module;
}

