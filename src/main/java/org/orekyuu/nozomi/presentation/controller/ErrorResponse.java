package org.orekyuu.nozomi.presentation.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.stream.Collectors;

public class ErrorResponse {
    FormErrorEntity formError;
    DevMessageEntity devMessage;

    public static class Builder {
        private FormErrorEntity formError;
        private DevMessageEntity devMessage;

        public Builder bindingError(BindingResult result) {
            if (result.hasErrors()) {
                var globalErrors = result.getGlobalErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                var fieldErrors = result.getFieldErrors().stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                it -> Objects.requireNonNullElse(it.getDefaultMessage(), "")
                        ));
                formError = new FormErrorEntity(globalErrors, fieldErrors);
            }
            return this;
        }

        public Builder exception(Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));

            String stacktrace = stringWriter.toString();
            devMessage = new DevMessageEntity(e.getClass().getName(), e.getMessage(), stacktrace);
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse response = new ErrorResponse();
            response.devMessage = devMessage;
            response.formError = formError;
            return response;
        }
    }
}
