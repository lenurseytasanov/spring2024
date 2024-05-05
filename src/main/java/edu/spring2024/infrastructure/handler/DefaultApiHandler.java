package edu.spring2024.infrastructure.handler;

import edu.spring2024.app.exception.ChatNotFoundException;
import edu.spring2024.app.exception.MessageNotFoundException;
import edu.spring2024.app.exception.UserNotFoundException;
import edu.spring2024.app.exception.UserNotUniqueException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultApiHandler {

    @ExceptionHandler(ChatNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    public ResponseEntity<?> chatNotFoundHandler(ChatNotFoundException exception) {
        log.debug("chat not found", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    public ResponseEntity<?> userNotFoundHandler(UserNotFoundException exception) {
        log.debug("user not found", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotUniqueException.class)
    @ApiResponse(responseCode = "409", description = "Not unique", content = @Content)
    public ResponseEntity<?> userNotUniqueException(UserNotUniqueException exception) {
        log.debug("user id not unique", exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MessageNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    public ResponseEntity<?> messageNotFoundHandler(MessageNotFoundException exception) {
        log.debug("message not found", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
