package org.alsjava.sessions.model.exception;

import org.alsjava.sessions.pattern.command.CommandHandler;

public class CommandNotInheritException extends RuntimeException {

    public CommandNotInheritException(Object bean) {
        super("Bean (%s) not inherit from %s".formatted(bean.getClass(), CommandHandler.class));
    }
}
