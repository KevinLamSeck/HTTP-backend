package fr.aelion.streamer;

import fr.aelion.streamer.services.interfaces.MediaStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamerApplication {

    @Resource
    MediaStorageService mediasStorageService;

    public static void main(String[] args) {
        SpringApplication.run(StreamerApplication.class, args);
    }

    public void run(String... args) throws Exception {
        mediasStorageService.deleteAll();
        mediasStorageService.init();
    }

}
