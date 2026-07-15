package org.alsjava.sessions.model.exception;

public class CommandHandlerNotFoundException extends RuntimeException {

    public CommandHandlerNotFoundException(Class<?> commandClass) {
        super("No handler registered for command: %s".formatted(commandClass));
    }
}
