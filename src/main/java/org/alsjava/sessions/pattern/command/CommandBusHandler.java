package org.alsjava.sessions.pattern.command;

import lombok.RequiredArgsConstructor;
import org.alsjava.sessions.model.exception.CommandHandlerNotFoundException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "pattern.cqrs", name = "enabled", havingValue = "true")
public class CommandBusHandler {

    private final CommandProvider commandProvider;

    @SuppressWarnings("unchecked")
    @ServiceActivator(inputChannel = "commandChannel")
    public <R> R executeCommand(Command<R> command) {
        CommandHandler handler = commandProvider.get(command.getClass());
        if (handler == null) {
            throw new CommandHandlerNotFoundException(command.getClass());
        }
        return (R) handler.handle(command);
    }
}
