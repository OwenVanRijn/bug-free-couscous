package io.swagger.api;

import io.swagger.dto.ErrorResponseDTO;
import io.swagger.exceptions.NotFoundException;
import io.swagger.exceptions.RestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {RestException.class})
    protected ResponseEntity<Object> handleRestExceptions(RestException e, WebRequest w){
        ErrorResponseDTO er = new ErrorResponseDTO(e);
        return handleExceptionInternal(e, er, new HttpHeaders(), e.getStatus(), w);
    }
}
