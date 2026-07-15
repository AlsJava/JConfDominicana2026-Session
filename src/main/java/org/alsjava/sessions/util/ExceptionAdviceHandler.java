package org.alsjava.sessions.util;

import lombok.RequiredArgsConstructor;
import org.alsjava.sessions.model.network.APIError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdviceHandler {

    private final ExceptionAdviceHandlerHelper exceptionAdviceHandlerHelper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handle(MethodArgumentNotValidException exception) {
        return exceptionAdviceHandlerHelper.createMethodArgument(exception);
    }
}
