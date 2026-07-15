package com.purrbyte.aitools.util;

import com.purrbyte.aitools.model.network.APIError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionAdviceHandlerHelper {

    @Value("${dev.advice.show-line-numbers}")
    private boolean showLineNumber;
    @Value("${dev.advice.show-stack-trace}")
    private boolean showStackTrace;
    @Value("${dev.advice.debug-console}")
    private boolean debugConsole;

    public ResponseEntity<APIError> createMethodArgument(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String errors = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(","));
        errors += bindingResult.getGlobalErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return createResponse(errors, null, exception, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<APIError> createResponse(Throwable throwable, HttpStatus httpStatus) {
        return createResponse(throwable.getMessage(), throwable.getStackTrace()[0].getLineNumber(), throwable, httpStatus);
    }

    private ResponseEntity<APIError> createResponse(String message, Integer lineNumber, Throwable throwable, HttpStatus httpStatus) {
        APIError apiError = APIError.builder()
                .message(message)
                .status(httpStatus.value())
                .line(showLineNumber ? lineNumber : null)
                .trace(showStackTrace ? throwable : null)
                .build();
        if (debugConsole) {
            log.error("Error caught by ControllerAdvice :: {}", apiError, throwable);
        }
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
