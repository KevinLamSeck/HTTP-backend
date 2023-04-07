package fr.aelion.streamer.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorModel {
    private HttpStatus httpStatus;
    private LocalDateTime timespamp;
    private String message;
    private String details;

    public ErrorModel(HttpStatus httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
        this.timespamp = LocalDateTime.now();
    }
}
