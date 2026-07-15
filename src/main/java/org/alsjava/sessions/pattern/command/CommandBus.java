package org.alsjava.sessions.pattern.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alsjava.sessions.model.exception.CommandTimeoutException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "pattern.cqrs", name = "enabled", havingValue = "true")
public class CommandBus {

    private final MessagingTemplate messagingTemplate;
    private final MessageChannel commandChannel;

    @SuppressWarnings("unchecked")
    public <R, C> R sendCommand(C command) {
        Message<?> message = messagingTemplate.sendAndReceive(commandChannel, new GenericMessage<>(command));
        if (message == null) {
            log.error("No response received for command: {}", command.getClass().getSimpleName());
            throw new CommandTimeoutException(command.getClass());
        }
        return (R) message.getPayload();
    }
}
