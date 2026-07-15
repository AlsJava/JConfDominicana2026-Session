package org.alsjava.sessions.model.exception;

public class InvalidCommandEventException extends RuntimeException {

    public InvalidCommandEventException(Object bean) {
        super("Bean (%s) has @CommandEvent without a valid command class".formatted(bean.getClass()));
    }
}
