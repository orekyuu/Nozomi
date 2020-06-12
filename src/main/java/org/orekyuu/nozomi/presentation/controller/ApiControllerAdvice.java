package org.orekyuu.nozomi.presentation.controller;

import org.orekyuu.nozomi.infrastructure.datasource.InMemoryResourceNotFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackageClasses = ApiControllerAdvice.class)
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    @ExceptionHandler({InMemoryResourceNotFoundException.class})
    protected ResponseEntity<Object> handleInMemoryResourceNotFoundException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(ex, null, headers, HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null || body.getClass() != ErrorResponse.class) {
            body = new ErrorResponse.Builder().exception(ex).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse body = new ErrorResponse.Builder()
                .exception(ex)
                .bindingError(ex.getBindingResult())
                .build();
        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
