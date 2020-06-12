package org.orekyuu.nozomi.presentation.controller;

public class DevMessageEntity {
    String exceptionClass;
    String message;
    String stacktrace;

    public DevMessageEntity(String exceptionClass, String message, String stacktrace) {
        this.exceptionClass = exceptionClass;
        this.message = message;
        this.stacktrace = stacktrace;
    }
}
