package org.alsjava.sessions.model.exception;

public class DuplicateCommandException extends RuntimeException {

    public DuplicateCommandException(Object bean) {
        super("Duplicate command for bean: %s".formatted(bean.getClass()));
    }
}
