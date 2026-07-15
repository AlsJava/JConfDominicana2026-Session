package org.alsjava.sessions.pattern.command;

public interface CommandHandler<R, C extends Command<R>> {

    R handle(C command);
}
