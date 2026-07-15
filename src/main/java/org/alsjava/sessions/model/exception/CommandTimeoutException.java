package org.alsjava.sessions.model.exception;

public class CommandTimeoutException extends RuntimeException {

    public CommandTimeoutException(Class<?> commandClass) {
        super("No response received for command: %s".formatted(commandClass));
    }
}
