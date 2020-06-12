package org.orekyuu.nozomi.infrastructure.datasource;

public class InMemoryResourceNotFoundException extends RuntimeException {
    public InMemoryResourceNotFoundException(String message) {
        super(message);
    }
}
