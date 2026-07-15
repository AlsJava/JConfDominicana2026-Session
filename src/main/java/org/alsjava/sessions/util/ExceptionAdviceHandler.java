package com.purrbyte.aitools.util;

import com.purrbyte.aitools.model.network.APIError;
import lombok.RequiredArgsConstructor;
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
